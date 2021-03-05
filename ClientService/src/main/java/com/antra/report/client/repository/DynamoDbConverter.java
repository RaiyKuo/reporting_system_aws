package com.antra.report.client.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.antra.report.client.entity.ReportStatus;

import java.time.LocalDateTime;

public class DynamoDbConverter {
    static public class LocalDateTimeToString implements DynamoDBTypeConverter<String, LocalDateTime> {

        @Override
        public String convert(final LocalDateTime time) {
            return time.toString();
        }

        @Override
        public LocalDateTime unconvert(final String stringValue) {
            return LocalDateTime.parse(stringValue);
        }
    }

    static public class ReportStatusToString implements DynamoDBTypeConverter<String, ReportStatus> {

        @Override
        public String convert(ReportStatus reportStatus) {
            return reportStatus.toString();
        }

        @Override
        public ReportStatus unconvert(String string) {
            return ReportStatus.valueOf(string);
        }
    }

}
