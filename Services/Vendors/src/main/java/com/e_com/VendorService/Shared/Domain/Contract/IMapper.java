package com.e_com.VendorService.Shared.Domain.Contract;

public interface IMapper<D,E> {
    public D toDomain(E entity);
    public E toEntity(D domain);
}