package com.mauriciorx.votacao.api.v1.controller;

import com.mauriciorx.votacao.api.v1.dto.request.AgendaRequestDTO;
import com.mauriciorx.votacao.api.v1.service.IAgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/agenda")
@RequiredArgsConstructor
public class AgendaController {

    private IAgendaService agendaService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody AgendaRequestDTO agendaRequestDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(agendaService.create(agendaRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{agendaId}")
    public ResponseEntity findById(@PathVariable Long agendaId){
        try {
            return ResponseEntity.ok().body(agendaService.findById(agendaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity findAll(){
        try {
            return ResponseEntity.ok().body(agendaService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/outcome/{agendaId}")
    public ResponseEntity generateVoteOutcome(@PathVariable Long agendaId){
        try {
            return ResponseEntity.ok().body(agendaService.generateVoteOutcome(agendaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
