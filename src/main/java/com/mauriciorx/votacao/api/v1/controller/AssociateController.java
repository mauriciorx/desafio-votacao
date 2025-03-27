package com.mauriciorx.votacao.api.v1.controller;

import com.mauriciorx.votacao.api.v1.dto.request.AssociateRequestDTO;
import com.mauriciorx.votacao.api.v1.service.IAssociateService;
import com.mauriciorx.votacao.api.v1.swagger.AssociateSwagger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/associate")
@RequiredArgsConstructor
public class AssociateController implements AssociateSwagger {

    private final IAssociateService associateService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody AssociateRequestDTO associateRequestDTO){
        log.info("Recebida requisição para criar um associado: {}", associateRequestDTO);

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(associateService.create(associateRequestDTO));
        } catch (Exception e) {
            log.error("Erro ao criar associado: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{associateId}")
    public ResponseEntity findById(@PathVariable Long associateId){
        log.info("Recebida requisição para buscar associado por ID: {}", associateId);

        try {
            return ResponseEntity.ok().body(associateService.findById(associateId));
        } catch (Exception e) {
            log.error("Erro ao buscar associado por ID: {} - {}", associateId, e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/cpf/{associateCpf}")
    public ResponseEntity findByCpf(@PathVariable String associateCpf){
        log.info("Recebida requisição para buscar associado por CPF: {}", associateCpf);

        try {
            return ResponseEntity.ok().body(associateService.findByCpf(associateCpf));
        } catch (Exception e) {
            log.error("Erro ao buscar associado por CPF: {} - {}", associateCpf, e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity findAll(){
        log.info("Recebida requisição para listar todos os associados");

        try {
            return ResponseEntity.ok().body(associateService.findAll());
        } catch (Exception e) {
            log.error("Erro ao listar associados: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
