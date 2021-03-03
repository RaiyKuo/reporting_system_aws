package com.antra.evaluation.reporting_system.repo;

import com.antra.evaluation.reporting_system.pojo.report.PDFFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PDFRepository extends JpaRepository<PDFFile, String> {
}