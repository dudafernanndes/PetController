package br.com.fiap.pet_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PetDTO(
        Long id,
        @NotBlank @Size(max = 80) String nome,
        @NotBlank @Size(max = 40) String especie,
        @Size(max = 60) String raca,
        @Min(0) Integer idade,
        @Size(max = 10) String sexo,
        @Size(max = 30) String cor,
        @NotBlank @Size(max = 80) String nomeTutor,
        @NotBlank @Size(max = 20) String telefoneTutor,
        @Size(max = 255) String observacoes
) { }
