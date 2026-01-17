# Colony Power - City Management Tycoon

## Description du Projet
**Colony Power** est un jeu de gestion type "tycoon" développé en JavaFX. Le joueur incarne un gestionnaire de cité qui doit prendre des décisions stratégiques pour maintenir l'équilibre d'un système complexe. L'objectif principal est de gérer la production et la consommation d'énergie tout en développant la ville.

## Fonctionnalités Principales
- **Gestion de l'Énergie** : Construction de centrales électriques (PowerPlants) de différents types.
- **Développement Urbain** : Construction de résidences et gestion de la population.
- **Économie et Ressources** : Gestion du budget et des ressources de la colonie.
- **Interface Interactive** : Système de dialogue pour la construction et suivi en temps réel de l'état de la ville.

## Technologies Utilisées
- **Langage** : Java
- **Framework UI** : JavaFX
- **Stylisation** : CSS (Vanilla CSS)

## Répartition des Tâches (Groupe de 2)

Conformément aux modalités du projet INF2328, voici la répartition du travail entre les collaborateurs :

### TCHAMDJA Luc Mazangui
- Architecture centrale du projet (Pattern MVC).
- Implémentation du moteur de jeu (`GameEngine`).
- Structure principale de l'application (`GameApplication`).
- Définition des modèles de base (`GameState`, `City`).
- Création de la vue de fin de jeu (`GameOverView`).
- Mises à jour esthétiques de l'interface utilisateur et stylisation complète via `styles.css`.

### KOKONE Caleb
- Contribution au développement des classes modèles (`PowerPlant`, `Residence`).
- Implémentation des dialogues de construction (`BuildDialogView`).
- **Réalisation du diagramme de classe / architecture du projet.**
- Implémentation de fonctionnalités spécifiques liées à la gestion des ressources de "Colony Power".

## Installation et Lancement
1. S'assurer que Java 17+ et JavaFX sont installés.
2. Cloner le dépôt : `git clone [URL_DU_DEPOT]`
3. Compiler et lancer la classe `Main.java`.
