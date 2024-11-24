package com.example.ms_support.controllers;

import com.example.ms_support.models.SupportQuestion;
import com.example.ms_support.services.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/support")
public class SupportController {

    private final SupportService supportService;

    @Autowired
    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    @PostMapping
    public ResponseEntity<SupportQuestion> createQuestion(@RequestBody SupportQuestion question) {
        SupportQuestion createdQuestion = supportService.createQuestion(question);
        return ResponseEntity.ok(createdQuestion);
    }

    @GetMapping
    public ResponseEntity<List<SupportQuestion>> getAllQuestions() {
        List<SupportQuestion> questions = supportService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportQuestion> getQuestionById(@PathVariable Long id) {
        return supportService.getQuestionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SupportQuestion>> getQuestionsByUser(@PathVariable Long userId) {
        List<SupportQuestion> questions = supportService.getQuestionsByUserId(userId);
        return ResponseEntity.ok(questions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupportQuestion> answerQuestion(
            @PathVariable Long id, 
            @RequestBody SupportQuestion updatedQuestion) {
        return supportService.getQuestionById(id)
                .map(existingQuestion -> {
                    existingQuestion.setReponse(updatedQuestion.getReponse());
                    existingQuestion.setStatus("answered");
                    SupportQuestion updated = supportService.updateQuestion(existingQuestion);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
