package com.bxb.hamrahi_app.service;

import com.bxb.hamrahi_app.model.AIAnalysis;
import org.springframework.web.multipart.MultipartFile;

public interface AIAnalysisService {

    AIAnalysis analyzeAndSave(MultipartFile file) throws Exception;
}
