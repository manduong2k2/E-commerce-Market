package com.e_com.StorageService.Validation;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadRequest {
    @NotNull(message = "File is required")
    private MultipartFile file; 

    @NotNull(message = "Suffix is required")
    private String suffix;
}
