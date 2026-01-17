package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.City;
import model.GameState;
import model.PowerPlant;
import model.Residence;

import java.util.List;

/**
 * Vue principale du jeu.
 */
public class MainGameView {

    private BorderPane root;

    // Barre du haut
    private Label labelResources;
    private Label labelBonheur;
    private Label labelCycle;
    private Label labelNomColonie;
    private ProgressBar barreBonheur;

    // Panneau gauche
    private Button boutonConstruire;
    private Button boutonAmeliorer;
    private Button boutonDetails;
    private Button boutonCycleSuivant;

    // Panneau central
    private VBox panneauCentral;
    private ProgressBar barreProduction;
    private ProgressBar barreDemande;
    private Label labelProduction;
    private Label labelDemande;
    private Label labelBalance;
    private ListView<String> listeCentrales;
    private ListView<String> listeResidences;

    // Panneau bas (Logs)
    private TextArea zoneLog;

    public MainGameView() {
        creerVue();
    }

    private void creerVue() {
        root = new BorderPane();
        root.getStyleClass().add("game-root");

        creerBarreHaut();
        creerPanneauGauche();
        creerPanneauCentral();
        creerPanneauBas();
    }

    private void creerBarreHaut() {
        HBox barreHaut = new HBox(30);
        barreHaut.setPadding(new Insets(15, 20, 15, 20));
        barreHaut.setAlignment(Pos.CENTER_LEFT);
        barreHaut.getStyleClass().add("info-bar");

        labelNomColonie = new Label("COLONIE: NOVA-7");
        labelNomColonie.setStyle("-fx-font-weight: bold; -fx-font-family: 'Segoe UI Black'; -fx-font-size: 18px;");

        labelCycle = new Label("[ CYCLE: 000 ]");
        labelCycle.getStyleClass().add("label-stat");

        labelResources = new Label("[ CREDITS: 0 ]");
        labelResources.getStyleClass().add("label-stat");

        HBox boiteBonheur = new HBox(10);
        boiteBonheur.setAlignment(Pos.CENTER_LEFT);
        labelBonheur = new Label("BONHEUR:");
        labelBonheur.getStyleClass().add("label-stat");

        barreBonheur = new ProgressBar(0);
        barreBonheur.setPrefWidth(150);
        barreBonheur.getStyleClass().add("progress-bar");

        boiteBonheur.getChildren().addAll(labelBonheur, barreBonheur);

        Region espaceur = new Region();
        HBox.setHgrow(espaceur, Priority.ALWAYS);

        barreHaut.getChildren().addAll(labelNomColonie, espaceur, labelCycle, labelResources, boiteBonheur);
        root.setTop(barreHaut);
    }

    private void creerPanneauGauche() {
        VBox panneauGauche = new VBox(15);
        panneauGauche.setPadding(new Insets(20));
        panneauGauche.setPrefWidth(220);
        panneauGauche.getStyleClass().add("panel");

        Label labelTitre = new Label("// COMMANDES");
        labelTitre.getStyleClass().add("panel-header");

        boutonConstruire = new Button("CONSTRUIRE");
        boutonConstruire.setMaxWidth(Double.MAX_VALUE);
        boutonConstruire.getStyleClass().add("button");

        boutonAmeliorer = new Button("AMELIORER");
        boutonAmeliorer.setMaxWidth(Double.MAX_VALUE);
        boutonAmeliorer.getStyleClass().add("button");

        boutonDetails = new Button("DETAILS");
        boutonDetails.setMaxWidth(Double.MAX_VALUE);
        boutonDetails.getStyleClass().add("button");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        boutonCycleSuivant = new Button(">> CYCLE SUIVANT");
        boutonCycleSuivant.setMaxWidth(Double.MAX_VALUE);
        boutonCycleSuivant.getStyleClass().addAll("button", "button-primary");

        panneauGauche.getChildren().addAll(labelTitre, boutonConstruire, boutonAmeliorer, boutonDetails, spacer,
                boutonCycleSuivant);
        root.setLeft(panneauGauche);
    }

