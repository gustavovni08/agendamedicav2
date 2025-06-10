package com.coastware.agenda_medicav2.controller;

import com.coastware.agenda_medicav2.model.AgendamentoModel;
import com.coastware.agenda_medicav2.service.AgendamentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentosController {

    @Autowired
    private AgendamentosService agendamentosService;

    @PostMapping
    public ResponseEntity<AgendamentoModel> criarAgendamento(@RequestBody AgendamentoModel agendamento) {
        AgendamentoModel novoAgendamento = agendamentosService.criarAgendamento(agendamento);
        return ResponseEntity.ok(novoAgendamento);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoModel>> listarTodos() {
        List<AgendamentoModel> agendamentos = agendamentosService.listarTodos();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoModel> buscarPorId(@PathVariable Long id) {
        return agendamentosService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoModel> atualizarAgendamento(
            @PathVariable Long id,
            @RequestBody AgendamentoModel agendamento) {
        return agendamentosService.atualizar(id, agendamento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long id) {
        agendamentosService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}