package view;

import controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application principale JavaFX.
 */
public class GameApplication extends Application {

    private Stage fenetrePrincipale;
    private GameController gameController;

    @Override
    public void start(Stage primaryStage) {
        this.fenetrePrincipale = primaryStage;

        fenetrePrincipale.setTitle("⚡ Colony Power - Gestionnaire d'Énergie Spatiale");
        fenetrePrincipale.setResizable(true);
        fenetrePrincipale.setMinWidth(1200);
        fenetrePrincipale.setMinHeight(800);

        afficherEcranBienvenue();
        fenetrePrincipale.show();
    }

    private void afficherEcranBienvenue() {
        WelcomeView welcomeView = new WelcomeView();
        welcomeView.getStartButton().setOnAction(e -> lancerJeu());

        Scene scene = welcomeView.createScene();
        fenetrePrincipale.setScene(scene);
    }

    private void lancerJeu() {
        gameController = new GameController(this);
        showMainGame(gameController);
    }

    public void showMainGame(GameController controller) {
        this.gameController = controller;
        Scene scene = controller.getMainGameView().createScene();
        fenetrePrincipale.setScene(scene);
    }

    public void showGameOver(GameOverView gameOverView) {
        Scene scene = new Scene(gameOverView.getRoot(), 1200, 800);

        try {
            String cheminCSS = getClass().getResource("styles.css").toExternalForm();
            scene.getStylesheets().add(cheminCSS);
        } catch (Exception e) {
            System.err.println("Impossible de charger styles.css: " + e.getMessage());
        }

        fenetrePrincipale.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
