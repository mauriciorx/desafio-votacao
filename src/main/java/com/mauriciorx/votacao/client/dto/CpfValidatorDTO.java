package com.mauriciorx.votacao.client.dto;

import com.mauriciorx.votacao.client.enums.CpfValidatorEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CpfValidatorDTO {
    private CpfValidatorEnum cpfValidatorEnum;
}
