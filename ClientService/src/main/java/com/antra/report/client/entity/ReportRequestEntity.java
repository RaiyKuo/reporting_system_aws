package com.antra.report.client.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.antra.report.client.repository.DynamoDbConverter;

import java.time.LocalDateTime;

@DynamoDBTable(tableName = "report_request")
public class ReportRequestEntity {
    @DynamoDBHashKey
    private String reqId;
    private String submitter;
    private String description;
    @DynamoDBTypeConverted(converter = DynamoDbConverter.LocalDateTimeToString.class)
    private LocalDateTime createdTime;
    @DynamoDBTypeConverted(converter = DynamoDbConverter.LocalDateTimeToString.class)
    private LocalDateTime updatedTime;

    // OneToOne relationship cannot be directly applied in DynamoDB; so assign these nested objects as DynamoDBDocuments.
    // A viable solution but maybe not a decent solution.
    private PDFReportEntity pdfReport;
    private ExcelReportEntity excelReport;

    public PDFReportEntity getPdfReport() {
        return pdfReport;
    }

    public void setPdfReport(PDFReportEntity pdfReport) {
        this.pdfReport = pdfReport;
    }

    public ExcelReportEntity getExcelReport() {
        return excelReport;
    }

    public void setExcelReport(ExcelReportEntity excelReport) {
        this.excelReport = excelReport;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
