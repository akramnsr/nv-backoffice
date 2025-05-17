// src/main/java/com/elearning/service/StorageService.java
package com.elearning.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    /**
     * Stocke le fichier et renvoie l'URL (relative ou absolue) pour y accéder.
     */
    String store(MultipartFile file);
}
