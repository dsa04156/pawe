package com.sslab.pawe.controller;

import com.sslab.pawe.service.PaweService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class PaweController {

    private final PaweService paweService;

    public PaweController(PaweService paweService) {
        this.paweService = paweService;
    }

    @PostMapping("/prompt")
    public ResponseEntity<String> handlePrompt(@RequestBody String input) {
        String result = paweService.handlePrompt(input);
        return ResponseEntity.ok(result);
    }
}