package com.coastware.agenda_medicav2.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtendimentoDTO {
    private Long agendamentoId;
    private Long pacienteId;
    private Long profissionalEspecialidadeId;
    private Long slotId;
    private LocalDateTime dataHora;
    private String observacoes;
}