package com.elearning.mapper;

import com.elearning.dto.FormationDto;
import com.elearning.dto.VideoDto;
import com.elearning.model.Formation;
import com.elearning.model.Video;
import com.elearning.service.StorageService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormationMapperImpl implements FormationMapper {

    private final StorageService storageService;

    public FormationMapperImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public FormationDto toDto(Formation f) {
        if (f == null) return null;
        FormationDto dto = new FormationDto();
        dto.setId(f.getId());
        dto.setTitre(f.getTitre());
        dto.setDescription(f.getDescription());
        dto.setDureeHeures(f.getDureeHeures());
        dto.setImageUrl(f.getImageUrl());

        // Mapping des vidéos complètes (pas que URL)
        List<VideoDto> videoDtos = f.getVideos().stream().map(video -> {
            VideoDto vdto = new VideoDto();
            vdto.setId(video.getId());
            vdto.setFilename(video.getFilename());
            vdto.setUrl(video.getUrl());
            return vdto;
        }).collect(Collectors.toList());
        dto.setVideos(videoDtos);

        // Pour compatibilité avec les anciens templates
        List<String> videoUrls = f.getVideos().stream().map(Video::getUrl).collect(Collectors.toList());
        dto.setVideoUrls(videoUrls);

        return dto;
    }

    @Override
    public Formation toEntity(FormationDto dto) {
        if (dto == null) return null;
        Formation f = new Formation();
        if (dto.getId() != null) {
            f.setId(dto.getId());
        }
        f.setTitre(dto.getTitre());
        f.setDescription(dto.getDescription());
        f.setDureeHeures(dto.getDureeHeures());
        f.setImageUrl(dto.getImageUrl());

        // Ajouter les anciennes vidéos (édition) ou rien (création)
        if (dto.getVideos() != null && !dto.getVideos().isEmpty()) {
            List<Video> existingVideos = dto.getVideos().stream().map(vdto -> {
                Video v = new Video();
                v.setId(vdto.getId());
                v.setFilename(vdto.getFilename());
                v.setUrl(vdto.getUrl());
                v.setFormation(f);
                return v;
            }).collect(Collectors.toList());
            f.setVideos(existingVideos);
        } else {
            f.setVideos(new ArrayList<>());
        }

        // Ajouter les nouvelles vidéos uploadées
        if (dto.getVideoFiles() != null && !dto.getVideoFiles().isEmpty()) {
            dto.getVideoFiles().stream()
                    .filter(file -> !file.isEmpty())
                    .forEach(file -> {
                        String storedName = storageService.storeVideo(file);
                        Video v = new Video();
                        v.setFilename(storedName);
                        v.setUrl("/uploads/" + storedName);
                        v.setFormation(f);
                        f.getVideos().add(v); // append, ne pas remplacer
                    });
        }
        return f;
    }



}
