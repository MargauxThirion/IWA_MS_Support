package com.example.ms_support.services;

import com.example.ms_support.models.SupportQuestion;
import com.example.ms_support.repositories.SupportQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupportService {

    private final SupportQuestionRepository supportRepository;

    @Autowired
    public SupportService(SupportQuestionRepository supportRepository) {
        this.supportRepository = supportRepository;
    }

    // Créer une question
    public SupportQuestion createQuestion(SupportQuestion question) {
        return supportRepository.save(question);
    }

    // Récupérer toutes les questions
    public List<SupportQuestion> getAllQuestions() {
        return supportRepository.findAll();
    }

    // Récupérer une question par ID
    public Optional<SupportQuestion> getQuestionById(Long id) {
        return supportRepository.findById(id.intValue());
    }

    // Récupérer les questions par utilisateur
    public List<SupportQuestion> getQuestionsByUserId(Long userId) {
        return supportRepository.findByUserId(userId);
    }

    // Mettre à jour une question
    public SupportQuestion updateQuestion(SupportQuestion question) {
        return supportRepository.save(question);
    }
}
