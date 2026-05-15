package com.e_com.VendorService.Shared.Application.Annotation.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.e_com.VendorService.Shared.Application.Annotation.Rules.UniqueList;
import com.e_com.VendorService.Shared.Application.Annotation.Rules.Unique;

import java.util.List;

@Component
public class UniqueListValidator implements ConstraintValidator<UniqueList, List<?>> {
    @Autowired
    private UniqueValidator uniqueValidator;

    private Unique uniqueAnnotation;
    private String message;

    @Override
    public void initialize(UniqueList uniqueList) {
        // Get the first @Unique annotation from the array
        this.uniqueAnnotation = uniqueList.value()[0];
        this.message = uniqueAnnotation.message();
        
        // Initialize the uniqueValidator with the unique annotation
        uniqueValidator.initialize(uniqueAnnotation);
    }

    @Override
    public boolean isValid(List<?> values, ConstraintValidatorContext context) {
        if (values == null || values.isEmpty()) {
            return true;
        }

        // Validate each item in the list using the existing UniqueValidator
        for (int i = 0; i < values.size(); i++) {
            Object value = values.get(i);
            if (value != null && !uniqueValidator.isValid(value.toString(), context)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(null)
                        .inIterable().atIndex(i)
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
