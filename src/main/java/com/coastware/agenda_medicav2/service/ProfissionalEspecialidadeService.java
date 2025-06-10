package com.coastware.agenda_medicav2.service;

import com.coastware.agenda_medicav2.model.ProfissionalEspecialidadeModel;
import com.coastware.agenda_medicav2.repository.ProfissionalEspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalEspecialidadeService {

    @Autowired
    private ProfissionalEspecialidadeRepository profissionalEspecialidadeRepository;

    public ProfissionalEspecialidadeModel criar(ProfissionalEspecialidadeModel profissionalEspecialidade) {
        return profissionalEspecialidadeRepository.save(profissionalEspecialidade);
    }

    public List<ProfissionalEspecialidadeModel> listarTodos() {
        return profissionalEspecialidadeRepository.findAll();
    }

    public Optional<ProfissionalEspecialidadeModel> buscarPorId(Long id) {
        return profissionalEspecialidadeRepository.findById(id);
    }

    public Optional<ProfissionalEspecialidadeModel> atualizar(
            Long id,
            ProfissionalEspecialidadeModel profissionalEspecialidadeAtualizada) {

        return profissionalEspecialidadeRepository.findById(id).map(pe -> {
            pe.setProfissional(profissionalEspecialidadeAtualizada.getProfissional());
            pe.setEspecialidade(profissionalEspecialidadeAtualizada.getEspecialidade());
            return profissionalEspecialidadeRepository.save(pe);
        });
    }

    public void deletar(Long id) {
        profissionalEspecialidadeRepository.deleteById(id);
    }
}