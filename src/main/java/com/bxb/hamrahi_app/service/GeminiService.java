package com.bxb.hamrahi_app.service;

import com.bxb.hamrahi_app.model.AIAnalysis;
import com.bxb.hamrahi_app.response.AIResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GeminiService {

    String analyzeImage(MultipartFile file) throws Exception;

    AIResponseDTO parseResponse(String rawResponse) throws Exception;
}
