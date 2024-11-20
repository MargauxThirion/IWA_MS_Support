package com.example.ms_support.repositories;


import com.example.ms_support.models.SupportQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SupportQuestionRepository extends JpaRepository<SupportQuestion, Integer> {

    // Requête personnalisée pour trouver les questions ouvertes par utilisateur
    List<SupportQuestion> findByUserIdAndStatus(Integer userId, String status);

    // Requête pour trouver toutes les questions ouvertes
    List<SupportQuestion> findByStatus(String status);
}

