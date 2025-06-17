package com.coastware.agenda_medicav2.controller;

import com.coastware.agenda_medicav2.model.AgendamentoModel;
import com.coastware.agenda_medicav2.model.PacientesModel;
import com.coastware.agenda_medicav2.model.ProfissionaisModel;
import com.coastware.agenda_medicav2.repository.AgendamentoRepository;
import com.coastware.agenda_medicav2.repository.ProfissionaisRepository;
import com.coastware.agenda_medicav2.service.AgendamentosService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AgendamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AgendamentosService agendamentosService;

    @MockitoBean
    private ProfissionaisRepository profissionaisRepository;

    @MockitoBean
    private AgendamentoRepository agendamentoRepository;

    @Test
    void buscarPorId_deveRetornarAgendamentoComPacienteAssociado() throws Exception {
        // 1. Configuração do teste
        Long agendamentoId = 1L;
        Long pacienteIdEsperado = 100L;

        // Criar paciente associado
        PacientesModel paciente = new PacientesModel();
        paciente.setId(pacienteIdEsperado);
        paciente.setNome("Maria Oliveira");
        paciente.setCpf("123.456.789-00");

        // Criar agendamento com paciente associado
        AgendamentoModel agendamento = new AgendamentoModel();
        agendamento.setId(agendamentoId);
        agendamento.setPaciente(paciente);

        // Configurar comportamento do serviço mockado
        Mockito.when(agendamentosService.buscarPorId(agendamentoId))
                .thenReturn(Optional.of(agendamento));

        // 2. Execução e verificação
        mockMvc.perform(MockMvcRequestBuilders.get("/agendamentos/{id}", agendamentoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(agendamentoId))
                .andExpect(jsonPath("$.paciente.id").value(pacienteIdEsperado))
                .andExpect(jsonPath("$.paciente.nome").value("Maria Oliveira"))
                .andExpect(jsonPath("$.paciente.cpf").value("123.456.789-00"));
    }

    @Test
    void buscarPorId_deveRetornar404QuandoAgendamentoNaoExiste() throws Exception {
        // Configuração
        Long agendamentoIdInexistente = 999L;

        Mockito.when(agendamentosService.buscarPorId(agendamentoIdInexistente))
                .thenReturn(Optional.empty());

        // Execução e verificação
        mockMvc.perform(MockMvcRequestBuilders.get("/agendamentos/{id}", agendamentoIdInexistente))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorId_deveRetornarPacienteCorretoQuandoAssociado() throws Exception {
        // Configuração
        Long agendamentoId = 1L;
        Long pacienteIdEsperado = 100L;

        PacientesModel paciente = new PacientesModel();
        paciente.setId(pacienteIdEsperado);

        AgendamentoModel agendamento = new AgendamentoModel();
        agendamento.setId(agendamentoId);
        agendamento.setPaciente(paciente);

        Mockito.when(agendamentosService.buscarPorId(agendamentoId))
                .thenReturn(Optional.of(agendamento));

        // Execução e verificação
        mockMvc.perform(MockMvcRequestBuilders.get("/agendamentos/{id}", agendamentoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paciente.id").value(pacienteIdEsperado));
    }

    @Test
    void buscarPorId_deveRetornarPacienteDiferenteQuandoNaoAssociado() throws Exception {
        // Configuração
        Long agendamentoId = 1L;
        Long pacienteIdEsperado = 100L; // ID esperado
        Long pacienteIdReal = 200L;     // ID real no sistema

        PacientesModel paciente = new PacientesModel();
        paciente.setId(pacienteIdReal); // ID diferente do esperado

        AgendamentoModel agendamento = new AgendamentoModel();
        agendamento.setId(agendamentoId);
        agendamento.setPaciente(paciente);

        Mockito.when(agendamentosService.buscarPorId(agendamentoId))
                .thenReturn(Optional.of(agendamento));

        // Execução e verificação
        mockMvc.perform(MockMvcRequestBuilders.get("/agendamentos/{id}", agendamentoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paciente.id").value(not(pacienteIdEsperado)));
    }

    @Test
    void contarAgendamentosPorProfissional_deveRetornarQuantidadeCorreta() throws Exception {
        // Configuração
        Long profissionalId = 1L;
        int quantidadeEsperada = 5;

        Mockito.when(agendamentosService.contarAgendamentosPorProfissional(profissionalId))
                .thenReturn((long) quantidadeEsperada);

        // Execução e verificação
        mockMvc.perform(MockMvcRequestBuilders.get("/profissionais/{id}/agendamentos/contagem", profissionalId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(quantidadeEsperada)));
    }

    @Test
    void contarAgendamentosPorProfissional_deveRetornarZeroQuandoNaoHaAgendamentos() throws Exception {
        // Configuração
        Long profissionalId = 2L;
        int quantidadeEsperada = 0;

        Mockito.when(agendamentosService.contarAgendamentosPorProfissional(profissionalId))
                .thenReturn((long) quantidadeEsperada);

        // Execução e verificação
        mockMvc.perform(MockMvcRequestBuilders.get("/profissionais/{id}/agendamentos/contagem", profissionalId))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void contarAgendamentosPorProfissional_deveRetornar404QuandoProfissionalNaoExiste() throws Exception {
        // Configuração
        Long profissionalIdInexistente = 999L;

        Mockito.when(agendamentosService.contarAgendamentosPorProfissional(profissionalIdInexistente))
                .thenThrow(new Exception("Profissional não encontrado"));

        // Execução e verificação
        mockMvc.perform(MockMvcRequestBuilders.get("/profissionais/{id}/agendamentos/contagem", profissionalIdInexistente))
                .andExpect(status().isNotFound());
    }

    @Test
    void getHorariosDisponiveis_deveRetornarHorariosDisponiveis() throws Exception {
        // Configuração
        Long profissionalId = 4L;
        LocalDateTime data = LocalDateTime.now();

        // Configurar profissional
        ProfissionaisModel profissional = new ProfissionaisModel();
        profissional.setId(profissionalId);


// Configurar agendamentos existentes
        AgendamentoModel agendamento1 = new AgendamentoModel();
        agendamento1.setDataHora(data);

        AgendamentoModel agendamento2 = new AgendamentoModel();
        agendamento2.setDataHora(data);

        Mockito.when(profissionaisRepository.findById(profissionalId))
                .thenReturn(Optional.of(profissional));

        Mockito.when(agendamentoRepository.findByProfissionalIdAndData(profissionalId, data))
                .thenReturn(Arrays.asList(agendamento1, agendamento2));

        // Execução e verificação
        mockMvc.perform(MockMvcRequestBuilders.get("/api/agendamentos/disponiveis/{profissionalId}", profissionalId)
                        .param("data", data.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(14))) // Total de slots: 16 (9:00-17:00) menos 2 ocupados
                .andExpect(jsonPath("$[0].hora").value("09:00"))
                .andExpect(jsonPath("$[0].disponivel").value(true))
                .andExpect(jsonPath("$[2].hora").value("10:00")) // Este deve estar ocupado
                .andExpect(jsonPath("$[2].disponivel").doesNotExist()); // Não deve estar na lista de disponíveis
    }
}
