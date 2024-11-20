package com.example.ms_support.services;

import com.example.ms_support.models.SupportQuestion;
import com.example.ms_support.repositories.SupportQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SupportService {

    private final SupportQuestionRepository repository;
    private final RestTemplate restTemplate;

    @Autowired
    public SupportService(SupportQuestionRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    // Créer une nouvelle question de support
    public SupportQuestion createSupportQuestion(SupportQuestion question) {
        return repository.save(question);
    }

    // Obtenir une question par ID
    public Optional<SupportQuestion> getSupportQuestionById(Integer questionId) {
        return repository.findById(questionId);
    }

    // Obtenir toutes les questions ouvertes
    public List<SupportQuestion> getOpenSupportQuestions() {
        return repository.findByStatus("open");
    }

    // Mettre à jour le statut d'une question (par exemple, de "open" à "closed")
    public SupportQuestion closeSupportQuestion(Integer questionId) {
        Optional<SupportQuestion> question = repository.findById(questionId);
        if (question.isPresent()) {
            SupportQuestion updatedQuestion = question.get();
            updatedQuestion.setStatus("closed");
            repository.save(updatedQuestion);

            // Appel au service de notifications
            createNotificationForClosedQuestion(updatedQuestion);
            return updatedQuestion;
        }
        return null;
    }

    // Appeler le service de notifications
    private void createNotificationForClosedQuestion(SupportQuestion question) {
        // Construire le corps de la requête
        Map<String, Long> requestBody = Map.of(
                "userId", question.getUserId(),
                "questionId", question.getQuestionId()
        );

        // URL du service de notifications
        String notificationUrl = "http://host.docker.internal:8085/notifications/create/support-response";

        // Appel à la route POST du ms_notification
        try {
            restTemplate.postForEntity(notificationUrl, requestBody, Void.class);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel au service de notifications : " + e.getMessage());
            // Ajoutez un mécanisme de gestion ou des logs supplémentaires ici
        }
    }
}
