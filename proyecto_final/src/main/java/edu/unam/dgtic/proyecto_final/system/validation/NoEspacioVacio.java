package edu.unam.dgtic.proyecto_final.system.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NoEspacioNoVacioValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface NoEspacioVacio {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
