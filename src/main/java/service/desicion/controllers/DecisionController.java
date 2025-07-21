package service.desicion.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.desicion.dto.ScoringInfo;
import service.desicion.dto.ScoringResult;
import service.desicion.services.DecisionService;

@RestController
@RequestMapping("/api/decision")
public class DecisionController {

    private final DecisionService decisionService;

    public DecisionController(DecisionService decisionService) {
        this.decisionService = decisionService;
    }

    @PostMapping("/response")
    public ResponseEntity<?> makeDecision(@RequestBody ScoringInfo scoringInfo) {
        ScoringResult result = decisionService.decision(scoringInfo);

        return ResponseEntity.ok(result);
    }
}
