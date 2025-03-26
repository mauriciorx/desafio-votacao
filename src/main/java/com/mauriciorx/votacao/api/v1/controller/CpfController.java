package com.mauriciorx.votacao.api.v1.controller;

import com.mauriciorx.votacao.feign.CpfUtilFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/cpf")
@RequiredArgsConstructor
public class CpfController {

    private final CpfUtilFacade cpfUtilFacade;

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateCpf(){
        try {
            return ResponseEntity.ok().body(cpfUtilFacade.generateCpf());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/validate/{cpf}")
    public ResponseEntity<Map<String, String>> validateCpf(@PathVariable String cpf){
        try {
            return ResponseEntity.ok().body(cpfUtilFacade.validateCpf(cpf));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
