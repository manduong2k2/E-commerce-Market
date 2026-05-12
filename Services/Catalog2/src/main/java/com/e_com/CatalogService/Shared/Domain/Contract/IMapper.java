package com.e_com.CatalogService.Shared.Domain.Contract;

public interface IMapper<D,E> {
    public D toDomain(E entity);
    public E toEntity(D domain);
}