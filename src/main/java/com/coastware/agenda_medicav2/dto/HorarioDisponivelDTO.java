package com.coastware.agenda_medicav2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class HorarioDisponivelDTO {
    private LocalDateTime dataHora;
}