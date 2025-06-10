package com.coastware.agenda_medicav2.service;

import com.coastware.agenda_medicav2.model.ProfissionaisModel;
import com.coastware.agenda_medicav2.repository.ProfissionaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionaisRepository profissionaisRepository;
    @Autowired
    private PacientesService pacientesService;

    public ProfissionaisModel criarProfissional(ProfissionaisModel profissional) {
        return profissionaisRepository.save(profissional);
    }

    public List<ProfissionaisModel> listarTodos() {
        return profissionaisRepository.findAll();
    }

    public Optional<ProfissionaisModel> buscarPorId(Long id) {
        return profissionaisRepository.findById(id);
    }

    public Optional<ProfissionaisModel> atualizar(Long id, ProfissionaisModel profissionalAtualizado) {
        return profissionaisRepository.findById(id).map(profissional -> {
            profissional.setNome(profissionalAtualizado.getNome());
            profissional.setEmail(profissionalAtualizado.getEmail());
            profissional.setTelefone(profissionalAtualizado.getTelefone());
            profissional.setCrm(profissionalAtualizado.getCrm());
            profissional.setDataNascimento(profissionalAtualizado.getDataNascimento());
            return profissionaisRepository.save(profissional);

        });
    }

    public void deletar(Long id) {
        profissionaisRepository.deleteById(id);
    }
}
