package com.antra.evaluation.reporting_system.repo;

import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExcelRepository extends JpaRepository<ExcelFile, String> {
}
