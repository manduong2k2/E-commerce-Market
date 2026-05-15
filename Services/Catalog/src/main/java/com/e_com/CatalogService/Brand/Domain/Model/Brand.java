package com.e_com.CatalogService.Brand.Domain.Model;

import java.util.UUID;

import com.e_com.CatalogService.Shared.Domain.AggregateRoot;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Brand extends AggregateRoot<UUID> {
    private String name;
    private String image;
    private String description;

    public Brand(UUID id, String name, String image, String description) {
        super(id);
        this.name = name;
        this.image = image;
        this.description = description;
    }
}