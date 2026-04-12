package com.e_com.StorageService.Repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_com.StorageService.Entity.File;

public interface IFileRepository extends JpaRepository<File, UUID> {
    Optional<File> findById(UUID id);
}
