package com.mauriciorx.votacao.api.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociateResponseDTO {

    private Long id;
    private String name;
    private String cpf;
}
