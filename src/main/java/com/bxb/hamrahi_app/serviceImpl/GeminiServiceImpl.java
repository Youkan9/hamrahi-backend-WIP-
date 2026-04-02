package com.bxb.hamrahi_app.serviceImpl;

import com.bxb.hamrahi_app.response.AIResponseDTO;
import com.bxb.hamrahi_app.service.GeminiService;
import com.bxb.hamrahi_app.util.ImageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String analyzeImage(MultipartFile file) throws IOException {

        String base64Image = ImageUtil.compressAndEncode(file.getBytes());

        String prompt = """
        Classify image. JSON only:
        {
        "IncidentCategory":"ROAD|CIVIC|SANITATION|CONSTRUCTION|FIRE|CRIME|UTILITY|OTHER",
        "SeverityLevel":"LOW|MEDIUM|HIGH|CRITICAL",
        "RecommendedAction":"DISPATCH_POLICE|DISPATCH_AMBULANCE|MUNICIPAL_CORPORATION|DISPATCH_FIRE_BRIGADE|NO_ACTION",
        "confidence_score":0.0
        }
        """;

        Map<String, Object> textPart = Map.of("text", prompt);

        Map<String, Object> imagePart = Map.of(
                "inline_data", Map.of(
                        "mime_type", "image/jpeg",
                        "data", base64Image
                )
        );

        Map<String, Object> content = Map.of(
                "parts", List.of(textPart, imagePart)
        );

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(content)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-goog-api-key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }

    @Override
    public AIResponseDTO parseResponse(String rawResponse) throws Exception {

        String cleaned = rawResponse
                .replace("```json", "")
                .replace("```", "")
                .trim();

        return objectMapper.readValue(cleaned, AIResponseDTO.class);
    }
}
