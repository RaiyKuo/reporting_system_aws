package com.antra.evaluation.reporting_system.service;

import com.amazonaws.services.s3.AmazonS3;
import com.antra.evaluation.reporting_system.pojo.api.PDFRequest;
import com.antra.evaluation.reporting_system.pojo.report.PDFFile;
import com.antra.evaluation.reporting_system.repo.PDFRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PDFServiceImpl implements PDFService {

    private static final Logger log = LoggerFactory.getLogger(PDFServiceImpl.class);

    private final PDFRepository repository;

    private final PDFGenerator generator;

    private final AmazonS3 s3Client;

    @Value("${s3.bucket}")
    private String s3Bucket;

    public PDFServiceImpl(PDFRepository repository, PDFGenerator generator, AmazonS3 s3Client) {
        this.repository = repository;
        this.generator = generator;
        this.s3Client = s3Client;
    }

    @Override
    public PDFFile createPDF(final PDFRequest request) {
        PDFFile file = generator.generate(request);
        file.setId("File-" + UUID.randomUUID().toString());
        file.setSubmitter(request.getSubmitter());
        file.setDescription(request.getDescription());
        file.setGeneratedTime(LocalDateTime.now());
        File temp = new File(file.getFileLocation());
        log.debug("Upload temp file to s3 {}", file.getFileLocation());
        try {
            s3Client.putObject(s3Bucket, file.getId(), temp);
            log.debug("Uploaded");
            file.setFileLocation(String.join("/", s3Bucket, file.getId()));
            repository.save(file);
        } catch (Exception e) {
            log.error("Uploading to s3 failed.");
            e.printStackTrace();
            file = null;
        }

        if (temp.delete()) {
            log.debug("tmp file cleared.");
        } else {
            log.warn("tmp file deletion failed.");
        }

        return file;
    }


    @Override
    public PDFFile deleteFile(String id) throws FileNotFoundException {
        Optional<PDFFile> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new FileNotFoundException();
        }
        s3Client.deleteObject(s3Bucket, id);
        repository.deleteById(id);
        return optional.get();
    }

}
