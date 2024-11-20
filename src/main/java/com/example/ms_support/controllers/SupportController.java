package com.example.ms_support.controllers;

import com.example.ms_support.models.SupportQuestion;
import com.example.ms_support.services.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/support")
public class SupportController {

    private final SupportService supportService;

    @Autowired
    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    // Créer une nouvelle question de support
    @PostMapping("/questions")
    public ResponseEntity<SupportQuestion> createSupportQuestion(@RequestBody SupportQuestion question) {
        SupportQuestion createdQuestion = supportService.createSupportQuestion(question);
        return ResponseEntity.ok(createdQuestion);
    }

    // Obtenir une question par ID
    @GetMapping("/questions/{id}")
    public ResponseEntity<SupportQuestion> getSupportQuestionById(@PathVariable Integer id) {
        Optional<SupportQuestion> question = supportService.getSupportQuestionById(id);
        return question.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtenir toutes les questions ouvertes
    @GetMapping("/questions/open")
    public ResponseEntity<List<SupportQuestion>> getOpenSupportQuestions() {
        List<SupportQuestion> openQuestions = supportService.getOpenSupportQuestions();
        return ResponseEntity.ok(openQuestions);
    }

    // Fermer une question de support
    @PutMapping("/questions/{id}/close")
    public ResponseEntity<SupportQuestion> closeSupportQuestion(@PathVariable Integer id) {
        SupportQuestion closedQuestion = supportService.closeSupportQuestion(id);
        if (closedQuestion != null) {
            return ResponseEntity.ok(closedQuestion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
