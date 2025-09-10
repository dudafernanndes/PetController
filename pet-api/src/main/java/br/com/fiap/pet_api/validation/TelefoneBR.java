package br.com.fiap.pet_api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TelefoneBRValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface TelefoneBR {
    String message() default "{telefone.invalido}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
