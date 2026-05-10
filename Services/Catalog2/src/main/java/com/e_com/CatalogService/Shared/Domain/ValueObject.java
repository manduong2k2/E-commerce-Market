package com.e_com.CatalogService.Shared.Domain;

public abstract class ValueObject {
    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
