package com.coastware.agenda_medicav2.controller;

import com.coastware.agenda_medicav2.model.PacientesModel;
import com.coastware.agenda_medicav2.service.PacientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacientesController {

    @Autowired
    private PacientesService pacientesService;

    // Criar paciente
    @PostMapping
    public ResponseEntity<PacientesModel> criarPaciente(@RequestBody PacientesModel paciente) {
        PacientesModel novoPaciente = pacientesService.criarPaciente(paciente);
        return ResponseEntity.ok(novoPaciente);
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<PacientesModel>> listarTodos() {
        List<PacientesModel> pacientes = pacientesService.listarTodos();
        return ResponseEntity.ok(pacientes);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<PacientesModel> buscarPorId(@PathVariable Long id) {
        return pacientesService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar paciente
    @PutMapping("/{id}")
    public ResponseEntity<PacientesModel> atualizarPaciente(@PathVariable Long id, @RequestBody PacientesModel paciente) {
        return pacientesService.atualizar(id, paciente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id) {
        pacientesService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
