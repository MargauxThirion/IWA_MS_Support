CREATE TABLE support_questions (
                                   question_id SERIAL PRIMARY KEY,
                                   user_id INTEGER NOT NULL,
                                   question VARCHAR(100) NOT NULL,
                                   reponse VARCHAR(100),
                                   status VARCHAR(100) DEFAULT 'open'
);


INSERT INTO support_questions (user_id, question, reponse, status)
VALUES (1, 'Puis-je modifier un lieu que j’ai posté ?', 'Malheureusement non, il faudra le supprimer et en créer un nouveau.', 'open');

INSERT INTO support_questions (user_id, question, reponse, status)
VALUES (2, 'Comment puis-je ajouter un lieu à mes favoris ?', 'Cliquez sur l''icône en forme de cœur sur la page du lieu pour l''ajouter à vos favoris.', 'open');
