// src/main/java/com/elearning/admin/controller/AdminFormationMvcController.java
package com.elearning.admin.controller;

import com.elearning.dto.FormationDto;
import com.elearning.mapper.FormationMapper;
import com.elearning.model.Formation;
import com.elearning.service.FormationService;
import com.elearning.service.StorageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/formations")
@SessionAttributes("formationDto")
public class AdminFormationMvcController {

    private final FormationService formationService;
    private final StorageService storageService;
    private final FormationMapper formationMapper;

    public AdminFormationMvcController(FormationService formationService,
                                       StorageService storageService,
                                       FormationMapper formationMapper) {
        this.formationService = formationService;
        this.storageService   = storageService;
        this.formationMapper  = formationMapper;
    }

    @ModelAttribute("formationDto")
    public FormationDto formationDto() {
        return new FormationDto();
    }

    /** 1. Liste paginée, triées par id DESC */
    @GetMapping
    public String list(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "20") int size) {
        Page<Formation> p = formationService.findAll(
                PageRequest.of(page, size, Sort.by("id").descending())
        );
        model.addAttribute("formations", p.getContent());
        model.addAttribute("currentPage", p.getNumber());
        model.addAttribute("totalPages", p.getTotalPages());
        return "admin/formation-list";
    }

    /** 2. Affichage du formulaire de création */
    @GetMapping("/nouveau")
    public String showCreationForm(Model model) {
        model.addAttribute("formationDto", new FormationDto());
        return "admin/formation-form";
    }

    /** 3. Traitement de la création */
    @PostMapping("/nouveau")
    public String processCreation(
            @ModelAttribute("formationDto") @Valid FormationDto dto,
            BindingResult br,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "videoFiles", required = false) List<MultipartFile> videoFiles,

            SessionStatus status
    ) {
        if (br.hasErrors()) {
            return "admin/formation-form";
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            dto.setImageUrl(storageService.store(imageFile));
        }
        if (videoFiles != null && !videoFiles.isEmpty()) {
            dto.setVideoFiles(videoFiles);
        }

        formationService.save(formationMapper.toEntity(dto));
        status.setComplete();
        // grâce au tri DESC, la nouvelle formation apparaît en tête
        return "redirect:/admin/formations";
    }

    /** 4. Formulaire d’édition */
    @GetMapping("/{id}/modifier")
    public String showEditForm(@PathVariable Long id, Model model) {
        Formation f = formationService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Formation introuvable"));
        model.addAttribute("formationDto", formationMapper.toDto(f));
        return "admin/formation-form";
    }

    @PostMapping("/{id}/modifier")
    public String processEdit(
            @PathVariable Long id,
            @ModelAttribute("formationDto") @Valid FormationDto dto,
            BindingResult br,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        if (br.hasErrors()) {
            return "admin/formation-form";
        }
        dto.setId(id);
        if (imageFile != null && !imageFile.isEmpty()) {
            dto.setImageUrl(storageService.store(imageFile));
        }

        // ——— Logique pour conserver les vidéos existantes si aucun nouveau fichier vidéo n’est uploadé ———
        Formation existing = formationService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Formation introuvable"));
        Formation f = formationMapper.toEntity(dto);

        // Ajoute les vidéos existantes si aucun nouveau fichier vidéo n’a été uploadé
        if (dto.getVideoFiles() == null || dto.getVideoFiles().isEmpty()) {
            f.setVideos(existing.getVideos());
        }

        formationService.save(f);
        return "redirect:/admin/formations";
    }


    /** 6. Suppression */
    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id) {
        formationService.delete(id);
        return "redirect:/admin/formations";
    }

    @GetMapping("/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Formation f = formationService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Formation introuvable pour l'ID " + id));
        model.addAttribute("formation", f);
        // on peut déjà fournir la liste des quizzes liés
        model.addAttribute("quizzes", f.getQuizzes());
        return "admin/formation-details";
    }
}
