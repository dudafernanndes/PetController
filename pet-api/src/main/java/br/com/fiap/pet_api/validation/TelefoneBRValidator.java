package br.com.fiap.pet_api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneBRValidator implements ConstraintValidator<TelefoneBR, String> {

    // aceita 11 dígitos (ex.: 11999998888) com ou sem separadores simples
    private static final String REGEX =
            "^(\\d{11}|\\d{2}-?\\d{5}-?\\d{4})$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true; // deixe @NotBlank cuidar do obrigatório
        return value.replaceAll("\\s+", "").matches(REGEX);
    }
}