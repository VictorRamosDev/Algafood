package com.algaworks.algafood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        this.valorField = constraintAnnotation.valorField();
        this.descricaoField = constraintAnnotation.descricaoField();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objectValidation, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objectValidation.getClass(), valorField)
                    .getReadMethod()
                    .invoke(objectValidation);
            String descricao = (String) BeanUtils.getPropertyDescriptor(objectValidation.getClass(), descricaoField)
                    .getReadMethod()
                    .invoke(objectValidation);

            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                valid = descricao.toLowerCase().contains(descricaoObrigatoria.toLowerCase());
            }

            return valid;
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }

}
