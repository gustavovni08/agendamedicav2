package com.coastware.agenda_medicav2.service;

import com.coastware.agenda_medicav2.model.EspecialidadeModel;
import com.coastware.agenda_medicav2.repository.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    public EspecialidadeModel criarEspecialidade(EspecialidadeModel especialidade) {
        return especialidadeRepository.save(especialidade);
    }

    public List<EspecialidadeModel> listarTodos() {
        return especialidadeRepository.findAll();
    }

    public Optional<EspecialidadeModel> buscarPorId(Long id) {
        return especialidadeRepository.findById(id);
    }

    public Optional<EspecialidadeModel> atualizar(Long id, EspecialidadeModel especialidadeAtualizada) {
        return especialidadeRepository.findById(id).map(especialidade -> {
            especialidade.setNome(especialidadeAtualizada.getNome());
            return especialidadeRepository.save(especialidade);
        });
    }

    public void deletar(Long id) {
        especialidadeRepository.deleteById(id);
    }
}