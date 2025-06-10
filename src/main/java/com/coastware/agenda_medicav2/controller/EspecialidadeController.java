package com.coastware.agenda_medicav2.controller;

import com.coastware.agenda_medicav2.model.EspecialidadeModel;
import com.coastware.agenda_medicav2.service.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService especialidadeService;

    @PostMapping
    public ResponseEntity<EspecialidadeModel> criarEspecialidade(@RequestBody EspecialidadeModel especialidade) {
        EspecialidadeModel novaEspecialidade = especialidadeService.criarEspecialidade(especialidade);
        return ResponseEntity.ok(novaEspecialidade);
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadeModel>> listarTodos() {
        List<EspecialidadeModel> especialidades = especialidadeService.listarTodos();
        return ResponseEntity.ok(especialidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeModel> buscarPorId(@PathVariable Long id) {
        return especialidadeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeModel> atualizarEspecialidade(
            @PathVariable Long id,
            @RequestBody EspecialidadeModel especialidade) {
        return especialidadeService.atualizar(id, especialidade)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEspecialidade(@PathVariable Long id) {
        especialidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}