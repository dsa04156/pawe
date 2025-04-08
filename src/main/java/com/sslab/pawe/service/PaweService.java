package com.sslab.pawe.service;

import com.sslab.pawe.executor.WasmExecutor;
import org.springframework.stereotype.Service;

@Service
public class PaweService {

    private final WasmExecutor wasmExecutor = new WasmExecutor("/wasm/pawe_wasm.wasm");

    public String handlePrompt(String input) {
        // WASM 모듈의 경로는 실제 Docker 환경에서의 파일 경로나 절대경로로 수정해야 합니다.
        return wasmExecutor.executeWasm(input);
    }
}
