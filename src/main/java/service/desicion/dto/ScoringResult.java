package service.desicion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScoringResult {
    private Boolean decisionStatus;
    private String rejectionReason;
    private Integer totalScore;
}
