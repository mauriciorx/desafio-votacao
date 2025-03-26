package com.mauriciorx.votacao.api.v1.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "CPF", description = "API para geração e validação de CPFs")
public interface CpfSwagger {

    @Operation(summary = "Gera um CPF válido", description = "Endpoint para gerar um CPF aleatório e válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "CPF gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao gerar o CPF")
    })
    @PostMapping("/generate")
    ResponseEntity<Map<String, String>> generateCpf();

    @Operation(summary = "Valida um CPF", description = "Verifica se um CPF fornecido é válido e se está habilitado a votar.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "CPF validado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao validar o CPF")
    })
    @PostMapping("/validate/{cpf}")
    ResponseEntity<Map<String, String>> validateCpf(@Parameter(description = "CPF a ser validado", required = true) @PathVariable String cpf);
}