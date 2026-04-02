package com.bxb.hamrahi_app.serviceImpl;

import com.bxb.hamrahi_app.model.AIAnalysis;
import com.bxb.hamrahi_app.repository.AIAnalysisRepository;
import com.bxb.hamrahi_app.response.AIResponseDTO;
import com.bxb.hamrahi_app.service.AIAnalysisService;
import com.bxb.hamrahi_app.service.GeminiService;
import com.bxb.hamrahi_app.util.IncidentCategory;
import com.bxb.hamrahi_app.util.RecommendedAction;
import com.bxb.hamrahi_app.util.SeverityLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AIAnalysisServiceImpl implements AIAnalysisService {

    private final GeminiService geminiService;

    private final AIAnalysisRepository aiAnalysisRepository;

    @Override
    public AIAnalysis analyzeAndSave(MultipartFile file) throws Exception {

        String raw = geminiService.analyzeImage(file);

        AIResponseDTO dto = geminiService.parseResponse(raw);

        AIAnalysis entity = new AIAnalysis();

        entity.setCategory(
                IncidentCategory.valueOf(dto.getIncidentCategory().toUpperCase())
        );

        entity.setSeverity(
                SeverityLevel.valueOf(dto.getSeverityLevel().toUpperCase())
        );

        entity.setRecommendedAction(
                RecommendedAction.valueOf(dto.getRecommendedAction().toUpperCase())
        );

        entity.setConfidenceScore(dto.getConfidenceScore());
        entity.setRawOutput(raw);
        entity.setProcessedAt(LocalDateTime.now());

        entity.setIncident(entity.getIncident());

        return aiAnalysisRepository.save(entity);
    }
}