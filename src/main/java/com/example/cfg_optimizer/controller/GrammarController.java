package com.example.cfg_optimizer.controller;

import com.example.cfg_optimizer.service.GrammarOptimizerService;
import com.example.cfg_optimizer.dto.GrammarRequest;
import com.example.cfg_optimizer.dto.GrammarResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrammarController {

    private final GrammarOptimizerService optimizerService;

    public GrammarController(GrammarOptimizerService optimizerService) {
        this.optimizerService = optimizerService;
    }

    @PostMapping("/optimize")
    public GrammarResponse optimizeGrammar(@Valid @RequestBody GrammarRequest grammarRequest) {
        return optimizerService.optimizeGrammar(grammarRequest);
    }
}
