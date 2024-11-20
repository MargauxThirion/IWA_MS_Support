CREATE TABLE support_questions (
                                   question_id SERIAL PRIMARY KEY,
                                   user_id INTEGER NOT NULL,
                                   subject VARCHAR(100) NOT NULL,
                                   description TEXT NOT NULL,
                                   status VARCHAR(100) DEFAULT 'open'
);


INSERT INTO support_questions (user_id, subject, description, status)
VALUES (1, 'Problème de connexion', 'Je n’arrive pas à me connecter à mon compte.', 'open');

INSERT INTO support_questions (user_id, subject, description, status)
VALUES (2, 'Erreur lors du paiement', 'Une erreur survient lorsque j’essaie de payer.', 'open');
