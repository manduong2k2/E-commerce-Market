package com.e_com.CartService.Shared.Infrastructure.Storage;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.e_com.CartService.Shared.Domain.Storage.IStorageService;

@Service
public class StorageService implements IStorageService {
        private final WebClient webClient;

        @Value("${spring.application.gateway-url}")
        private String gatewayUrl;

        private static final String STORAGE_SERVICE_NAME = "storage-service";
        private static final String STORAGE_ENDPOINT = "/api/files";

        public StorageService(WebClient.Builder builder) {
                this.webClient = builder.baseUrl(gatewayUrl).build();
        }

        @Override
        public void uploadFile(String suffix, byte[] file, String entityType, String entityId) {
                webClient.post()
                                .uri(gatewayUrl + "/" + STORAGE_SERVICE_NAME + STORAGE_ENDPOINT)
                                .bodyValue(Map.of(
                                                "suffix", suffix,
                                                "file", file,
                                                "entityType", entityType,
                                                "entityId", entityId))
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();
        }

        @Override
        public void uploadFiles(String suffix, List<MultipartFile> files, String entityType, String entityId) {
                MultipartBodyBuilder builder = new MultipartBodyBuilder();
                builder.part("suffix", suffix);
                builder.part("entityType", entityType);
                builder.part("entityId", entityId);
                for (MultipartFile file : files) {
                        try {
                                ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                                        @Override
                                        public String getFilename() {
                                                return file.getOriginalFilename();
                                        }
                                };
                                builder.part("files", resource)
                                                .contentType(MediaType.parseMediaType(file.getContentType()));
                        } catch (Exception e) {
                                throw new RuntimeException(e);
                        }
                }
                webClient.post()
                                .uri(gatewayUrl + "/" + STORAGE_SERVICE_NAME + "/api/file-batch")
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .body(
                                                BodyInserters.fromMultipartData(builder.build()))
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();
        }

        @Override
        public String getFiles(String entityType, String entityId) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path(STORAGE_ENDPOINT + "/" + STORAGE_SERVICE_NAME)
                                                .queryParam("entityType", entityType)
                                                .queryParam("entityId", entityId)
                                                .build())
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();
        }

        @Override
        public void deleteFile(String path) {
                webClient.delete()
                                .uri(gatewayUrl + "/" + STORAGE_SERVICE_NAME + STORAGE_ENDPOINT + "/" + path)
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();
        }
}
