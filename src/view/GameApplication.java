package src.view;

import src.controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.view.GameOverView;
import src.view.WelcomeView;

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
        src.view.WelcomeView welcomeView = new src.view.WelcomeView();
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
            java.net.URL cssUrl = getClass().getResource("styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("Avertissement : styles.css non trouvé (GameApplication)");
            }
        } catch (Exception e) {
            System.err.println("Impossible de charger styles.css: " + e.getMessage());
        }

        fenetrePrincipale.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
