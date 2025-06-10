package com.coastware.agenda_medicav2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "atendimentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "agendamento_id", nullable = false, unique = true)
    private AgendamentoModel agendamento;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacientesModel paciente;

    @ManyToOne
    @JoinColumn(name = "profissional_especialidade_id", nullable = false)
    private ProfissionalEspecialidadeModel profissionalEspecialidade;


    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(columnDefinition = "TEXT")
    private String observacoes;
}