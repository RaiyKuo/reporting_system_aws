package com.antra.evaluation.reporting_system.endpoint;

import com.antra.evaluation.reporting_system.pojo.api.PDFRequest;
import com.antra.evaluation.reporting_system.pojo.api.PDFResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PDFGenerationControllerTest {

    PDFGenerationController pdfGenerationController;
    ResponseEntity<PDFResponse> responseResponseEntity;

    static PDFRequest pdfRequest;

    static String UUID_SAMPLE = "Req-"+ "abd31def-00ba-45f0-a06f-a8b6f37a4a21";

    @Autowired
    public PDFGenerationControllerTest(PDFGenerationController pdfGenerationController) {
        this.pdfGenerationController = pdfGenerationController;
    }

    @BeforeAll
    static void generatePDFRequestInput(){
        pdfRequest = new PDFRequest();
        pdfRequest.setReqId(UUID_SAMPLE);
        pdfRequest.setHeaders(List.of("Id","Name","Age"));
        pdfRequest.setData(List.of(List.of("1","Dd","23"),List.of("2","AJ","32")));
        pdfRequest.setDescription("This is a test");
        pdfRequest.setSubmitter("class PDFGenerationControllerTest");
    }

    /**
     * Test PDFService endpoint "/pdf"; Feed a fix request and check response with Http.Status and if ReqId matched.
     */
    @Test
    void createPDF() {
        responseResponseEntity = pdfGenerationController.createPDF(pdfRequest);
        assertEquals(HttpStatus.OK, responseResponseEntity.getStatusCode());
        PDFResponse response = responseResponseEntity.getBody();
        assertNotNull(response);
        assertAll("Check response is correct",
                ()->assertFalse(response.isFailed()),
                ()->assertEquals(UUID_SAMPLE, response.getReqId())
        );
        System.out.println(response.getFileLocation());
    }
}