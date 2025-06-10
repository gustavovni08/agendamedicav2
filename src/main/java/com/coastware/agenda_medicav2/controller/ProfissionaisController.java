package com.coastware.agenda_medicav2.controller;

import com.coastware.agenda_medicav2.model.ProfissionaisModel;
import com.coastware.agenda_medicav2.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionaisController {

    @Autowired
    private ProfissionalService profissionalService;

    @PostMapping
    public ResponseEntity<ProfissionaisModel> criaProfissionais(@RequestBody ProfissionaisModel profissional) {
        ProfissionaisModel novoProfissional = profissionalService.criarProfissional(profissional);
        return ResponseEntity.ok(novoProfissional);
    }

    @GetMapping
    public ResponseEntity<List<ProfissionaisModel>> buscaProfissionais() {
        List<ProfissionaisModel> profissionais = profissionalService.listarTodos();
        return ResponseEntity.ok(profissionais);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<ProfissionaisModel> buscaProfissionaisPorId(@PathVariable Long id) {
        return profissionalService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionaisModel>  atualizarProfissional(@PathVariable Long id, @RequestBody ProfissionaisModel profissional) {
        return profissionalService.atualizar(id, profissional)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProfissionaisModel> deletarProfissionais(@PathVariable Long id) {
        profissionalService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
