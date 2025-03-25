package com.mauriciorx.votacao.api.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponseDTO {

    private Long id;

    private Integer time;

    private Long agenda_id;

    private LocalDateTime creationDate;
}
