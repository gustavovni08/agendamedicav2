package com.coastware.agenda_medicav2.repository;

import com.coastware.agenda_medicav2.model.AgendamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<AgendamentoModel, Long> {
}
