package com.e_com.StorageService.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

    @Column(unique = false, nullable = true)
    private String entityType;

    @Column(unique = false, nullable = true)
    private java.util.UUID entityId;

    @Column(unique = false, nullable = false)
    private String suffix;

    @Column(unique = false, nullable = false)
    private String originalName;

    @Column(unique = false, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private String extension;

    @Column(unique = false, nullable = false)
    private Long size;

    public File() {}

    public File(String suffix, Long size, String originalName, String name, String extension, String entityType, java.util.UUID entityId) {
        this.suffix = suffix;
        this.size = size;
        this.originalName = originalName;
        this.name = name;
        this.extension = extension;
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public com.e_com.StorageService.Model.File toDomain() {
        return new com.e_com.StorageService.Model.File(id, suffix, originalName, entityType, entityId, name, extension, size);
    }
}