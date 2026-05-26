package com.e_com.CartService.Shared.Domain.Storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IStorageService {
    public void uploadFile(String suffix, byte[] file, String entityType, String entityId);
    public void uploadFiles(String suffix, List<MultipartFile> files, String entityType, String entityId);
    public String getFiles(String entityType, String entityId);
    public void deleteFile(String url);
}
