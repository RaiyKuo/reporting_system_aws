package com.antra.evaluation.reporting_system.endpoint;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.ExcelResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExcelGenerationControllerTest {

    ExcelGenerationController excelGenerationController;
    ResponseEntity<ExcelResponse> responseEntity;

    static ExcelRequest excelRequest;

    static String UUID_SAMPLE = "Req-" + "abd31def-00ba-45f0-a06f-a8b6f37a4a21";


    @Autowired
    public ExcelGenerationControllerTest(ExcelGenerationController excelGenerationController) {
        this.excelGenerationController = excelGenerationController;
    }

    @BeforeAll
    static void generatePDFRequestInput() {
        excelRequest = new ExcelRequest();
        excelRequest.setReqId(UUID_SAMPLE);
        excelRequest.setHeaders(List.of("Id", "Name", "Age"));
        excelRequest.setData(List.of(List.of("1", "Dd", "23"), List.of("2", "AJ", "32")));
        excelRequest.setDescription("This is a test");
        excelRequest.setSubmitter("class PDFGenerationControllerTest");
    }

    /**
     * Test ExcelService endpoint "/excel"; Feed a fix request and check response with Http.Status and if ReqId matched.
     */
    @Test
    void createExcel() {
        responseEntity = excelGenerationController.createExcel(excelRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ExcelResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertAll("Check response is correct",
                () -> assertFalse(response.isFailed()),
                () -> assertEquals(UUID_SAMPLE, response.getReqId())
        );
        System.out.println(response.getFileLocation());
    }

    @Test
    @Disabled
    void downloadExcel() {
    }
}