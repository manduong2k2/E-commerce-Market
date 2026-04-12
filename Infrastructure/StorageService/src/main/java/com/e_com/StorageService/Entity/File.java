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

    @Column(unique = true, nullable = false)
    private String url;

    @Column(unique = false, nullable = false)
    private String originalName;

    @Column(unique = false, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private String extension;

    @Column(unique = false, nullable = false)
    private Long size;

    public File() {}

    public File(String url, Long size, String originalName, String name, String extension) {
        this.url = url;
        this.size = size;
        this.originalName = originalName;
        this.name = name;
        this.extension = extension;
    }

    public com.e_com.StorageService.Model.File toDomain() {
        return new com.e_com.StorageService.Model.File(id, url, originalName, name, extension, size);
    }
}