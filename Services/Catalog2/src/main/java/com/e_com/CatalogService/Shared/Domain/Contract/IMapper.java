package com.e_com.CatalogService.Shared.Domain.Contract;

public interface IMapper<T,E> {
    public T toDomain(E entity);
    public E toEntity(T domain);
}
