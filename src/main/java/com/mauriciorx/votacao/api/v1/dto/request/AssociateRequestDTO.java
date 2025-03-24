package com.mauriciorx.votacao.api.v1.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociateRequestDTO {

    private String name;
    private String cpf;
}
