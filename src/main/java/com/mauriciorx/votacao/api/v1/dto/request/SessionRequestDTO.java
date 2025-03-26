package com.mauriciorx.votacao.api.v1.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionRequestDTO {

    private Integer time;

    private Long agendaId;

    @Schema(hidden = true)
    private LocalDateTime creationDate;
}
