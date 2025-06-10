package com.coastware.agenda_medicav2.service;

import com.coastware.agenda_medicav2.dto.AtendimentoDTO;
import com.coastware.agenda_medicav2.model.*;
import com.coastware.agenda_medicav2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private PacientesRpository pacientesRepository;

    @Autowired
    private ProfissionalEspecialidadeRepository profissionalEspecialidadeRepository;

    @Autowired
    private SlotsAgendaRepository slotAgendaRepository;

    @Transactional
    public AtendimentoModel criar(AtendimentoDTO dto) {
        AtendimentoModel atendimento = new AtendimentoModel();

        // Carregar entidades relacionadas a partir dos IDs
        AgendamentoModel agendamento = agendamentoRepository.findById(dto.getAgendamentoId())
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));
        atendimento.setAgendamento(agendamento);

        PacientesModel paciente = pacientesRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
        atendimento.setPaciente(paciente);

        ProfissionalEspecialidadeModel pe = profissionalEspecialidadeRepository.findById(dto.getProfissionalEspecialidadeId())
                .orElseThrow(() -> new IllegalArgumentException("Profissional-Especialidade não encontrado"));
        atendimento.setProfissionalEspecialidade(pe);

        atendimento.setDataHora(dto.getDataHora());
        atendimento.setObservacoes(dto.getObservacoes());

        return atendimentoRepository.save(atendimento);
    }

    public List<AtendimentoModel> listarTodos() {
        return atendimentoRepository.findAll();
    }

    public Optional<AtendimentoModel> buscarPorId(Long id) {
        return atendimentoRepository.findById(id);
    }

    @Transactional
    public Optional<AtendimentoModel> atualizar(Long id, AtendimentoDTO dto) {
        return atendimentoRepository.findById(id).map(atendimento -> {
            if (dto.getAgendamentoId() != null) {
                AgendamentoModel agendamento = agendamentoRepository.findById(dto.getAgendamentoId())
                        .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));
                atendimento.setAgendamento(agendamento);
            }

            if (dto.getPacienteId() != null) {
                PacientesModel paciente = pacientesRepository.findById(dto.getPacienteId())
                        .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
                atendimento.setPaciente(paciente);
            }

            if (dto.getProfissionalEspecialidadeId() != null) {
                ProfissionalEspecialidadeModel pe = profissionalEspecialidadeRepository.findById(dto.getProfissionalEspecialidadeId())
                        .orElseThrow(() -> new IllegalArgumentException("Profissional-Especialidade não encontrado"));
                atendimento.setProfissionalEspecialidade(pe);
            }

            if (dto.getDataHora() != null) {
                atendimento.setDataHora(dto.getDataHora());
            }

            if (dto.getObservacoes() != null) {
                atendimento.setObservacoes(dto.getObservacoes());
            }

            return atendimentoRepository.save(atendimento);
        });
    }

    @Transactional
    public void deletar(Long id) {
        if (!atendimentoRepository.existsById(id)) {
            throw new IllegalArgumentException("Atendimento com ID " + id + " não encontrado");
        }
        atendimentoRepository.deleteById(id);
    }
}