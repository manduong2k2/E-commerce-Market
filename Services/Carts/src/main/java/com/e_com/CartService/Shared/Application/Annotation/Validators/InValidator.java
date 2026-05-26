package com.e_com.CartService.Shared.Application.Annotation.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

import com.e_com.CartService.Shared.Application.Annotation.Rules.In;

public class InValidator implements ConstraintValidator<In, Object> {
    private Set<String> allowedValues;

    @Override
    public void initialize(In annotation) {
        allowedValues = new HashSet<>(Set.of(annotation.values()));
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return allowedValues.contains(value.toString());
    }
}