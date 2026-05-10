package com.e_com.StorageService.Validation;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadBatchRequest {
    @NotNull(message = "File is required")
    private List<MultipartFile> files; 

    @NotNull(message = "Suffix is required")
    private String suffix;

    private String entityType;
    
    private java.util.UUID entityId;
}
