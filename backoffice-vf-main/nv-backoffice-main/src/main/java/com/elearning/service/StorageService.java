// src/main/java/com/elearning/service/StorageService.java
package com.elearning.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    /**
     * Stocke un fichier (image, document, etc.) à la racine du dossier uploads
     * @param file le fichier à stocker
     * @return le nom de fichier ou chemin relatif pour accès statique
     */
    String store(MultipartFile file);

    /**
     * Stocke une vidéo dans le sous-dossier "videos" du dossier uploads
     * @param file la vidéo à stocker
     * @return chemin relatif comme "videos/nom-fichier.ext"
     */
    String storeVideo(MultipartFile file);
}
