package com.coastware.agenda_medicav2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoModel {

    public enum StatusAgendamento {
        AGENDADO, CONFIRMADO, CANCELADO, REALIZADO, NAO_COMPARECEU
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacientesModel paciente;

    @ManyToOne
    @JoinColumn(name = "profissional_especialidade_id", nullable = false)
    private ProfissionalEspecialidadeModel profissionalEspecialidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('AGENDADO','CONFIRMADO','CANCELADO','REALIZADO','NAO_COMPARECEU')")
    private StatusAgendamento status = StatusAgendamento.AGENDADO;

    @Column(name = "data_criacao", updatable = false, insertable = false)
    private LocalDateTime dataCriacao;
}