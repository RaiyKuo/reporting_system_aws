package com.antra.report.client.handler;

import com.antra.report.client.controller.WebSocketController;
import com.antra.report.client.pojo.FileType;
import com.antra.report.client.pojo.reponse.SqsResponse;
import com.antra.report.client.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReportSQSListener {

    private static final Logger log = LoggerFactory.getLogger(ReportSQSListener.class);

    private final ReportService reportService;
    private final WebSocketController webSocketController;

    @Autowired
    public ReportSQSListener(ReportService reportService, WebSocketController webSocketController) {
        this.reportService = reportService;
        this.webSocketController = webSocketController;
    }

    @SqsListener("${app.aws.sqs.pdf_queue}")
    public void responseQueueListenerPdf(SqsResponse response) {
        log.info("Get response from sqs : {}", response);
        //queueListener(request.getPdfRequest());
        reportService.updateAsyncFileReport(response, FileType.PDF);
        notifyUpdateToWebSocket("PDF sqs response updated");
    }

    @SqsListener("${app.aws.sqs.excel_queue}")
    public void responseQueueListenerExcel(SqsResponse response) {
        log.info("Get response from sqs : {}", response);
        //queueListener(request.getPdfRequest());
        reportService.updateAsyncFileReport(response, FileType.EXCEL);
        notifyUpdateToWebSocket("EXCEL sqs response updated");
    }

    public void notifyUpdateToWebSocket(String message) {
        try {
            webSocketController.sendMessage(message);
        } catch (IOException e) {
            log.warn("Unable to push notification to websocket.", e);
        }
    }

//    @SqsListener(value = "Excel_Response_Queue", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
//    public void responseQueueListenerExcelManualAcknowledge(SqsResponse response, Acknowledgment ack) {
//        log.info("Get response from sqs : {}", response);
//        log.info("Manually Acknowledge");
//        //queueListener(request.getPdfRequest());
//        reportService.updateAsyncExcelReport(response);
//        ack.acknowledge();
//    }
}
