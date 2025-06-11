package com.coastware.agenda_medicav2.service;

import com.coastware.agenda_medicav2.dto.AgendamentoDTO;
import com.coastware.agenda_medicav2.dto.HorarioDisponivelDTO;
import com.coastware.agenda_medicav2.model.AgendamentoModel;
import com.coastware.agenda_medicav2.model.SlotsAgendaModel;
import com.coastware.agenda_medicav2.repository.AgendamentoRepository;
import com.coastware.agenda_medicav2.repository.PacientesRpository;
import com.coastware.agenda_medicav2.repository.ProfissionalEspecialidadeRepository;
import com.coastware.agenda_medicav2.repository.SlotsAgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgendamentosService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private PacientesRpository pacientesRepository;
    @Autowired
    private SlotsAgendaRepository slotsAgendaRepository;
    @Autowired
    private ProfissionalEspecialidadeRepository profissionalEspecialidadeRepository;
    @Autowired
    private HorarioService horarioService;

    public AgendamentoModel fromDTO(AgendamentoDTO dto) {
        AgendamentoModel agendamento = new AgendamentoModel();

        agendamento.setDataHora(dto.getDataHora());

        agendamento.setPaciente(
                pacientesRepository.findById(dto.getPacienteId())
                        .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"))
        );

        agendamento.setProfissionalEspecialidade(
                profissionalEspecialidadeRepository.findById(dto.getProfissionalEspecialidadeId())
                        .orElseThrow(() -> new IllegalArgumentException("ProfissionalEspecialidade não encontrado"))
        );

        if (dto.getStatus() != null) {
            agendamento.setStatus(dto.getStatus());
        }

        return agendamento;
    }

    public List<HorarioDisponivelDTO> obterHorariosDisponiveis(Long peId) {
        // Carrega slots do profissional_especialidade
        SlotsAgendaModel slot = slotsAgendaRepository.findByProfissionalEspecialidadeId(peId)
                .stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Slots não encontrados"));

        // Parse dos dias da semana e horários
        Set<DayOfWeek> dias = Arrays.stream(slot.getDiasSemanas().split(","))
                .map(String::toUpperCase)
                .map(s -> DayOfWeek.valueOf(mapDia(s)))
                .collect(Collectors.toSet());

        List<LocalTime> horarios = Arrays.stream(slot.getHorarios().split(","))
                .map(LocalTime::parse).collect(Collectors.toList());

        LocalDate hoje = LocalDate.now();
        LocalDate fim = hoje.plusMonths(2);

        // Carrega agendamentos já confirmados/agendados/concluídos
        List<AgendamentoModel> existentes = agendamentoRepository
                .findByProfissionalEspecialidadeIdAndStatusInAndDataHoraBetween(
                        peId,
                        List.of(
                                AgendamentoModel.StatusAgendamento.AGENDADO,
                                AgendamentoModel.StatusAgendamento.CONFIRMADO,
                                AgendamentoModel.StatusAgendamento.REALIZADO
                        ),
                        hoje.atStartOfDay(),
                        fim.atTime(LocalTime.MAX)
                );

        Set<LocalDateTime> ocupados = existentes.stream()
                .map(AgendamentoModel::getDataHora)
                .collect(Collectors.toSet());

        List<HorarioDisponivelDTO> lista = new ArrayList<>();
        for (LocalDate d = hoje; !d.isAfter(fim); d = d.plusDays(1)) {
            if (!dias.contains(d.getDayOfWeek())) continue;
            for (LocalTime t : horarios) {
                LocalDateTime dt = LocalDateTime.of(d, t);
                if (!ocupados.contains(dt))
                    lista.add(new HorarioDisponivelDTO(dt));
            }
        }
        return lista;
    }

    private String mapDia(String abreviatura) {
        return switch (abreviatura) {
            case "SEG" -> "MONDAY";
            case "TER" -> "TUESDAY";
            case "QUA" -> "WEDNESDAY";
            case "QUI" -> "THURSDAY";
            case "SEX" -> "FRIDAY";
            case "SAB" -> "SATURDAY";
            case "DOM" -> "SUNDAY";
            default -> throw new IllegalArgumentException("Dia inválido: " + abreviatura);
        };
    }

    public AgendamentoModel criarAgendamento(AgendamentoDTO dto) {
        Long peId = dto.getProfissionalEspecialidadeId();

        List<HorarioDisponivelDTO> disponiveis = horarioService.obterHorariosDisponiveis(peId);

        boolean disponivel = disponiveis.stream()
                .anyMatch(h -> h.getDataHora().equals(dto.getDataHora()));

        if (!disponivel) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horário indisponível");
        }

        // Monta o model
        AgendamentoModel agendamento = new AgendamentoModel();
        agendamento.setDataHora(dto.getDataHora());
        agendamento.setStatus(dto.getStatus() != null ? dto.getStatus() : AgendamentoModel.StatusAgendamento.AGENDADO);
        agendamento.setPaciente(pacientesRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paciente não encontrado")));
        agendamento.setProfissionalEspecialidade(profissionalEspecialidadeRepository.findById(peId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profissional/Especialidade não encontrado")));

        return agendamentoRepository.save(agendamento);
    }

    public List<AgendamentoModel> listarTodos() {
        return agendamentoRepository.findAll();
    }

    public Optional<AgendamentoModel> buscarPorId(Long id) {
        return agendamentoRepository.findById(id);
    }

    public Optional<AgendamentoModel> atualizar(Long id, AgendamentoModel agendamentoAtualizado) {
        return agendamentoRepository.findById(id).map(agendamento -> {
            agendamento.setDataHora(agendamentoAtualizado.getDataHora());
            agendamento.setPaciente(agendamentoAtualizado.getPaciente());
            agendamento.setProfissionalEspecialidade(agendamentoAtualizado.getProfissionalEspecialidade());
            agendamento.setStatus(agendamentoAtualizado.getStatus());
            return agendamentoRepository.save(agendamento);
        });
    }

    public void deletar(Long id) {
        agendamentoRepository.deleteById(id);
    }
}