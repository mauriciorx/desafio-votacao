package com.mauriciorx.votacao.api.v1.controller;

import com.mauriciorx.votacao.api.v1.dto.request.SessionRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.request.VoteRequestDTO;
import com.mauriciorx.votacao.api.v1.service.ISessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

    @Autowired
    private ISessionService sessionService;

    @PostMapping("/vote")
    public ResponseEntity vote(@RequestBody VoteRequestDTO voteRequestDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.vote(voteRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody SessionRequestDTO sessionRequestDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.create(sessionRequestDTO));
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
