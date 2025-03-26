package com.mauriciorx.votacao.api.v1.swagger;

import com.mauriciorx.votacao.api.v1.dto.request.SessionRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.request.VoteRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Sessão", description = "API para gerenciamento de sessões de votação")
public interface SessionSwagger {

    @Operation(summary = "Registra um voto", description = "Endpoint para registrar um voto em uma sessão.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao registrar o voto")
    })
    @PostMapping("/vote")
    ResponseEntity vote(@RequestBody VoteRequestDTO voteRequestDTO);

    @Operation(summary = "Cria uma nova sessão", description = "Endpoint para criar uma nova sessão de votação.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sessão criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar a sessão")
    })
    @PostMapping("/create")
    ResponseEntity create(@RequestBody SessionRequestDTO sessionRequestDTO);

    @Operation(summary = "Busca uma sessão por ID", description = "Retorna os detalhes de uma sessão específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sessão encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar a sessão")
    })
    @GetMapping("/{sessionId}")
    ResponseEntity findById(@Parameter(description = "ID da sessão a ser buscada", required = true) @PathVariable Long sessionId);

    @Operation(summary = "Lista todas as sessões", description = "Retorna uma lista de todas as sessões cadastradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de sessões retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar sessões")
    })
    @GetMapping("/")
    ResponseEntity findAll();
}
