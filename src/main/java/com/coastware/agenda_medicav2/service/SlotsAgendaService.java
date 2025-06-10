package com.coastware.agenda_medicav2.service;

import com.coastware.agenda_medicav2.dto.SlotsAgendaDTO;
import com.coastware.agenda_medicav2.model.SlotsAgendaModel;
import com.coastware.agenda_medicav2.model.ProfissionalEspecialidadeModel;
import com.coastware.agenda_medicav2.repository.SlotsAgendaRepository;
import com.coastware.agenda_medicav2.repository.ProfissionalEspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SlotsAgendaService {

    @Autowired
    private SlotsAgendaRepository slotsAgendaRepository;

    @Autowired
    private ProfissionalEspecialidadeRepository profissionalEspecialidadeRepository;

    @Transactional
    public SlotsAgendaModel criar(SlotsAgendaDTO dto) {
        SlotsAgendaModel slot = new SlotsAgendaModel();

        // Converter listas para strings separadas por vírgula
        slot.setDiasSemanas(String.join(",", dto.getDiasSemanas()));
        slot.setHorarios(String.join(",", dto.getHorarios()));

        // Carregar a associação profissional-especialidade
        ProfissionalEspecialidadeModel pe = profissionalEspecialidadeRepository.findById(dto.getProfissionalEspecialidadeId())
                .orElseThrow(() -> new IllegalArgumentException("Associação Profissional-Especialidade não encontrada"));

        slot.setProfissionalEspecialidade(pe);

        return slotsAgendaRepository.save(slot);
    }

    public List<SlotsAgendaModel> listarTodos() {
        return slotsAgendaRepository.findAll();
    }

    public Optional<SlotsAgendaModel> buscarPorId(Long id) {
        return slotsAgendaRepository.findById(id);
    }

    @Transactional
    public Optional<SlotsAgendaModel> atualizar(Long id, SlotsAgendaDTO dto) {
        return slotsAgendaRepository.findById(id).map(slot -> {
            if(dto.getDiasSemanas() != null) {
                slot.setDiasSemanas(String.join(",", dto.getDiasSemanas()));
            }

            if(dto.getHorarios() != null) {
                slot.setHorarios(String.join(",", dto.getHorarios()));
            }

            if(dto.getProfissionalEspecialidadeId() != null) {
                ProfissionalEspecialidadeModel pe = profissionalEspecialidadeRepository.findById(dto.getProfissionalEspecialidadeId())
                        .orElseThrow(() -> new IllegalArgumentException("Associação Profissional-Especialidade não encontrada"));
                slot.setProfissionalEspecialidade(pe);
            }

            return slotsAgendaRepository.save(slot);
        });
    }

    public void deletar(Long id) {
        slotsAgendaRepository.deleteById(id);
    }

    // Método para buscar por profissional_especialidade_id
    public List<SlotsAgendaModel> buscarPorProfissionalEspecialidade(Long profissionalEspecialidadeId) {
        return slotsAgendaRepository.findByProfissionalEspecialidadeId(profissionalEspecialidadeId);
    }
}
