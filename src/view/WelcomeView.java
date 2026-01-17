package src.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Ecran de bienvenue du jeu.
 */
public class WelcomeView {

    private VBox root;
    private Button boutonDemarrer;

    public WelcomeView() {
        creerVue();
    }

    private void creerVue() {
        root = new VBox(40);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.getStyleClass().add("game-root");

        // Titre Stylisé
        Text titre = new Text("[ COLONY POWER ]");
        titre.getStyleClass().add("title");

        Text sousTitre = new Text(">> SYSTEME DE GESTION ENERGETIQUE <<");
        sousTitre.getStyleClass().add("subtitle");

        String texteDescription = "CONNEXION ETABLIE...\n";

        Label labelDescription = new Label(texteDescription);
        labelDescription.setWrapText(true);
        labelDescription.setMaxWidth(800);
        labelDescription.setTextAlignment(TextAlignment.CENTER);
        labelDescription.getStyleClass().add("label-secondary");
        // On garde juste une petite marge pour la lisibilité, mais on utilise Consolas
        // via CSS
        labelDescription.setStyle("-fx-line-spacing: 5px; -fx-font-family: 'Consolas';");

        boutonDemarrer = new Button("[ INITIALISER MISSION ]");
        boutonDemarrer.getStyleClass().add("button-primary");
        // Suppression des styles inline pour la taille, géré par CSS

        root.getChildren().addAll(titre, sousTitre, labelDescription, boutonDemarrer);
    }

    public VBox getRoot() {
        return root;
    }

    public Button getStartButton() {
        return boutonDemarrer;
    }

    public Scene createScene() {
        Scene scene = new Scene(root, 1200, 800);
        try {
            java.net.URL cssUrl = getClass().getResource("styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("Avertissement : styles.css non trouvé dans le package view.");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du CSS : " + e.getMessage());
        }
        return scene;
    }
}
