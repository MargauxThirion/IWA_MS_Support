package com.example.ms_support.repositories;

import com.example.ms_support.models.SupportQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportQuestionRepository extends JpaRepository<SupportQuestion, Integer> {

    // Trouver les questions par utilisateur et statut
    List<SupportQuestion> findByUserIdAndStatus(Long userId, String status);

    // Trouver toutes les questions avec un statut spécifique
    List<SupportQuestion> findByStatus(String status);

    // Trouver les questions posées par un utilisateur
    List<SupportQuestion> findByUserId(Long userId);
}
