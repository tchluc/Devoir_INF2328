package controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import model.*;
import view.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controleur du jeu - Gere les interactions entre le modele et la vue.
 */
public class GameController {

    private GameEngine gameEngine;
    private MainGameView mainGameView;
    private GameApplication application;

    /**
     * Constructeur du controleur.
     * 
     * @param application L'application principale
     */
    public GameController(GameApplication application) {
        this.application = application;
        this.gameEngine = new GameEngine("Nova-7");
        this.mainGameView = new MainGameView();
        configurerBoutons();
        mettreAJourVue();
    }

    /**
     * Configure les actions des boutons.
     */
    private void configurerBoutons() {
        mainGameView.getBuildButton().setOnAction(e -> gererConstructionCentrale());
        mainGameView.getUpgradeButton().setOnAction(e -> gererAmeliorationCentrale());
        mainGameView.getDetailsButton().setOnAction(e -> afficherDetails());
        mainGameView.getNextCycleButton().setOnAction(e -> passerCycleSuivant());
    }

    /**
     * Gere la construction d'une nouvelle centrale.
     */
    private void gererConstructionCentrale() {
        GameState gameState = gameEngine.getGameState();
        BuildDialogView dialog = new BuildDialogView(gameState.getResources());
        java.util.Optional<PowerPlantType> resultat = dialog.showAndWait();

        if (resultat.isPresent()) {
            PowerPlantType typeChoisi = resultat.get();
            boolean reussite = gameEngine.buildPowerPlant(typeChoisi);

            if (!reussite) {
                afficherAlerte("Construction Impossible",
                        "Ressources insuffisantes pour construire une centrale " + typeChoisi.getName() + ".",
                        Alert.AlertType.WARNING);
            }

            mettreAJourVue();
        }
    }

    /**
     * Gere l'amelioration d'une centrale existante.
     */
    private void gererAmeliorationCentrale() {
        GameState gameState = gameEngine.getGameState();
        City city = gameState.getCity();
        List<PowerPlant> centrales = city.getPowerPlants();

        if (centrales.isEmpty()) {
            afficherAlerte("Aucune Centrale",
                    "Il n'y a aucune centrale à améliorer. Construisez-en une d'abord !",
                    Alert.AlertType.INFORMATION);
            return;
        }

        ArrayList<String> choix = new ArrayList<>();

        for (int i = 0; i < centrales.size(); i++) {
            PowerPlant centrale = centrales.get(i);
            String description = centrale.getType().getIcon() + " #" + centrale.getId()
                    + " (Niv." + centrale.getLevel() + ")"
                    + " - " + Math.round(centrale.getProduction()) + " kW";

            if (!centrale.canUpgrade()) {
                description += " [MAX]";
            } else {
                description += " [Coût amélioration: " + centrale.getUpgradeCost() + " cr]";
            }

            choix.add(description);
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choix.get(0), choix);
        dialog.setTitle("Améliorer une Centrale");
        dialog.setHeaderText("Sélectionnez la centrale à améliorer:");
        dialog.setContentText("Centrale:");

        java.util.Optional<String> resultat = dialog.showAndWait();

        if (resultat.isPresent()) {
            String choixUtilisateur = resultat.get();
            int index = choix.indexOf(choixUtilisateur);

            if (index >= 0) {
                PowerPlant centraleChoisie = centrales.get(index);
                boolean reussite = gameEngine.upgradePowerPlant(centraleChoisie);

                if (!reussite && !centraleChoisie.canUpgrade()) {
                    afficherAlerte("Amélioration Impossible",
                            "Cette centrale est déjà au niveau maximum !",
                            Alert.AlertType.INFORMATION);
                }
            }

            mettreAJourVue();
        }
    }

    /**
     * Affiche les statistiques detaillees de la colonie.
     */
    private void afficherDetails() {
        GameState gameState = gameEngine.getGameState();
        City city = gameState.getCity();

        String details = "STATISTIQUES DÉTAILLÉES\n\n"
                + "Colonie: " + city.getName() + "\n"
                + "Cycle: " + gameState.getCurrentCycle() + "\n"
                + "Ressources: " + gameState.getResources() + " crédits\n"
                + "Bonheur: " + Math.round(gameState.getHappiness() * 100) + "% (" + gameState.getHappinessStatus()
                + ")\n\n"
                + "Population: " + city.getTotalPopulation() + " habitants\n"
                + "Résidences: " + city.getResidenceCount() + "\n"
                + "Centrales: " + city.getPowerPlantCount() + "\n\n"
                + "Production totale: " + Math.round(city.getTotalEnergyProduction()) + " kW\n"
                + "Demande totale: " + Math.round(city.getTotalEnergyDemand()) + " kW\n"
                + "Balance: " + Math.round(gameState.getEnergyBalance()) + " kW\n"
                + "Ratio: " + Math.round(gameState.getEnergyRatio() * 100) + "%\n\n"
                + "Coût d'entretien: " + city.getTotalMaintenanceCost() + " crédits/cycle";

        afficherAlerte("Détails de la Colonie", details, Alert.AlertType.INFORMATION);
    }

    /**
     * Passe au cycle suivant du jeu.
     */
    private void passerCycleSuivant() {
        gameEngine.processCycle();
        mettreAJourVue();

        if (gameEngine.getGameState().isGameOver()) {
            gererFinDePartie();
        }
    }

    /**
     * Gere la fin de partie.
     */
    private void gererFinDePartie() {
        Platform.runLater(() -> {
            GameState gameState = gameEngine.getGameState();
            City city = gameState.getCity();

            GameOverView gameOverView = new GameOverView();
            gameOverView.setGameOverInfo(
                    gameState.getGameOverReason(),
                    gameState.getCurrentCycle(),
                    gameState.getResources(),
                    gameState.getHappiness(),
                    city.getTotalPopulation());

            gameOverView.getNewGameButton().setOnAction(e -> recommencerPartie());
            gameOverView.getQuitButton().setOnAction(e -> Platform.exit());

            application.showGameOver(gameOverView);
        });
    }

    /**
     * Reinitialise le jeu.
     */
    private void recommencerPartie() {
        gameEngine.resetGame("Nova-7");
        mainGameView.clearLogs();
        mettreAJourVue();
        application.showMainGame(this);
    }

    /**
     * Met a jour l'interface avec l'etat actuel du jeu.
     */
    private void mettreAJourVue() {
        Platform.runLater(() -> {
            mainGameView.updateDisplay(gameEngine.getGameState());
            List<String> logs = gameEngine.getRecentLogs(10);
            if (!logs.isEmpty()) {
                mainGameView.addLogs(logs);
            }
        });
    }

    /**
     * Affiche une alerte.
     */
    private void afficherAlerte(String titre, String contenu, Alert.AlertType type) {
        Alert alerte = new Alert(type);
        alerte.setTitle(titre);
        alerte.setHeaderText(null);
        alerte.setContentText(contenu);
        alerte.showAndWait();
    }

    public MainGameView getMainGameView() {
        return mainGameView;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }
}
