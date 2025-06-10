package com.coastware.agenda_medicav2.dto;

import com.coastware.agenda_medicav2.model.AgendamentoModel.StatusAgendamento;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoDTO {
    private LocalDateTime dataHora;
    private Long pacienteId;
    private Long profissionalEspecialidadeId;
    private StatusAgendamento status; // opcional
}
