package com.mauriciorx.votacao.api.v1.controller;

import com.mauriciorx.votacao.api.v1.dto.request.AssociateRequestDTO;
import com.mauriciorx.votacao.api.v1.service.IAssociateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/associate")
@RequiredArgsConstructor
public class AssociateController {

    private final IAssociateService associateService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody AssociateRequestDTO associateRequestDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(associateService.create(associateRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{associateId}")
    public ResponseEntity findById(@PathVariable Long associateId){
        try {
            return ResponseEntity.ok().body(associateService.findById(associateId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/cpf/{associateCpf}")
    public ResponseEntity findByCpf(@PathVariable String associateCpf){
        try {
            return ResponseEntity.ok().body(associateService.findByCpf(associateCpf));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity findAll(){
        try {
            return ResponseEntity.ok().body(associateService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
