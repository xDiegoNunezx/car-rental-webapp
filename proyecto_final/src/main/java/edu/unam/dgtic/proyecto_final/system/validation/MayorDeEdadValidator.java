package edu.unam.dgtic.proyecto_final.system.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class MayorDeEdadValidator implements ConstraintValidator<MayorDeEdad, LocalDate> {

    @Override
    public boolean isValid(LocalDate fechaNacimiento, ConstraintValidatorContext context) {
        if (fechaNacimiento == null) {
            return false;
        }
        LocalDate hoyMenos18 = LocalDate.now().minusYears(18);
        return !fechaNacimiento.isAfter(hoyMenos18);
    }
}
