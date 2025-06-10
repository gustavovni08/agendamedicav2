package com.coastware.agenda_medicav2.repository;

import com.coastware.agenda_medicav2.model.SlotsAgendaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SlotsAgendaRepository extends JpaRepository<SlotsAgendaModel, Long> {
    @Query("SELECT s FROM SlotsAgendaModel s WHERE s.profissionalEspecialidade.id = :profissionalEspecialidadeId")
    List<SlotsAgendaModel> findByProfissionalEspecialidadeId(@Param("profissionalEspecialidadeId") Long profissionalEspecialidadeId);
}
