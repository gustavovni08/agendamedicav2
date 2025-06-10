package com.coastware.agenda_medicav2.service;

import com.coastware.agenda_medicav2.dto.AgendamentoDTO;
import com.coastware.agenda_medicav2.model.AgendamentoModel;
import com.coastware.agenda_medicav2.repository.AgendamentoRepository;
import com.coastware.agenda_medicav2.repository.PacientesRpository;
import com.coastware.agenda_medicav2.repository.ProfissionalEspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentosService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private PacientesRpository pacientesRepository;

    @Autowired
    private ProfissionalEspecialidadeRepository profissionalEspecialidadeRepository;

    public AgendamentoModel fromDTO(AgendamentoDTO dto) {
        AgendamentoModel agendamento = new AgendamentoModel();

        agendamento.setDataHora(dto.getDataHora());

        agendamento.setPaciente(
                pacientesRepository.findById(dto.getPacienteId())
                        .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"))
        );

        agendamento.setProfissionalEspecialidade(
                profissionalEspecialidadeRepository.findById(dto.getProfissionalEspecialidadeId())
                        .orElseThrow(() -> new IllegalArgumentException("ProfissionalEspecialidade não encontrado"))
        );

        if (dto.getStatus() != null) {
            agendamento.setStatus(dto.getStatus());
        }

        return agendamento;
    }

    public AgendamentoModel criarAgendamento(AgendamentoModel agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    public List<AgendamentoModel> listarTodos() {
        return agendamentoRepository.findAll();
    }

    public Optional<AgendamentoModel> buscarPorId(Long id) {
        return agendamentoRepository.findById(id);
    }

    public Optional<AgendamentoModel> atualizar(Long id, AgendamentoModel agendamentoAtualizado) {
        return agendamentoRepository.findById(id).map(agendamento -> {
            agendamento.setDataHora(agendamentoAtualizado.getDataHora());
            agendamento.setPaciente(agendamentoAtualizado.getPaciente());
            agendamento.setProfissionalEspecialidade(agendamentoAtualizado.getProfissionalEspecialidade());
            agendamento.setStatus(agendamentoAtualizado.getStatus());
            return agendamentoRepository.save(agendamento);
        });
    }

    public void deletar(Long id) {
        agendamentoRepository.deleteById(id);
    }
}