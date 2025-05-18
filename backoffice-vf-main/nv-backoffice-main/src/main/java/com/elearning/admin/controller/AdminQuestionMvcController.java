// src/main/java/com/elearning/admin/controller/AdminQuestionMvcController.java
package com.elearning.admin.controller;
import com.elearning.dto.ChoiceDto;
import com.elearning.dto.QuestionDto;
import com.elearning.mapper.QuestionMapper;
import com.elearning.model.Quiz;
import com.elearning.model.Question;
import com.elearning.service.QuestionService;
import com.elearning.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/quiz/{quizId}/questions")
public class AdminQuestionMvcController {

    private final QuestionService questionService;
    private final QuizService quizService;
    private final QuestionMapper mapper;

    public AdminQuestionMvcController(QuestionService qs,
                                      QuizService quizService,
                                      QuestionMapper mapper) {
        this.questionService = qs;
        this.quizService = quizService;
        this.mapper = mapper;
    }

    @GetMapping
    public String list(@PathVariable Long quizId, Model model) {
        Quiz quiz = quizService.findById(quizId).orElseThrow();
        List<Question> questions = questionService.findByQuiz(quizId);
        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        return "admin/question-list";
    }

    @GetMapping("/new")
    public String newForm(@PathVariable Long quizId, Model m) {
        m.addAttribute("quiz", quizService.findById(quizId).orElseThrow());
        m.addAttribute("questionDto", new QuestionDto());
        return "admin/question-form";
    }

    @PostMapping
    public String create(@PathVariable Long quizId,
                         @ModelAttribute @Valid QuestionDto dto,
                         BindingResult br) {
        if(br.hasErrors()) return "admin/question-form";
        Quiz quiz = quizService.findById(quizId).orElseThrow();
        questionService.save(mapper.toEntity(dto, quiz));
        return "redirect:/admin/quiz/"+quizId+"/questions";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long quizId,
                       @PathVariable Long id,
                       Model m) {
        m.addAttribute("quiz", quizService.findById(quizId).orElseThrow());
        Question q = questionService.findById(id).orElseThrow();
        m.addAttribute("questionDto", mapper.toDto(q));
        return "admin/question-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long quizId,
                         @PathVariable Long id,
                         @ModelAttribute @Valid QuestionDto dto,
                         BindingResult br) {
        if(br.hasErrors()) return "admin/question-form";
        Quiz quiz = quizService.findById(quizId).orElseThrow();
        dto.setId(id);
        questionService.save(mapper.toEntity(dto, quiz));
        return "redirect:/admin/quiz/"+quizId+"/questions";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long quizId,
                         @PathVariable Long id) {
        questionService.delete(id);
        return "redirect:/admin/quiz/"+quizId+"/questions";
    }


    /** Import CSV minimaliste : libelle,option1,option2,option3,option4,bonneRéponseIndex(0–3) */
    @PostMapping("/import")
    public String importCsv(@PathVariable Long quizId,
                            @RequestParam("file") MultipartFile file) throws IOException {

        System.out.println("Import CSV pour quiz " + quizId + " → " + file.getOriginalFilename());

        Quiz quiz = quizService.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz introuvable"));

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            List<QuestionDto> dtos = br.lines()
                    .skip(1)
                    .peek(l -> System.out.println("Lue : " + l))
                    .map(String::trim)
                    .filter(l -> !l.isEmpty())
                    .filter(l -> l.split(",").length >= 6)
                    .map(line -> {
                        String[] cols = line.split(",");                         // 1) on split la ligne
                        QuestionDto dto = new QuestionDto();
                        dto.setLibelle(cols[0].replaceAll("^\"|\"$", ""));

                        // 2) on construit la liste de 4 ChoiceDto et on l’affecte
                        dto.setChoices(
                                IntStream.range(1, 5)
                                        .mapToObj(i -> {
                                            ChoiceDto cd = new ChoiceDto();
                                            cd.setTexte(cols[i].replaceAll("^\"|\"$", ""));
                                            // setter à renommer impérativement en setCorrect(Boolean)
                                            cd.setCorrect((i - 1) == Integer.parseInt(cols[5]));
                                            return cd;
                                        })
                                        .collect(Collectors.toList())
                        );
                        return dto;
                    })
                    .collect(Collectors.toList());



            dtos.stream()
                    .map(dto -> mapper.toEntity(dto, quiz))
                    .forEach(questionService::save);
        }

        return "redirect:/admin/quiz/" + quizId + "/questions";
    }
}
