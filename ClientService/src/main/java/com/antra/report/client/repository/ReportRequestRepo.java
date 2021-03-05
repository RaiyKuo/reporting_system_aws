package com.antra.report.client.repository;

import com.antra.report.client.entity.ReportRequestEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ReportRequestRepo extends CrudRepository<ReportRequestEntity, String> {
}
