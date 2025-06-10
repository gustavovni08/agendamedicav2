package com.coastware.agenda_medicav2.controller;

import com.coastware.agenda_medicav2.dto.SlotsAgendaDTO;
import com.coastware.agenda_medicav2.model.SlotsAgendaModel;
import com.coastware.agenda_medicav2.service.SlotsAgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots-agenda")
public class SlotsAgendaController {

    @Autowired
    private SlotsAgendaService slotsAgendaService;

    @PostMapping
    public ResponseEntity<SlotsAgendaModel> criar(@RequestBody SlotsAgendaDTO dto) {
        SlotsAgendaModel novoSlot = slotsAgendaService.criar(dto);
        return ResponseEntity.ok(novoSlot);
    }

    @GetMapping
    public ResponseEntity<List<SlotsAgendaModel>> listarTodos() {
        List<SlotsAgendaModel> slots = slotsAgendaService.listarTodos();
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlotsAgendaModel> buscarPorId(@PathVariable Long id) {
        return slotsAgendaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/por-profissional-especialidade/{profissionalEspecialidadeId}")
    public ResponseEntity<List<SlotsAgendaModel>> buscarPorProfissionalEspecialidade(
            @PathVariable Long profissionalEspecialidadeId) {

        List<SlotsAgendaModel> slots =
                slotsAgendaService.buscarPorProfissionalEspecialidade(profissionalEspecialidadeId);

        return ResponseEntity.ok(slots);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SlotsAgendaModel> atualizar(
            @PathVariable Long id,
            @RequestBody SlotsAgendaDTO dto) {

        return slotsAgendaService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        slotsAgendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
