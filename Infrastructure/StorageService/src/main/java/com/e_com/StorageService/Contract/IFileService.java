package com.e_com.StorageService.Contract;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.e_com.StorageService.Entity.File;

public interface IFileService {
    public String uploadFile(MultipartFile file, String suffix, String entityType, UUID entityId) throws IOException;

    byte[] getFile(String fileName) throws IOException;

    List<File> getFiles(String entityType, UUID entityId);

    void deleteFile(String fileName) throws IOException;
}
