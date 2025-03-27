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

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(agendaService.create(agendaRequestDTO));
        } catch (Exception e) {
            log.error("Erro ao criar pauta: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{agendaId}")
    public ResponseEntity findById(@PathVariable Long agendaId){
        log.info("Recebida requisição para buscar pauta ID: {}", agendaId);

        try {
            return ResponseEntity.ok().body(agendaService.findById(agendaId));
        } catch (Exception e) {
            log.error("Erro ao buscar pauta ID: {} - {}", agendaId, e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity findAll(){
        log.info("Recebida requisição para listar todos as pautas");

        try {
            return ResponseEntity.ok().body(agendaService.findAll());
        } catch (Exception e) {
            log.error("Erro ao listar pautas: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/outcome/{agendaId}")
    public ResponseEntity generateVoteOutcome(@PathVariable Long agendaId){
        log.info("Recebida requisição para gerar resultado dos votos da pauta ID: {}", agendaId);

        try {
            return ResponseEntity.ok().body(agendaService.generateVoteOutcome(agendaId));
        } catch (Exception e) {
            log.error("Erro ao gerar resultado dos votos para a pauta ID: {} : {}", agendaId, e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
