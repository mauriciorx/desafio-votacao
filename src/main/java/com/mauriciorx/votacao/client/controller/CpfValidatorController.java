package com.mauriciorx.votacao.client.controller;

import com.mauriciorx.votacao.client.dto.CpfValidatorDTO;
import com.mauriciorx.votacao.client.enums.CpfValidatorEnum;
import com.mauriciorx.votacao.client.service.CpfValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private CpfValidatorService cpfValidatorService;

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateCpf(){
        try {
            Map<String, String> response = Map.of("cpf", cpfValidatorService.generateCpf());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/validate/{cpf}")
    public ResponseEntity<Map<String, String>> validateCpf(@PathVariable String cpf){
        try {
            CpfValidatorDTO cpfValidatorDTO = cpfValidatorService.validateCpf( cpf );

            Map<String, String> responseBody = Map.of("status", cpfValidatorDTO.getCpfValidatorEnum().name());

            if(cpfValidatorDTO.getCpfValidatorEnum() == CpfValidatorEnum.UNABLE_TO_VOTE) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);

            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


}
