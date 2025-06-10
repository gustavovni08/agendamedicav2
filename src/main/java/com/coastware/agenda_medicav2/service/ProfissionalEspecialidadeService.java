package com.coastware.agenda_medicav2.service;

import com.coastware.agenda_medicav2.dto.ProfissionalEspecialidadeDTO;
import com.coastware.agenda_medicav2.model.ProfissionalEspecialidadeModel;
import com.coastware.agenda_medicav2.repository.EspecialidadeRepository;
import com.coastware.agenda_medicav2.repository.ProfissionaisRepository;
import com.coastware.agenda_medicav2.repository.ProfissionalEspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalEspecialidadeService {

    @Autowired
    private ProfissionalEspecialidadeRepository profissionalEspecialidadeRepository;

    @Autowired
    private ProfissionaisRepository profissionaisRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Transactional
    public ProfissionalEspecialidadeModel criar(ProfissionalEspecialidadeDTO dto) {
        ProfissionalEspecialidadeModel model = new ProfissionalEspecialidadeModel();

        // Carrega as entidades completas a partir dos IDs
        model.setProfissional(
                profissionaisRepository.findById(dto.getProfissionalId())
                        .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"))
        );

        model.setEspecialidade(
                especialidadeRepository.findById(dto.getEspecialidadeId())
                        .orElseThrow(() -> new IllegalArgumentException("Especialidade não encontrada"))
        );

        return profissionalEspecialidadeRepository.save(model);
    }

    @Transactional
    public Optional<ProfissionalEspecialidadeModel> atualizar(
            Long id,
            ProfissionalEspecialidadeDTO dto) {

        return profissionalEspecialidadeRepository.findById(id).map(pe -> {
            if(dto.getProfissionalId() != null) {
                pe.setProfissional(
                        profissionaisRepository.findById(dto.getProfissionalId())
                                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"))
                );
            }

            if(dto.getEspecialidadeId() != null) {
                pe.setEspecialidade(
                        especialidadeRepository.findById(dto.getEspecialidadeId())
                                .orElseThrow(() -> new IllegalArgumentException("Especialidade não encontrada"))
                );
            }

            return profissionalEspecialidadeRepository.save(pe);
        });
    }


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