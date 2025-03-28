package com.mauriciorx.votacao.api.v1.controller;

import com.mauriciorx.votacao.api.v1.dto.request.AgendaRequestDTO;
import com.mauriciorx.votacao.api.v1.service.IAgendaService;
import com.mauriciorx.votacao.api.v1.swagger.AgendaSwagger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/agenda")
@RequiredArgsConstructor
public class AgendaController implements AgendaSwagger {

    private final IAgendaService agendaService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody AgendaRequestDTO agendaRequestDTO){
        log.info("Recebida requisição para criar uma pauta: {}", agendaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaService.create(agendaRequestDTO));
    }

    @GetMapping("/{agendaId}")
    public ResponseEntity findById(@PathVariable Long agendaId){
        log.info("Recebida requisição para buscar pauta ID: {}", agendaId);
        return ResponseEntity.ok().body(agendaService.findById(agendaId));
    }

    @GetMapping("/")
    public ResponseEntity findAll(){
        log.info("Recebida requisição para listar todos as pautas");
        return ResponseEntity.ok().body(agendaService.findAll());
    }

    @GetMapping("/outcome/{agendaId}")
    public ResponseEntity generateVoteOutcome(@PathVariable Long agendaId){
        log.info("Recebida requisição para gerar resultado dos votos da pauta ID: {}", agendaId);
        return ResponseEntity.ok().body(agendaService.generateVoteOutcome(agendaId));
    }
}
