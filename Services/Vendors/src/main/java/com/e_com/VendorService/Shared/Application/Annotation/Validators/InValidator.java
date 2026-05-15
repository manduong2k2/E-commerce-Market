package com.e_com.VendorService.Shared.Application.Annotation.Validators;

import com.e_com.VendorService.Shared.Application.Annotation.Rules.In;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

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