package com.antra.report.client.controller;

import com.antra.report.client.entity.ReportRequestEntity;
import com.antra.report.client.entity.ReportStatus;
import com.antra.report.client.pojo.FileType;
import com.antra.report.client.pojo.reponse.GeneralResponse;
import com.antra.report.client.pojo.reponse.ReportVO;
import com.antra.report.client.pojo.request.ReportRequest;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportControllerTest {

    ReportController reportController;
    ResponseEntity<GeneralResponse> responseEntity;
    String reqId;

    HttpServletResponse response;


    static ReportRequest requestBody;

    static final String jsonEventFilePath = "D:\\Coding\\Java\\reporting_system_aws\\ClientService\\src\\test\\resources\\events\\report.json";

    @Autowired
    ReportControllerTest(ReportController reportController, HttpServletResponse response) {
        this.reportController = reportController;
        this.response = response;
    }

    @BeforeAll
    static void prepareRequestBody() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            requestBody = mapper.readValue(new File(jsonEventFilePath), ReportRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createReportDirectly() {
        responseEntity = reportController.createReportDirectly(requestBody);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ReportVO reportVO = (ReportVO) Objects.requireNonNull(responseEntity.getBody()).getData();
        assertAll("Checking file status are both completed.",
                () -> assertEquals(ReportStatus.COMPLETED, reportVO.getPdfReportStatus()),
                () -> assertEquals(ReportStatus.COMPLETED, reportVO.getExcelReportStatus())
        );
        reqId = reportVO.getId();
        assertNotNull(ifFileCanBeDownloaded(FileType.PDF), "PDF file cannot be downloaded");
        assertNotNull(ifFileCanBeDownloaded(FileType.EXCEL), "Excel file cannot be downloaded");
//        deleteReportAndFiles();
    }

    ServletOutputStream ifFileCanBeDownloaded(FileType fileType) {
        try {
            reportController.downloadFile(reqId, fileType, response);
            return response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    void deleteReportAndFiles() {
        reportController.deleteReportAndFiles(reqId);
        assertNull(ifFileCanBeDownloaded(FileType.PDF), "PDF file did not be deleted!");
        assertNull(ifFileCanBeDownloaded(FileType.EXCEL), "Excel file did not be deleted!");
    }

}