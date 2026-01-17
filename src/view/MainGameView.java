package src.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import src.model.City;
import src.model.GameState;
import src.model.PowerPlant;
import src.model.Residence;


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
    private FlowPane gridCentrales;
    private FlowPane gridResidences;

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
        VBox.setVgrow(section, Priority.ALWAYS); // Allow section to grow

        // Centrales
        VBox boiteCentrales = new VBox(8);
        boiteCentrales.setPadding(new Insets(12));
        boiteCentrales.getStyleClass().add("panel");
        boiteCentrales.setPrefWidth(450);
        VBox.setVgrow(boiteCentrales, Priority.ALWAYS); // Grow vertical

        Label titreCentrales = new Label("CENTRALES");
        titreCentrales.getStyleClass().add("panel-header");

        gridCentrales = new FlowPane();
        gridCentrales.setHgap(10);
        gridCentrales.setVgap(10);
        gridCentrales.setPrefWrapLength(400); // Force wrapping

        ScrollPane scrollCentrales = new ScrollPane(gridCentrales);
        scrollCentrales.setFitToWidth(true);
        scrollCentrales.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollCentrales.getStyleClass().add("scroll-pane");
        VBox.setVgrow(scrollCentrales, Priority.ALWAYS);

        boiteCentrales.getChildren().addAll(titreCentrales, scrollCentrales);

        // Residences
        VBox boiteResidences = new VBox(8);
        boiteResidences.setPadding(new Insets(12));
        boiteResidences.getStyleClass().add("panel");
        boiteResidences.setPrefWidth(450);
        VBox.setVgrow(boiteResidences, Priority.ALWAYS);

        Label titreResidences = new Label("RÉSIDENCES");
        titreResidences.getStyleClass().add("panel-header");

        gridResidences = new FlowPane();
        gridResidences.setHgap(10);
        gridResidences.setVgap(10);
        gridResidences.setPrefWrapLength(400);

        ScrollPane scrollResidences = new ScrollPane(gridResidences);
        scrollResidences.setFitToWidth(true);
        scrollResidences.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollResidences.getStyleClass().add("scroll-pane");
        VBox.setVgrow(scrollResidences, Priority.ALWAYS);

        boiteResidences.getChildren().addAll(titreResidences, scrollResidences);

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

        gridCentrales.getChildren().clear();
        for (PowerPlant centrale : city.getPowerPlants()) {
            gridCentrales.getChildren().add(createPlantCard(centrale));
        }

        gridResidences.getChildren().clear();
        List<Residence> residences = city.getResidences();
        for (int i = 0; i < residences.size(); i++) {
            gridResidences.getChildren().add(createResidenceCard(residences.get(i), i + 1));
        }
    }

    private VBox createPlantCard(PowerPlant plant) {
        VBox card = new VBox(5);
        card.getStyleClass().add("card");

        Label header = new Label(plant.getType().getIcon() + " " + plant.getType().getName());
        header.getStyleClass().add("card-header");

        Label prodLabel = new Label("Prod: " + Math.round(plant.getProduction()) + " kW");
        prodLabel.getStyleClass().add("card-stat");

        // Status indicator (simple text color or icon)
        Label statusLabel = new Label("ACTIF");
        statusLabel.setStyle("-fx-text-fill: #00ff00; -fx-font-weight: bold; -fx-font-size: 10px;");

        card.getChildren().addAll(header, prodLabel, statusLabel);
        return card;
    }

    private VBox createResidenceCard(Residence residence, int index) {
        VBox card = new VBox(5);
        card.getStyleClass().add("card");

        Label header = new Label("Résidence #" + index);
        header.getStyleClass().add("card-header");

        Label popLabel = new Label("Pop: " + residence.getInhabitants());
        popLabel.getStyleClass().add("card-stat");

        Label consoLabel = new Label("Besoin: " + Math.round(residence.getEnergyNeed()) + " kW");
        consoLabel.getStyleClass().add("card-stat");

        // Satisfaction bar
        ProgressBar satBar = new ProgressBar(residence.getSatisfaction());
        satBar.setPrefWidth(180);
        satBar.getStyleClass().add("progress-bar");

        card.getChildren().addAll(header, popLabel, consoLabel, satBar);
        return card;
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

    public FlowPane getPowerPlantsGrid() {
        return gridCentrales;
    }

    public BorderPane getRoot() {
        return root;
    }

    public Scene createScene() {
        Scene scene = new Scene(root, 1400, 850);
        try {
            java.net.URL cssUrl = getClass().getResource("styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("Avertissement : styles.css non trouvé (MainGameView)");
            }
        } catch (Exception e) {
            System.err.println("Impossible de charger styles.css: " + e.getMessage());
        }
        return scene;
    }
}