    private void creerPanneauCentral() {
        panneauCentral = new VBox(15);
        panneauCentral.setPadding(new Insets(15));

        VBox sectionEnergie = creerSectionEnergie();
        HBox sectionBatiments = creerSectionBatiments();

        panneauCentral.getChildren().addAll(sectionEnergie, sectionBatiments);
        root.setCenter(panneauCentral);
    }

    private VBox creerSectionEnergie() {
        VBox section = new VBox(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("panel");

        Label titre = new Label("STATUT RESEAU ENERGETIQUE");
        titre.getStyleClass().add("panel-header");

        // Utilisation d'une grille ou VBox pour aligner proprement

        // Production
        HBox ligneProduction = new HBox(10);
        ligneProduction.setAlignment(Pos.CENTER_LEFT);
        labelProduction = new Label("PROD: 0 kW");
        labelProduction.setPrefWidth(120);
        labelProduction.getStyleClass().add("label-stat");

        barreProduction = new ProgressBar(0);
        barreProduction.setPrefWidth(600);
        barreProduction.getStyleClass().addAll("progress-bar", "progress-bar-success");
        ligneProduction.getChildren().addAll(labelProduction, barreProduction);

        // Demande
        HBox ligneDemande = new HBox(10);
        ligneDemande.setAlignment(Pos.CENTER_LEFT);
        labelDemande = new Label("DEMANDE: 0 kW");
        labelDemande.setPrefWidth(120);
        labelDemande.getStyleClass().add("label-stat");

        barreDemande = new ProgressBar(0);
        barreDemande.setPrefWidth(600);
        barreDemande.getStyleClass().addAll("progress-bar", "progress-bar-warning");
        ligneDemande.getChildren().addAll(labelDemande, barreDemande);

        // Balance text only, no style modification here, controlled by updateDisplay
        labelBalance = new Label("BALANCE: 0 kW");
        labelBalance.getStyleClass().add("label-stat");
        labelBalance.setStyle("-fx-font-size: 16px; -fx-padding: 10px 0 0 0;");

        section.getChildren().addAll(titre, ligneProduction, ligneDemande, labelBalance);
        return section;
    }

    private HBox creerSectionBatiments() {
        HBox section = new HBox(20);
        section.setAlignment(Pos.TOP_CENTER);

        // Centrales
        VBox boiteCentrales = new VBox(8);
        boiteCentrales.setPadding(new Insets(12));
        boiteCentrales.getStyleClass().add("panel");
        boiteCentrales.setPrefWidth(450);

        Label titreCentrales = new Label("CENTRALES");
        titreCentrales.getStyleClass().add("panel-header");

        listeCentrales = new ListView<>();
        listeCentrales.setPrefHeight(180);
        listeCentrales.getStyleClass().add("list-view");

        boiteCentrales.getChildren().addAll(titreCentrales, listeCentrales);

        // Residences
        VBox boiteResidences = new VBox(8);
        boiteResidences.setPadding(new Insets(12));
        boiteResidences.getStyleClass().add("panel");
        boiteResidences.setPrefWidth(450);

        Label titreResidences = new Label("RÉSIDENCES");
        titreResidences.getStyleClass().add("panel-header");

        listeResidences = new ListView<>();
        listeResidences.setPrefHeight(180);
        listeResidences.getStyleClass().add("list-view");

        boiteResidences.getChildren().addAll(titreResidences, listeResidences);

        section.getChildren().addAll(boiteCentrales, boiteResidences);
        return section;
    }

    private void creerPanneauBas() {
        VBox panneauBas = new VBox(8);
        panneauBas.setPadding(new Insets(12, 15, 12, 15));
        panneauBas.setPrefHeight(150);

        Label titre = new Label("JOURNAL");
        titre.getStyleClass().add("panel-header");

        zoneLog = new TextArea();
        zoneLog.setEditable(false);
        zoneLog.setWrapText(true);
        zoneLog.getStyleClass().add("log-area");
        VBox.setVgrow(zoneLog, Priority.ALWAYS);

        panneauBas.getChildren().addAll(titre, zoneLog);
        root.setBottom(panneauBas);
    }

    public void updateDisplay(GameState gameState) {
        City city = gameState.getCity();

        labelNomColonie.setText(city.getName());
        labelCycle.setText("[ CYC: " + gameState.getCurrentCycle() + " ]");
        labelResources.setText("[ CR: " + gameState.getResources() + " ]");

        double bonheur = gameState.getHappiness();
        // Use [ ] for bar
        labelBonheur.setText(Math.round(bonheur * 100) + "% " + gameState.getHappinessStatus());
        barreBonheur.setProgress(bonheur);

        barreBonheur.getStyleClass().removeAll("progress-bar-success", "progress-bar-warning", "progress-bar-danger");
        if (bonheur >= 0.7) {
            barreBonheur.getStyleClass().add("progress-bar-success");
        } else if (bonheur >= 0.4) {
            barreBonheur.getStyleClass().add("progress-bar-warning");
        } else {
            barreBonheur.getStyleClass().add("progress-bar-danger");
        }

        double production = city.getTotalEnergyProduction();
        double demande = city.getTotalEnergyDemand();

        labelProduction.setText("Production: " + Math.round(production) + " kW");
        labelDemande.setText("Demande: " + Math.round(demande) + " kW");

        double maximum = Math.max(production, demande);
        if (maximum > 0) {
            barreProduction.setProgress(production / maximum);
            barreDemande.setProgress(demande / maximum);
        } else {
            barreProduction.setProgress(0);
            barreDemande.setProgress(0);
        }

        double balance = production - demande;
        if (balance >= 0) {
            labelBalance.setText("EXCEDENT: +" + Math.round(balance) + " kW");
            // White text for success in BW theme
            labelBalance.setStyle("-fx-font-size: 16px; -fx-padding: 10px 0 0 0; -fx-text-fill: #ffffff;");
        } else {
            labelBalance.setText("DEFICIT: " + Math.round(balance) + " kW");
            // Use gray or Keep white but maybe add an icon?
            // In strict BW, we use text or border styles.
            // Let's keep it white but clearly labelled 'DEFICIT' which is scary enough.
            labelBalance.setStyle(
                    "-fx-font-size: 16px; -fx-padding: 10px 0 0 0; -fx-text-fill: #ffffff; -fx-font-style: italic;");
        }

        listeCentrales.getItems().clear();
        for (PowerPlant centrale : city.getPowerPlants()) {
            listeCentrales.getItems().add(centrale.getType().getIcon() + " " + centrale.toString());
        }

        listeResidences.getItems().clear();
        for (Residence residence : city.getResidences()) {
            listeResidences.getItems().add(residence.toString());
        }
    }

    public void addLogs(List<String> logs) {
        for (String message : logs) {
            zoneLog.appendText(message + "\n");
        }
        zoneLog.setScrollTop(Double.MAX_VALUE);
    }

    public void clearLogs() {
        zoneLog.clear();
    }

    public Button getBuildButton() {
        return boutonConstruire;
    }

    public Button getUpgradeButton() {
        return boutonAmeliorer;
    }

    public Button getDetailsButton() {
        return boutonDetails;
    }

    public Button getNextCycleButton() {
        return boutonCycleSuivant;
    }

    public ListView<String> getPowerPlantsList() {
        return listeCentrales;
    }

    public BorderPane getRoot() {
        return root;
    }

    public Scene createScene() {
        Scene scene = new Scene(root, 1400, 850);
        try {
            String cheminCSS = getClass().getResource("styles.css").toExternalForm();
            scene.getStylesheets().add(cheminCSS);
        } catch (Exception e) {
            System.err.println("Impossible de charger styles.css: " + e.getMessage());
        }
        return scene;
    }
}
