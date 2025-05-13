# Plateforme E-learning – PFA 2025

## Objectif
Développement d'une plateforme web d'e-learning permettant :
- Gestion des cours, quiz, utilisateurs (étudiants, enseignants, admin)
- Suivi intelligent pendant les évaluations (IA de surveillance)
- Accès différencié selon les rôles (admin / enseignant / étudiant)

---

##  Technologies utilisées

| Couche | Technologies |
|--------|--------------|
| Backend | Java, Spring MVC, Servlets |
| Frontend | JSP (admin, enseignants), Angular (étudiants – à venir) |
| Base de données | MySQL |
| Build / dépendances | Maven |
| Serveur d’application | Apache Tomcat |
| IA (prochainement) | Python, OpenCV, MediaPipe |

---

##  Lancement local (développement)

### 1. Cloner le projet

```bash
git clone https://github.com/akramnsr/pfa-elearning.git
2. Ouvrir le projet dans IntelliJ IDEA
Ouvre IntelliJ IDEA

File > Open > Choisis le dossier pfa-elearning

Attends le téléchargement automatique des dépendances Maven

3. Configurer Tomcat (si ce n’est pas déjà fait)
Run > Edit Configurations...

Ajouter une configuration : Tomcat Server > Local

Déploiement : + > Artifact > main:war exploded

Application context : vide ou /elearning

Appliquer et OK

4. Lancer le projet
Clique sur le bouton  ou fais Shift + F10

Attends que le message Artifact deployed successfully s’affiche

5. Accéder à l'application
Si le contexte est vide : http://localhost:8080/

Si le contexte est /elearning : http://localhost:8080/elearning/

👨 Guide Utilisateur
🔹 Étudiant :
Se connecter via /login

Accéder aux cours et quiz

Bénéficier du suivi IA pendant les évaluations

🔹 Enseignant :
Ajouter/éditer des cours

Créer des quiz

Accéder aux résultats des étudiants

🔹 Admin :
Gérer tous les utilisateurs

Visualiser les rapports IA et alertes de triche

 Encadré par :
Pr.KORAICHE FAHD

 Réalisé par :
Nouasria Akram et Baroukh Ayoub  –  2024/2025

Ce projet est réalisé dans le cadre du Projet de Fin d’Année (PFA) – École Marocaine des Sciences de l’Ingénieur – EMSI







