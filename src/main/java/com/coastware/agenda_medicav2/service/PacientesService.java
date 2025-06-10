package com.coastware.agenda_medicav2.service;

import com.coastware.agenda_medicav2.model.PacientesModel;
import com.coastware.agenda_medicav2.repository.PacientesRpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacientesService {

    @Autowired
    private PacientesRpository pacientesRpository;

    public PacientesModel criarPaciente(PacientesModel pacientesModel) {
        return pacientesRpository.save(pacientesModel);
    }

    public List<PacientesModel> listarTodos(){
        return pacientesRpository.findAll();
    }

    public Optional<PacientesModel> buscarPorId(Long id) {
        return pacientesRpository.findById(id);
    }

    public Optional<PacientesModel> atualizar(Long id, PacientesModel pacienteAtualizado) {
        return pacientesRpository.findById(id).map(paciente -> {
            paciente.setNome(pacienteAtualizado.getNome());
            paciente.setCpf(pacienteAtualizado.getCpf());
            paciente.setTelefone(pacienteAtualizado.getTelefone());
            paciente.setEmail(pacienteAtualizado.getEmail());
            paciente.setDataNascimento(pacienteAtualizado.getDataNascimento());
            return pacientesRpository.save(paciente);
        });
    }

    public void deletar(Long id) {
        pacientesRpository.deleteById(id);
    }


}
