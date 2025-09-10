package br.com.fiap.pet_api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class HorarioComercialValidator implements ConstraintValidator<HorarioComercial, LocalTime> {
    private static final LocalTime INICIO = LocalTime.of(8, 0);
    private static final LocalTime FIM = LocalTime.of(20, 0);

    @Override
    public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
        if (value == null) return true; // @NotNull deve validar obrigatoriedade
        return !value.isBefore(INICIO) && !value.isAfter(FIM);
    }
}