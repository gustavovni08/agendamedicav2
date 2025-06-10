package com.coastware.agenda_medicav2.controller;

import com.coastware.agenda_medicav2.model.ProfissionalEspecialidadeModel;
import com.coastware.agenda_medicav2.service.ProfissionalEspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profissionais-especialidades")
public class ProfissionalEspecialidadeController {

    @Autowired
    private ProfissionalEspecialidadeService profissionalEspecialidadeService;

    @PostMapping
    public ResponseEntity<ProfissionalEspecialidadeModel> criar(
            @RequestBody ProfissionalEspecialidadeModel profissionalEspecialidade) {

        ProfissionalEspecialidadeModel novo = profissionalEspecialidadeService.criar(profissionalEspecialidade);
        return ResponseEntity.ok(novo);
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalEspecialidadeModel>> listarTodos() {
        List<ProfissionalEspecialidadeModel> list = profissionalEspecialidadeService.listarTodos();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalEspecialidadeModel> buscarPorId(@PathVariable Long id) {
        return profissionalEspecialidadeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalEspecialidadeModel> atualizar(
            @PathVariable Long id,
            @RequestBody ProfissionalEspecialidadeModel profissionalEspecialidade) {

        return profissionalEspecialidadeService.atualizar(id, profissionalEspecialidade)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        profissionalEspecialidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}