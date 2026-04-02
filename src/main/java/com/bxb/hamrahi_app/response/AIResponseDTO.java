package com.bxb.hamrahi_app.response;

import lombok.Data;

@Data
public class AIResponseDTO {


        private String incidentId;
        private String incidentCategory;
        private String severityLevel;
        private String recommendedAction;
        private Double confidenceScore;
}

