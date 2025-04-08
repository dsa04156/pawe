package com.sslab.pawe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaweController {

    @PostMapping("/prompt")
    public ResponseEntity<String> handlePrompt(@RequestBody String input) {
        // 실제 wasmExecutor 연동 예정
        return ResponseEntity.ok("입력 수신됨: " + input);
    }
}
