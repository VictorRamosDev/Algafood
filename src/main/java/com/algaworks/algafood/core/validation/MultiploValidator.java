package com.algaworks.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultiplo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean valid = true;

        if (value != null) {
            var valorDecimal = BigDecimal.valueOf(value.doubleValue());
            var valorMultiplo = BigDecimal.valueOf(this.numeroMultiplo);
            var resto = valorDecimal.remainder(valorMultiplo);

            return BigDecimal.ZERO.compareTo(resto) == 0;
        }

        return valid;
    }

}
