package com.e_com.StorageService.Service;

import com.e_com.StorageService.Contract.IFileService;
import com.e_com.StorageService.Entity.File;
import com.e_com.StorageService.Repository.IFileRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService implements IFileService {

    private static final String ROOT = "uploads/";

    @Autowired
    private IFileRepository fileRepository;

    @Override
    public String uploadFile(MultipartFile file, String suffix, String entityType, UUID entityId) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String originalName = file.getOriginalFilename();
        String extension = getExtension(originalName);

        String fileName = UUID.randomUUID() + "." + extension;

        Path path = Paths.get(ROOT, suffix, fileName);

        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        File fileEntity = new File(suffix, file.getSize(), originalName, fileName, extension, entityType, entityId);
        fileRepository.save(fileEntity);

        return suffix + "/" + fileName;
    }

    @Override
    public byte[] getFile(String fileName) throws IOException {
        Path path = Paths.get(ROOT, fileName);
        System.out.println(path);
        return Files.readAllBytes(path);
    }

    @Override
    public List<File> getFiles(String entityType, UUID entityId) {
        return fileRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }

    @Override
    public void deleteFile(String fileName) throws IOException {
        Path path = Paths.get(ROOT, fileName);
        Files.deleteIfExists(path);
    }

    private String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "bin";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}