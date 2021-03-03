package com.antra.evaluation.reporting_system.service;

import com.amazonaws.services.s3.AmazonS3;
import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import com.antra.evaluation.reporting_system.repo.ExcelRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;

@Service("ExcelServiceImpl2")
public class ExcelServiceImpl2 extends ExcelServiceImpl {

    private final AmazonS3 s3Client;

    @Value("${s3.bucket}")
    private String s3Bucket;

    public ExcelServiceImpl2(ExcelRepository excelRepository, ExcelGenerationService excelGenerationService, AmazonS3 s3Client) {
        super(excelRepository, excelGenerationService);
        this.s3Client = s3Client;
    }

    @Override
    public InputStream getExcelBodyById(String id) {
        return s3Client.getObject(s3Bucket, id).getObjectContent();
    }

    @Override
    public ExcelFile generateFile(ExcelRequest request, boolean multisheet) {
        ExcelFile excelFile = super.generateFile(request, multisheet);
        s3Client.putObject(s3Bucket, excelFile.getFileId(), new File(excelFile.getFileLocation()));
        try {
            File file = new File(excelFile.getFileLocation());
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        excelFile.setFileLocation(String.join("/", s3Bucket, excelFile.getFileId()));
        return excelFile;
    }

    @Override
    public ExcelFile deleteFile(String id) throws FileNotFoundException {
        Optional<ExcelFile> optional = excelRepository.findById(id);
        if (optional.isEmpty()) {
            throw new FileNotFoundException();
        }
        s3Client.deleteObject(s3Bucket, id);
        excelRepository.deleteById(id);
        return optional.get();
    }

//    @Override
//    public List<ExcelFile> getExcelList(){
//        return super.getExcelList();
//    }

}
