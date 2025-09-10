package br.com.fiap.pet_api.dto;

import br.com.fiap.pet_api.validation.TelefoneBR;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record PetDTO(
        Long id,

        @NotBlank(message="{NotBlank}") @Size(max=80,  message="{Size}")
        String nome,

        @NotBlank(message="{NotBlank}") @Size(max=40, message="{Size}")
        // opcional: restringe valores permitidos
        @Pattern(regexp="(?i)Cachorro|Gato|Ave|Peixe|Roedor|Réptil|Outros", message="{Pattern}")
        String especie,

        @Size(max=60, message="{Size}")
        String raca,

        @Min(value=0, message="{Min}")
        Integer idade,

        @Size(max=10, message="{Size}")
        @Pattern(regexp="(?i)M|F|Indefinido", message="{Pattern}")
        String sexo,

        @Size(max=30, message="{Size}")
        String cor,

        @NotBlank(message="{NotBlank}") @Size(max=80, message="{Size}")
        String nomeTutor,

        @NotBlank(message="{NotBlank}") @TelefoneBR
        String telefoneTutor,

        @Size(max=255, message="{Size}")
        String observacoes
) { }
