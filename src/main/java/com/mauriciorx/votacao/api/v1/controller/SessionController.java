package com.mauriciorx.votacao.api.v1.controller;

import com.mauriciorx.votacao.api.v1.dto.request.SessionRequestDTO;
import com.mauriciorx.votacao.api.v1.service.impl.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    @PostMapping("/create/{agendaId}")
    public ResponseEntity create(@PathVariable Long agendaId, @RequestBody SessionRequestDTO sessionRequestDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.create(agendaId, sessionRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity findById(@PathVariable Long sessionId){
        try {
            return ResponseEntity.ok().body(sessionService.findById(sessionId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity findAll(){
        try {
            return ResponseEntity.ok().body(sessionService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
