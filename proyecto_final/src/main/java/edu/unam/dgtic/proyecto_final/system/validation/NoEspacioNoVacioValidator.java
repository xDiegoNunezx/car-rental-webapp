package edu.unam.dgtic.proyecto_final.system.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoEspacioNoVacioValidator implements ConstraintValidator<NoEspacioVacio,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null
                && !s.regionMatches(0, " ", 0, 1)
                && !s.isBlank();
    }
}
