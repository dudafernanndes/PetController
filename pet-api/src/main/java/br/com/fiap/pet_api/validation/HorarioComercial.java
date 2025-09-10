package br.com.fiap.pet_api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HorarioComercialValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface HorarioComercial {
    String message() default "{horario.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
