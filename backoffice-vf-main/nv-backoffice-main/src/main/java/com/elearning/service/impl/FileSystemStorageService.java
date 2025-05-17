// src/main/java/com/elearning/service/impl/FileSystemStorageService.java
package com.elearning.service.impl;

import com.elearning.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path uploadDir;

    public FileSystemStorageService(@Value("${storage.upload-dir}") String uploadDir) {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadDir);
        } catch (IOException ex) {
            throw new RuntimeException("Impossible de créer le dossier de stockage", ex);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (filename.contains("..")) {
                throw new RuntimeException("Nom de fichier non valide : " + filename);
            }
            Path target = uploadDir.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            // retourne ici le chemin relatif pour l'accès via un contrôleur statique
            return filename;
        } catch (IOException ex) {
            throw new RuntimeException("Échec du stockage du fichier " + filename, ex);
        }
    }
}
