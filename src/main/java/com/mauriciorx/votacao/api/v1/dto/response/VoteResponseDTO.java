package com.mauriciorx.votacao.api.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponseDTO {

    private Long id;
    private Long sessionId;
    private Long associateId;

    private String voteResult;
}
