package com.coastware.agenda_medicav2.repository;

import com.coastware.agenda_medicav2.model.PacientesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacientesRpository extends JpaRepository<PacientesModel, Long> {
}
