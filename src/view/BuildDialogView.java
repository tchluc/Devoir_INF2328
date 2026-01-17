package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.PowerPlant;
import model.PowerPlantType;

/**
 * Dialogue de construction de centrale.
 */
public class BuildDialogView extends Dialog<PowerPlantType> {

    private ToggleGroup groupeBoutons;
    private Label labelCout;
    private Label labelProduction;

    public BuildDialogView(int creditsDuJoueur) {
        setTitle("Construire une Centrale");
        setHeaderText("Sélectionnez le type de centrale à construire");

        DialogPane panneauDialogue = getDialogPane();
        panneauDialogue.getStyleClass().add("dialog-pane");

        VBox contenu = new VBox(15);
        contenu.setPadding(new Insets(20));

        groupeBoutons = new ToggleGroup();
        PowerPlantType[] tousLesTypes = PowerPlantType.values();

        for (int i = 0; i < tousLesTypes.length; i++) {
            RadioButton bouton = creerBoutonCentrale(tousLesTypes[i]);
            groupeBoutons.getToggles().add(bouton);
            contenu.getChildren().add(bouton);
        }

        if (!groupeBoutons.getToggles().isEmpty()) {
            groupeBoutons.getToggles().get(0).setSelected(true);
        }

        VBox panneauInfo = new VBox(10);
        panneauInfo.setPadding(new Insets(15));
        panneauInfo.getStyleClass().add("panel");

        labelCout = new Label();
        labelCout.getStyleClass().add("label-stat");

        labelProduction = new Label();
        labelProduction.getStyleClass().add("label-stat");

        Label labelCredits = new Label("Ressources disponibles: " + creditsDuJoueur + " crédits");
        labelCredits.setStyle("-fx-text-fill: #00d4ff;");

        panneauInfo.getChildren().addAll(labelCout, labelProduction, labelCredits);
        contenu.getChildren().add(panneauInfo);

        groupeBoutons.selectedToggleProperty().addListener((obs, old, val) -> {
            if (val != null)
                mettreAJourInfos();
        });

        mettreAJourInfos();
        panneauDialogue.setContent(contenu);

        ButtonType boutonConstruire = new ButtonType("Construire", ButtonBar.ButtonData.OK_DONE);
        panneauDialogue.getButtonTypes().addAll(boutonConstruire, ButtonType.CANCEL);

        setResultConverter(typeBouton -> {
            if (typeBouton == boutonConstruire) {
                Toggle selection = groupeBoutons.getSelectedToggle();
                if (selection != null)
                    return (PowerPlantType) selection.getUserData();
            }
            return null;
        });

        try {
            String cheminCSS = getClass().getResource("styles.css").toExternalForm();
            panneauDialogue.getStylesheets().add(cheminCSS);
        } catch (Exception e) {
            System.err.println("Impossible de charger styles.css: " + e.getMessage());
        }
    }

    private RadioButton creerBoutonCentrale(PowerPlantType type) {
        RadioButton bouton = new RadioButton();
        bouton.setUserData(type);

        HBox conteneur = new HBox(15);
        conteneur.setAlignment(Pos.CENTER_LEFT);
        conteneur.setPadding(new Insets(10));
        conteneur.getStyleClass().add("panel");

        Label labelIcone = new Label(type.getIcon());
        labelIcone.getStyleClass().add("icon-label");

        VBox details = new VBox(5);
        Label labelNom = new Label(type.getName());
        labelNom.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        int cout = PowerPlant.getBuildCost(type);
        double production = PowerPlant.getBaseProduction(type);
        Label labelInfo = new Label("Coût: " + cout + " cr | Production: " + Math.round(production) + " kW");
        labelInfo.getStyleClass().add("label-secondary");

        details.getChildren().addAll(labelNom, labelInfo);
        conteneur.getChildren().addAll(labelIcone, details);

        bouton.setGraphic(conteneur);
        bouton.getStyleClass().add("radio-button");

        return bouton;
    }

    private void mettreAJourInfos() {
        Toggle selection = groupeBoutons.getSelectedToggle();

        if (selection != null) {
            PowerPlantType type = (PowerPlantType) selection.getUserData();
            int cout = PowerPlant.getBuildCost(type);
            double production = PowerPlant.getBaseProduction(type);

            labelCout.setText("💰 Coût de construction: " + cout + " crédits");
            labelProduction.setText("⚡ Production: " + Math.round(production) + " kW");
        }
    }
}
