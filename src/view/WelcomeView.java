package view;

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

        String texteDescription = "CONNEXION ETABLIE...\n"
                + "ID: COMMANDANT NOVA-7\n"
                + "STATUT: EN ATTENTE D'ORDRES\n\n"
                + "MISSION:\n"
                + "> Gérer la production d'énergie\n"
                + "> Maintenir les systèmes vitaux\n"
                + "> Assurer la survie de la population\n\n"
                + "INITIALISATION DES SYSTEMES...";

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
        String cheminCSS = getClass().getResource("styles.css").toExternalForm();
        scene.getStylesheets().add(cheminCSS);
        return scene;
    }
}
