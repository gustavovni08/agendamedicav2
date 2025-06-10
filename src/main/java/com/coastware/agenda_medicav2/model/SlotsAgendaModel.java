package com.coastware.agenda_medicav2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "slots_agenda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotsAgendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profissional_especialidade_id", nullable = false)
    private ProfissionalEspecialidadeModel profissionalEspecialidade;

    @Column(name = "dias_semanas", nullable = false, length = 255)
    private String diasSemanas; // Ex: "SEG,TER,QUA"

    @Column(name = "horarios", nullable = false, length = 255)
    private String horarios; // Ex: "08:00,09:00,10:00"
}