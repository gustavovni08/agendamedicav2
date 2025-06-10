package com.coastware.agenda_medicav2.service;

import com.coastware.agenda_medicav2.model.AgendamentoModel;
import com.coastware.agenda_medicav2.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentosService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

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