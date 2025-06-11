package com.coastware.agenda_medicav2.repository;

import com.coastware.agenda_medicav2.model.AgendamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;



public interface AgendamentoRepository extends JpaRepository<AgendamentoModel, Long> {
    List<AgendamentoModel> findByProfissionalEspecialidadeIdAndStatusInAndDataHoraBetween(
            Long peId,
            List<AgendamentoModel.StatusAgendamento> statuses,
            LocalDateTime inicio,
            LocalDateTime fim
    );
}
