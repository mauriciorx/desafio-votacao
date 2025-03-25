package com.mauriciorx.votacao.api.v1.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequestDTO {

    private Long sessionId;
    private Long associateId;

    private boolean voteApproved;
}
