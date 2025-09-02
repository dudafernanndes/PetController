package br.com.fiap.pet_api.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoDTO(
        Long id,
        @NotNull Long idPet,
        @NotBlank @Size(max = 40) String servico,
        @Size(max = 60) String profissional,
        @Size(max = 60) String local,
        @NotNull @FutureOrPresent LocalDate data,
        @NotNull LocalTime horario,
        @Size(max = 255) String observacoes
) { }
