package br.com.fiap.pet_api.dto;

import br.com.fiap.pet_api.validation.HorarioComercial;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoDTO(
        Long id,

        @NotNull(message="{NotNull}")
        Long idPet,

        @NotBlank(message="{NotBlank}") @Size(max=40, message="{Size}")
        @Pattern(regexp="(?i)Banho|Tosa|Vacina|Consulta|Exame|Outros", message="{Pattern}")
        String servico,

        @Size(max=60, message="{Size}")
        String profissional,

        @Size(max=60, message="{Size}")
        String local,

        @NotNull(message="{NotNull}") @FutureOrPresent(message="{FutureOrPresent}")
        LocalDate data,

        @NotNull(message="{NotNull}") @HorarioComercial
        LocalTime horario,

        @Size(max=255, message="{Size}")
        String observacoes
) { }
