package com.antra.report.client.config;

import com.antra.report.client.pojo.FileType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointConfig {

    @Value("${service.excel}")
    private String excelService;

    @Value("${service.pdf}")
    private String pdfService;

    private String emailServiceApi;

    public String getFileService(FileType fileType) {
        return switch (fileType) {
            case EXCEL:
                yield excelService;
            case PDF:
                yield pdfService;
        };
    }

    public String getExcelService() {
        return excelService;
    }

    public String getPdfService() {
        return pdfService;
    }

    public String getEmailServiceApi() {
        return emailServiceApi;
    }
}
