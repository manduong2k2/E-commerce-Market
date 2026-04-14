package com.e_com.StorageService.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class File {
    private java.util.UUID id;
    private String suffix;
    private String originalName;
    private String name;
    private String extension;
    private Long size;
    
    public com.e_com.StorageService.Entity.File toEntity() {
        return new com.e_com.StorageService.Entity.File(suffix, size, originalName, name, extension);
    }
}
