package com.sslab.pawe.executor;

import io.github.kawamuray.wasmtime.*;

public class WasmExecutor {

    public String executeWasm(String wasmPath, String input) {
        try (Engine engine = new Engine();
             Store<Void> store = new Store<>(engine);
             Module module = Module.fromFile(engine, wasmPath);
             Linker linker = new Linker(engine)) {

            WasiConfig wasiConfig = new WasiConfig()
                    .inheritStdout()
                    .inheritStderr()
                    .stdinString(input);
            WasiCtx wasiCtx = new WasiCtx(wasiConfig);
            linker.defineWasi();

            Instance instance = linker.instantiate(store, module);
            Func func = instance.getFunc(store, "generate_response");
            func.call(store);

            return "✅ WASM 실행 성공";

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ WASM 실행 실패: " + e.getMessage();
        }
    }
}
