package com.mauriciorx.votacao.api.v1.controller;

import com.mauriciorx.votacao.api.v1.dto.request.SessionRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.request.VoteRequestDTO;
import com.mauriciorx.votacao.api.v1.service.ISessionService;
import com.mauriciorx.votacao.api.v1.swagger.SessionSwagger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController implements SessionSwagger {

    private final ISessionService sessionService;

    @PostMapping("/vote")
    public ResponseEntity vote(@RequestBody VoteRequestDTO voteRequestDTO){
        log.info("Recebendo solicitação de voto: {}", voteRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.vote(voteRequestDTO));
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody SessionRequestDTO sessionRequestDTO){
        log.info("Recebendo solicitação para criar sessão: {}", sessionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.create(sessionRequestDTO));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity findById(@PathVariable Long sessionId){
        log.info("Buscando sessão por ID: {}", sessionId);
        return ResponseEntity.ok().body(sessionService.findById(sessionId));
    }

    @GetMapping("/")
    public ResponseEntity findAll(){
        log.info("Buscando todas as sessões");
        return ResponseEntity.ok().body(sessionService.findAll());
    }
}
