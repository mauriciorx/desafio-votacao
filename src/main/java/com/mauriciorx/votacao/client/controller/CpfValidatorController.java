package com.mauriciorx.votacao.client.controller;

import com.mauriciorx.votacao.client.service.CpfValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/client/cpf")
@RequiredArgsConstructor
public class CpfValidatorController {

    private final CpfValidatorService cpfValidatorService;

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateCpf(){
        return cpfValidatorService.generateCpf();
    }

    @PostMapping("/validate/{cpf}")
    public ResponseEntity<Map<String, String>> validateCpf(@PathVariable String cpf){
        return cpfValidatorService.validateCpf(cpf);
    }
}
