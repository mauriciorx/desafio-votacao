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
        return ResponseEntity.status(HttpStatus.CREATED).body(associateService.create(associateRequestDTO));
    }

    @GetMapping("/{associateId}")
    public ResponseEntity findById(@PathVariable Long associateId){
        log.info("Recebida requisição para buscar associado por ID: {}", associateId);
        return ResponseEntity.ok().body(associateService.findById(associateId));
    }

    @GetMapping("/cpf/{associateCpf}")
    public ResponseEntity findByCpf(@PathVariable String associateCpf){
        log.info("Recebida requisição para buscar associado por CPF: {}", associateCpf);
        return ResponseEntity.ok().body(associateService.findByCpf(associateCpf));
    }

    @GetMapping("/")
    public ResponseEntity findAll(){
        log.info("Recebida requisição para listar todos os associados");
        return ResponseEntity.ok().body(associateService.findAll());
    }
}
