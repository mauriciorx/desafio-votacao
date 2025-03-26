package com.mauriciorx.votacao.api.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteOutcomeResponseDTO {

    private String title;
    private String description;
    private String outcome;

    private Long approvedVotes;
    private Long rejectedVotes;
    private Long overall;

}
