package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Ecran de fin de partie (Game Over).
 */
public class GameOverView {

    private VBox root;
    private Button boutonNouvellePartie;
    private Button boutonQuitter;
    private Label labelStatistiques;
    private Label labelRaison;

    public GameOverView() {
        creerVue();
    }

    private void creerVue() {
        root = new VBox(25);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.getStyleClass().add("game-root");

        Text titre = new Text("⚠ MISSION TERMINÉE ⚠");
        titre.getStyleClass().add("title");
        titre.setStyle("-fx-fill: #ef4444;");

        labelRaison = new Label();
        labelRaison.setWrapText(true);
        labelRaison.setMaxWidth(700);
        labelRaison.setTextAlignment(TextAlignment.CENTER);
        labelRaison.setStyle("-fx-font-size: 18px; -fx-text-fill: #f59e0b;");

        labelStatistiques = new Label();
        labelStatistiques.setWrapText(true);
        labelStatistiques.setMaxWidth(700);
        labelStatistiques.setTextAlignment(TextAlignment.CENTER);
        labelStatistiques.getStyleClass().add("label-secondary");
        labelStatistiques.setStyle("-fx-font-size: 16px; -fx-line-spacing: 5px;");

        boutonNouvellePartie = new Button("🔄 NOUVELLE MISSION");
        boutonNouvellePartie.getStyleClass().add("button-primary");
        boutonNouvellePartie.setStyle("-fx-font-size: 16px; -fx-padding: 12px 30px;");

        boutonQuitter = new Button("❌ QUITTER");
        boutonQuitter.getStyleClass().add("button-danger");
        boutonQuitter.setStyle("-fx-font-size: 16px; -fx-padding: 12px 30px;");

        root.getChildren().addAll(titre, labelRaison, labelStatistiques, boutonNouvellePartie, boutonQuitter);
    }

    public void setGameOverInfo(String reason, int cycles, int finalResources, double finalHappiness, int population) {
        labelRaison.setText(reason);

        String stats = "RAPPORT FINAL\n\n"
                + "Cycles survécus: " + cycles + "\n"
                + "Ressources finales: " + finalResources + " crédits\n"
                + "Bonheur final: " + Math.round(finalHappiness * 100) + "%\n"
                + "Population: " + population + " habitants";

        labelStatistiques.setText(stats);
    }

    public VBox getRoot() {
        return root;
    }

    public Button getNewGameButton() {
        return boutonNouvellePartie;
    }

    public Button getQuitButton() {
        return boutonQuitter;
    }
}
