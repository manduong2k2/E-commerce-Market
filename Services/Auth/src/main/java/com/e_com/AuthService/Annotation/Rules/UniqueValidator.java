package com.e_com.AuthService.Annotation.Rules;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, String> {

    @PersistenceContext
    private EntityManager em;

    private String table;
    private String column;
    private String deletedAtColumn;

    @Override
    public void initialize(Unique unique) {
        this.table = unique.table();
        this.column = unique.column();
        this.deletedAtColumn = unique.deletedAtColumn();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;

        String sql = "SELECT COUNT(*) FROM " + table + " WHERE " + column + " = :value";

        if (deletedAtColumn != null && !deletedAtColumn.isBlank()) {
            sql += " AND " + deletedAtColumn + " IS NULL";
        }

        Number count = (Number) em.createNativeQuery(sql)
                .setParameter("value", value)
                .getSingleResult();

        return count.intValue() == 0;
    }
}
