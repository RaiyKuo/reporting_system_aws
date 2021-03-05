package com.antra.report.client.service;

import com.amazonaws.services.s3.AmazonS3;
import com.antra.report.client.config.EndpointConfig;
import com.antra.report.client.entity.*;
import com.antra.report.client.exception.RequestNotFoundException;
import com.antra.report.client.pojo.EmailType;
import com.antra.report.client.pojo.FileType;
import com.antra.report.client.pojo.reponse.*;
import com.antra.report.client.pojo.request.ReportRequest;
import com.antra.report.client.repository.ReportRequestRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReportServiceImpl implements ReportService {
    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportRequestRepo reportRequestRepo;
    private final SNSService snsService;
    private final AmazonS3 s3Client;
    private final EmailService emailService;
    private final EndpointConfig endpoints;

    @Autowired
    public ReportServiceImpl(ReportRequestRepo reportRequestRepo, SNSService snsService, AmazonS3 s3Client, EmailService emailService, EndpointConfig endpoints) {
        this.reportRequestRepo = reportRequestRepo;
        this.snsService = snsService;
        this.s3Client = s3Client;
        this.emailService = emailService;
        this.endpoints = endpoints;
    }

    private ReportRequestEntity persistToLocal(ReportRequest request) {
        request.setReqId("Req-" + UUID.randomUUID().toString());

        ReportRequestEntity entity = new ReportRequestEntity();
        entity.setReqId(request.getReqId());
        entity.setSubmitter(request.getSubmitter());
        entity.setDescription(request.getDescription());
        entity.setCreatedTime(LocalDateTime.now());

        PDFReportEntity pdfReport = new PDFReportEntity();
        pdfReport.setRequest(entity);
        pdfReport.setStatus(ReportStatus.PENDING);
        pdfReport.setCreatedTime(LocalDateTime.now());
        entity.setPdfReport(pdfReport);

        ExcelReportEntity excelReport = new ExcelReportEntity();
        BeanUtils.copyProperties(pdfReport, excelReport);
        entity.setExcelReport(excelReport);

        return reportRequestRepo.save(entity);
    }

    @Override
    public ReportVO generateReportsSync(ReportRequest request) {
        persistToLocal(request);
        sendDirectRequests(request);
        return new ReportVO(reportRequestRepo.findById(request.getReqId()).orElseThrow());
    }

    @Override
    @Transactional
    public ReportVO generateReportsAsync(ReportRequest request) {
        ReportRequestEntity entity = persistToLocal(request);
        snsService.sendReportNotification(request);
        log.info("Send SNS the message: {}", request);
        return new ReportVO(entity);
    }

    //TODO:Change to parallel process using Threadpool? CompletableFuture?
    private void sendDirectRequests(ReportRequest request) {
        sendOneDirectRequest(request, new ExcelResponse(), FileType.EXCEL);
        sendOneDirectRequest(request, new PDFResponse(), FileType.PDF);
    }

    private void sendOneDirectRequest(ReportRequest request, FileResponse fileResponse, FileType fileType) {
        RestTemplate rs = new RestTemplate();
        try {
            fileResponse = rs.postForEntity(endpoints.getFileService(fileType), request, ExcelResponse.class).getBody();
        } catch (Exception e) {
            log.error(fileType.toString() + "Generation Error (Sync) : e", e);
            fileResponse.setReqId(request.getReqId());
            fileResponse.setFailed(true);
        } finally {
            assert fileResponse != null;
            updateLocal(fileResponse, fileType);
        }
    }


    private void updateLocal(FileResponse fileResponse, FileType fileType) {
        System.out.println(fileResponse.getClass().getName());
        SqsResponse response = new SqsResponse();
        BeanUtils.copyProperties(fileResponse, response);
        updateAsyncFileReport(response, fileType);
    }

    @Override
    //    @Transactional // why this? email could fail
    public void updateAsyncFileReport(SqsResponse response, FileType fileType) {
        ReportRequestEntity entity = reportRequestRepo.findById(response.getReqId()).orElseThrow(RequestNotFoundException::new);
        BaseReportEntity fileReport = switch (fileType) {
            case EXCEL:
                yield entity.getExcelReport();
            case PDF:
                yield entity.getPdfReport();
        };
        fileReport.setUpdatedTime(LocalDateTime.now());
        if (response.isFailed()) {
            fileReport.setStatus(ReportStatus.FAILED);
        } else {
            fileReport.setStatus(ReportStatus.COMPLETED);
            fileReport.setFileId(response.getFileId());
            fileReport.setFileLocation(response.getFileLocation());
            fileReport.setFileSize(response.getFileSize());
        }
        entity.setUpdatedTime(LocalDateTime.now());
        reportRequestRepo.save(entity);
        String to = "xaiykou@gmail.com";
        emailService.sendEmail(to, EmailType.SUCCESS, entity.getSubmitter());
    }


    @Override
    @Transactional(readOnly = true)
    public List<ReportVO> getReportList() {
        return StreamSupport.stream(reportRequestRepo.findAll().spliterator(), false).map(ReportVO::new).collect(Collectors.toList());
    }

    @Override
    public InputStream getFileBodyByReqId(String reqId, FileType type) {
        ReportRequestEntity entity = reportRequestRepo.findById(reqId).orElseThrow(RequestNotFoundException::new);
        String fileLocation = switch (type) {
            case PDF:
                yield entity.getPdfReport().getFileLocation(); // this location is s3 "bucket/key"
            case EXCEL:
                yield entity.getExcelReport().getFileLocation();
        };
        String bucket = fileLocation.split("/")[0];
        String key = fileLocation.split("/")[1];
        return s3Client.getObject(bucket, key).getObjectContent();
    }

    public void deleteReportAndFiles(String reqId) {
        ReportRequestEntity entity = reportRequestRepo.findById(reqId).orElseThrow(RequestNotFoundException::new);
        deleteSyncFile(entity.getExcelReport().getFileId(), FileType.EXCEL);
        deleteSyncFile(entity.getPdfReport().getFileId(), FileType.PDF);
        reportRequestRepo.deleteById(reqId);
    }

    private void deleteSyncFile(String fileId, FileType fileType) {
        log.info("Send Request to delete the {} file: {}", fileType.toString(), fileId);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete(endpoints.getFileService(fileType) + "/" + fileId);
        } catch (RestClientException e) {
            log.error(fileType.toString() + " file deletion failed", e);
        }
    }
}
