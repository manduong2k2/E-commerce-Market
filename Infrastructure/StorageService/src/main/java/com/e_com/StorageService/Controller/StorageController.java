package com.e_com.StorageService.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.e_com.StorageService.Contract.IFileService;
import com.e_com.StorageService.Validation.UploadRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class StorageController {
    @Autowired
    private IFileService fileService;

    @GetMapping("/files")
    public ResponseEntity<byte[]> getFile(@RequestParam String url) throws IOException {
        byte[] data = fileService.getFile(url);

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(data);
    }

    @PostMapping("/files")
    public String uploadFile(@Valid @ModelAttribute UploadRequest request) throws IOException {
        return fileService.uploadFile(request.getFile(), request.getSuffix());
    }

    @DeleteMapping("/files")
    public void deleteFile(@RequestParam String url) throws IOException {
        fileService.deleteFile(url);
    }
}
