package com.coastware.agenda_medicav2.dto;

import lombok.Data;

import java.util.List;

@Data
public class SlotsAgendaDTO {
    private Long profissionalEspecialidadeId;
    private List<String> diasSemanas; // ["SEG", "TER", "QUA"]
    private List<String> horarios; // ["08:00", "09:00", "10:00"]
}