package com.e_com.CatalogService.Shared.Application.Annotation.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.e_com.CatalogService.Shared.Application.Annotation.Rules.ExistList;
import com.e_com.CatalogService.Shared.Application.Annotation.Rules.Exist;

import java.util.List;

@Component
public class ExistListValidator implements ConstraintValidator<ExistList, List<?>> {

    @Autowired
    private ExistValidator existValidator;

    private Exist existAnnotation;
    private String message;

    @Override
    public void initialize(ExistList existList) {
        // Get the first @Exist annotation from the array
        this.existAnnotation = existList.value()[0];
        this.message = existAnnotation.message();
        
        // Initialize the existValidator with the exist annotation
        existValidator.initialize(existAnnotation);
    }

    @Override
    public boolean isValid(List<?> values, ConstraintValidatorContext context) {
        if (values == null || values.isEmpty()) {
            return true;
        }

        // Validate each item in the list using the existing ExistValidator
        for (int i = 0; i < values.size(); i++) {
            Object value = values.get(i);
            if (value != null && !existValidator.isValid(value, context)) {
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
