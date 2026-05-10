package com.e_com.StorageService.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.e_com.StorageService.Contract.IFileService;
import com.e_com.StorageService.Entity.File;
import com.e_com.StorageService.Validation.UploadBatchRequest;
import com.e_com.StorageService.Validation.UploadRequest;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
public class StorageController {
    @Autowired
    private IFileService fileService;

    @GetMapping("/files/{*url}")
    public ResponseEntity<byte[]> getFile(@PathVariable String url) throws IOException {
        byte[] data = fileService.getFile(url);

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(data);
    }

    @GetMapping("/files")
    public List<File> getFiles(@RequestParam String entityType, @RequestParam String entityId) {
        return fileService.getFiles(entityType, java.util.UUID.fromString(entityId));
    }
    
    @PostMapping("/files")
    public ResponseEntity<HashMap<String, String>> uploadFile(@Valid @ModelAttribute UploadRequest request) throws IOException {
        String path = fileService.uploadFile(request.getFile(), request.getSuffix(), request.getEntityType(), request.getEntityId());
        HashMap<String, String> response = new HashMap<>();
        response.put("path", path);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/file-batch")
    public ResponseEntity<HashMap<String, Object>> uploadMultipleFiles(@Valid @ModelAttribute UploadBatchRequest request) throws IOException {
        HashMap<String, Object> response = new HashMap<>();
        List<String> paths = fileService.uploadMultipleFiles(request.getFiles(), request.getSuffix(), request.getEntityType(), request.getEntityId());
        response.put("paths", paths);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/files")
    public void deleteFile(@RequestParam String url) throws IOException {
        fileService.deleteFile(url);
    }
}
