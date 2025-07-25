package service.desicion.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.desicion.dto.ScoringInfo;
import service.desicion.services.DecisionService;

@RestController
@RequestMapping("/api/decision")
public class DecisionController {

    private final DecisionService decisionService;

    public DecisionController(DecisionService decisionService) {
        this.decisionService = decisionService;
    }

    @PostMapping("/result")
    @ResponseStatus(HttpStatus.OK)
    public void makeDecision(@RequestBody ScoringInfo scoringInfo) {
        decisionService.decision(scoringInfo);
    }
}
