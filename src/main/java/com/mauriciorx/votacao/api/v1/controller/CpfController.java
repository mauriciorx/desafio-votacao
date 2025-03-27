package com.mauriciorx.votacao.api.v1.controller;

import com.mauriciorx.votacao.api.v1.swagger.CpfSwagger;
import com.mauriciorx.votacao.feign.CpfUtilFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/cpf")
@RequiredArgsConstructor
public class CpfController implements CpfSwagger {

    private final CpfUtilFacade cpfUtilFacade;

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateCpf(){
        log.info("Solicitação para gerar um novo CPF recebida");

        try {
            Map<String, String> generatedCpf = cpfUtilFacade.generateCpf();

            log.info("CPF gerado com sucesso: {}", generatedCpf.get("cpf"));

            return ResponseEntity.ok().body(generatedCpf);
        } catch (Exception e) {
            log.error("Erro ao gerar CPF: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/validate/{cpf}")
    public ResponseEntity<Map<String, String>> validateCpf(@PathVariable String cpf){
        log.info("Solicitação para validar CPF: {}", cpf);

        try {
            Map<String, String> validationResponse = cpfUtilFacade.validateCpf(cpf);

            log.info("Resultado da validação do CPF {}: {}", cpf, validationResponse);

            return ResponseEntity.ok().body(validationResponse);
        } catch (Exception e) {
            log.error("Erro ao validar CPF {}: {}", cpf, e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
