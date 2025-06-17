package com.coastware.agenda_medicav2.repository;

import com.coastware.agenda_medicav2.model.AgendamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;



public interface AgendamentoRepository extends JpaRepository<AgendamentoModel, Long> {
    List<AgendamentoModel> findByProfissionalEspecialidadeIdAndStatusInAndDataHoraBetween(
            Long peId,
            List<AgendamentoModel.StatusAgendamento> statuses,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    @Query("SELECT COUNT(a) FROM AgendamentoModel a WHERE a.profissionalEspecialidade.profissional.id = :profissionalId")
    Long countByProfissionalId(@Param("profissionalId") Long profissionalId);

    @Query("SELECT a FROM AgendamentoModel a " +
            "WHERE a.profissionalEspecialidade.profissional.id = :profissionalId " +
            "AND CAST(a.dataHora AS date) = :data")
    List<AgendamentoModel> findByProfissionalIdAndData(
            @Param("profissionalId") Long profissionalId,
            @Param("data") LocalDateTime data);


}
