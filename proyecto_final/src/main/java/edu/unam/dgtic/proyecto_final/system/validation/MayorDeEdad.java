package edu.unam.dgtic.proyecto_final.system.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MayorDeEdadValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MayorDeEdad {
    String message() default "Debes ser mayor de edad (mínimo 18 años)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
