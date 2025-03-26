package com.mauriciorx.votacao.api.v1.swagger;

import com.mauriciorx.votacao.api.v1.dto.request.AgendaRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Pauta", description = "API para gerenciamento de pautas")
public interface AgendaSwagger {

    @Operation(summary = "Cria uma nova pauta", description = "Endpoint para criar uma nova pauta.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar a pauta")
    })
    @PostMapping("/create")
    ResponseEntity create(@RequestBody AgendaRequestDTO agendaRequestDTO);

    @Operation(summary = "Busca uma pauta por ID", description = "Retorna os detalhes de uma pauta específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pauta encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar a pauta")
    })
    @GetMapping("/{agendaId}")
    ResponseEntity findById(@Parameter(description = "ID da pauta a ser buscada", required = true) @PathVariable Long agendaId);

    @Operation(summary = "Lista todas as pautas", description = "Retorna uma lista de todas as pautas cadastradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pautas retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar pautas")
    })
    @GetMapping("/")
    ResponseEntity findAll();

    @Operation(summary = "Gera o resultado da votação", description = "Gera e retorna o resultado da votação para uma pauta específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao gerar resultado da votação")
    })
    @GetMapping("/outcome/{agendaId}")
    ResponseEntity generateVoteOutcome(@Parameter(description = "ID da pauta para geração do resultado", required = true) @PathVariable Long agendaId);
}

