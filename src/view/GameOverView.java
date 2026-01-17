package src.view;

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
        root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.getStyleClass().add("game-root");

        Text titre = new Text("/// MISSION TERMINEE ///");
        titre.getStyleClass().add("title");

        labelRaison = new Label();
        labelRaison.setWrapText(true);
        labelRaison.setMaxWidth(700);
        labelRaison.setTextAlignment(TextAlignment.CENTER);
        // Using subtitle style for the reason to make it prominent but tech-y
        labelRaison.getStyleClass().add("subtitle");
        labelRaison.setStyle("-fx-fill: #ffffff; -fx-font-size: 18px;");

        labelStatistiques = new Label();
        labelStatistiques.setWrapText(true);
        labelStatistiques.setMaxWidth(700);
        labelStatistiques.setTextAlignment(TextAlignment.CENTER);
        labelStatistiques.getStyleClass().add("label-stat");
        labelStatistiques.setStyle(
                "-fx-font-size: 16px; -fx-line-spacing: 8px; -fx-border-color: #ffffff; -fx-border-style: dashed; -fx-padding: 20px;");

        boutonNouvellePartie = new Button("[ RELANCER SIMULATION ]");
        boutonNouvellePartie.getStyleClass().add("button-primary");

        boutonQuitter = new Button("[ DECONNEXION ]");
        boutonQuitter.getStyleClass().add("button-danger");

        root.getChildren().addAll(titre, labelRaison, labelStatistiques, boutonNouvellePartie, boutonQuitter);
    }

    public void setGameOverInfo(String reason, int cycles, int finalResources, double finalHappiness, int population) {
        labelRaison.setText(reason);

        String stats = "RAPPORT FINAL\n"
                + "--------------------------------\n"
                + "CYCLES SURVECUS    : " + cycles + "\n"
                + "CREDITS FINAUX     : " + finalResources + "\n"
                + "BONHEUR POPULATION : " + Math.round(finalHappiness * 100) + "%\n"
                + "CITOYENS           : " + population;

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
