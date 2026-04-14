package com.e_com.StorageService.Contract;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    public String uploadFile(MultipartFile file, String suffix) throws IOException;

    byte[] getFile(String fileName) throws IOException;

    void deleteFile(String fileName) throws IOException;
}
