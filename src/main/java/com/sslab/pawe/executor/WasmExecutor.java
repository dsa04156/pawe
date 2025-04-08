package com.sslab.pawe.executor;

import io.github.kawamuray.wasmtime.Engine;
import io.github.kawamuray.wasmtime.Module;
import io.github.kawamuray.wasmtime.Instance;
import io.github.kawamuray.wasmtime.Func;
import io.github.kawamuray.wasmtime.Store;
import io.github.kawamuray.wasmtime.wasi.WasiCtx;
import io.github.kawamuray.wasmtime.wasi.WasiCtxBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class WasmExecutor {

    private final String wasmPath;

    public WasmExecutor(String wasmPath) {
        this.wasmPath = wasmPath;
    }

    /**
     * 입력 문자열을 임시 파일에 기록하여 WASI 컨텍스트를 구성한 후,
     * WASM 모듈을 로드하고 "generate_response" 함수를 호출합니다.
     *
     * @param input 사용자 입력 문자열
     * @return 실행 결과 메시지
     */
    public String executeWasm(String input) {
        try {
            // 1. 엔진 생성
            Engine engine = new Engine();

            // 2. 입력 문자열을 임시 파일에 기록
            Path tempInputPath = Files.createTempFile("wasm-input", ".txt");
            Files.write(tempInputPath, input.getBytes(StandardCharsets.UTF_8));

            // 3. WASI 컨텍스트 구성: 표준 입력은 임시 파일, stdout/stderr 상속
            WasiCtx wasiCtx = new WasiCtxBuilder()
                    .stdin(tempInputPath)
                    .inheritStdout()
                    .inheritStderr()
                    .build();

            // 4. Store 생성 (사용자 데이터 없이 WASI 컨텍스트 포함)
            Store<Void> store = Store.withoutData(wasiCtx);

            // 5. WASM 모듈 로딩
            Module module = Module.fromFile(engine, wasmPath);

            // 6. Instance 인스턴스 생성 (빈 import 목록 전달)
            Instance instance = new Instance(store, module, Collections.emptyList());

            // 7. 내보낸 "generate_response" 함수 가져오기 및 호출
            Func func = instance.getFunc(store, "generate_response")
                    .orElseThrow(() -> new RuntimeException("Function 'generate_response' not found"));
            func.call(store);

            return "✅ WASM 실행 성공";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ WASM 실행 실패: " + e.getMessage();
        }
    }
}
