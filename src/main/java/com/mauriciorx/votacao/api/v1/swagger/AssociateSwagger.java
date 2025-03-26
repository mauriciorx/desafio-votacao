package com.mauriciorx.votacao.api.v1.swagger;

import com.mauriciorx.votacao.api.v1.dto.request.AssociateRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Associado", description = "API para gerenciamento de associados")
public interface AssociateSwagger {

    @Operation(summary = "Cria um novo associado", description = "Endpoint para criar um novo associado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Associado criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar o associado")
    })
    @PostMapping("/create")
    ResponseEntity create(@RequestBody AssociateRequestDTO associateRequestDTO);

    @Operation(summary = "Busca um associado por ID", description = "Retorna os detalhes de um associado específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Associado encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar o associado")
    })
    @GetMapping("/{associateId}")
    ResponseEntity findById(@Parameter(description = "ID do associado a ser buscado", required = true) @PathVariable Long associateId);

    @Operation(summary = "Busca um associado por CPF", description = "Retorna os detalhes de um associado pelo CPF.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Associado encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar o associado pelo CPF")
    })
    @GetMapping("/cpf/{associateCpf}")
    ResponseEntity findByCpf(@Parameter(description = "CPF do associado a ser buscado", required = true) @PathVariable String associateCpf);

    @Operation(summary = "Lista todos os associados", description = "Retorna uma lista de todos os associados cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de associados retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar associados")
    })
    @GetMapping("/")
    ResponseEntity findAll();
}
