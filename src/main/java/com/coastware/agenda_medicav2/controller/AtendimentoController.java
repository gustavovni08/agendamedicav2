package com.coastware.agenda_medicav2.controller;

import com.coastware.agenda_medicav2.dto.AtendimentoDTO;
import com.coastware.agenda_medicav2.model.AtendimentoModel;
import com.coastware.agenda_medicav2.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    // Criar novo atendimento
    @PostMapping
    public ResponseEntity<AtendimentoModel> criar(@RequestBody AtendimentoDTO dto) {
        AtendimentoModel atendimento = atendimentoService.criar(dto);
        return ResponseEntity.ok(atendimento);
    }

    // Listar todos os atendimentos
    @GetMapping
    public ResponseEntity<List<AtendimentoModel>> listarTodos() {
        return ResponseEntity.ok(atendimentoService.listarTodos());
    }

    // Buscar atendimento por ID
    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoModel> buscarPorId(@PathVariable Long id) {
        return atendimentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar atendimento
    @PutMapping("/{id}")
    public ResponseEntity<AtendimentoModel> atualizar(@PathVariable Long id, @RequestBody AtendimentoDTO dto) {
        return atendimentoService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar atendimento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            atendimentoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
