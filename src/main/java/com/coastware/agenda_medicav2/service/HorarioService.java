package com.coastware.agenda_medicav2.service;

import com.coastware.agenda_medicav2.dto.HorarioDisponivelDTO;
import com.coastware.agenda_medicav2.model.AgendamentoModel;
import com.coastware.agenda_medicav2.model.SlotsAgendaModel;
import com.coastware.agenda_medicav2.repository.AgendamentoRepository;
import com.coastware.agenda_medicav2.repository.SlotsAgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HorarioService {

    @Autowired
    private SlotsAgendaRepository slotsAgendaRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public List<HorarioDisponivelDTO> obterHorariosDisponiveis(Long peId) {
        return gerarHorarios(peId, false);
    }

    public List<LocalDateTime> obterHorariosOcupados(Long peId) {
        return gerarHorarios(peId, true).stream()
                .map(HorarioDisponivelDTO::getDataHora)
                .collect(Collectors.toList());
    }

    private List<HorarioDisponivelDTO> gerarHorarios(Long peId, boolean apenasOcupados) {
        SlotsAgendaModel slot = slotsAgendaRepository.findByProfissionalEspecialidadeId(peId)
                .stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Slots não encontrados"));

        Set<DayOfWeek> dias = Arrays.stream(slot.getDiasSemanas().split(","))
                .map(String::toUpperCase)
                .map(this::mapDia)
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toSet());

        List<LocalTime> horarios = Arrays.stream(slot.getHorarios().split(","))
                .map(LocalTime::parse)
                .collect(Collectors.toList());

        LocalDate hoje = LocalDate.now();
        LocalDate fim = hoje.plusMonths(2);

        List<AgendamentoModel> agendamentos = agendamentoRepository
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

        Set<LocalDateTime> ocupados = agendamentos.stream()
                .map(AgendamentoModel::getDataHora)
                .collect(Collectors.toSet());

        List<HorarioDisponivelDTO> lista = new ArrayList<>();
        for (LocalDate d = hoje; !d.isAfter(fim); d = d.plusDays(1)) {
            if (!dias.contains(d.getDayOfWeek())) continue;
            for (LocalTime t : horarios) {
                LocalDateTime dt = LocalDateTime.of(d, t);
                boolean ocupado = ocupados.contains(dt);
                if ((ocupado && apenasOcupados) || (!ocupado && !apenasOcupados)) {
                    lista.add(new HorarioDisponivelDTO(dt));
                }
            }
        }
        return lista;
    }

    private String mapDia(String dia) {
        dia = dia.trim().toUpperCase(); // remove espaços e \r\n, padroniza para maiúsculas

        return switch (dia) {
            case "SEG" -> "MONDAY";
            case "TER" -> "TUESDAY";
            case "QUA" -> "WEDNESDAY";
            case "QUI" -> "THURSDAY";
            case "SEX" -> "FRIDAY";
            case "SAB" -> "SATURDAY";
            case "DOM" -> "SUNDAY";
            default -> throw new IllegalArgumentException("Dia inválido: " + dia);
        };
    }
}
