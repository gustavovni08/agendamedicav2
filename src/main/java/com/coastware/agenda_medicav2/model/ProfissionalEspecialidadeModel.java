package com.coastware.agenda_medicav2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "profissionais_especialidades") // Nome da tabela no banco de dados
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalEspecialidadeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionaisModel profissional;

    @ManyToOne
    @JoinColumn(name = "especialidade_id", nullable = false)
    private EspecialidadeModel especialidade;


}
