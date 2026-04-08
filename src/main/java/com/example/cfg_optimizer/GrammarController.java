package com.example.cfg_optimizer;

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
    public GrammarResponse optimizeGrammar(@RequestBody GrammarRequest grammarRequest) {
        return optimizerService.optimizeGrammar(grammarRequest);
    }
}
