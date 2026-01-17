package src.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Moteur du jeu - Gere la logique principale et la simulation.
 */
public class GameEngine {

    private Random random;
    private GameState gameState;
    private ArrayList<String> eventLog;

    private String[] evenementsPositifs = {
            "Découverte d'un gisement de cristaux énergétiques ! +100 crédits",
            "Les ingénieurs ont optimisé le réseau. Production +5% ce cycle.",
            "Arrivée d'une navette avec des provisions. Moral en hausse !",
            "Une innovation technique réduit les coûts d'entretien de 20% ce cycle."
    };

    private String[] evenementsNegatifs = {
            "Tempête solaire détectée. Production réduite de 10% ce cycle.",
            "Panne dans un secteur résidentiel. Satisfaction en baisse.",
            "Hausse des prix des matériaux de construction.",
            "Vague de chaleur inhabituelle. Demande énergétique +15%."
    };

    public GameEngine(String colonyName) {
        this.random = new Random();
        this.gameState = new GameState(colonyName);
        this.eventLog = new ArrayList<>();
        initialiserJeu();
    }

    private void initialiserJeu() {
        City city = gameState.getCity();

        city.addResidence(new Residence(1, 1));
        city.addResidence(new Residence(2, 1));
        city.addResidence(new Residence(3, 2));
        city.addResidence(new Residence(4, 2));
        city.addResidence(new Residence(5, 3));

        PowerPlant centraleDeDepart = new PowerPlant(PowerPlantType.SOLAR, 1);
        city.addPowerPlant(centraleDeDepart);

        addLog("Bienvenue sur la colonie " + city.getName() + " !");
        addLog("Vous commencez avec " + gameState.getResources() + " crédits.");
        addLog("Population initiale: " + city.getTotalPopulation() + " habitants dans " + city.getResidenceCount()
                + " résidences.");
    }

    public boolean buildPowerPlant(PowerPlantType type) {
        int cout = PowerPlant.getBuildCost(type);

        if (!gameState.hasEnoughResources(cout)) {
            addLog("Ressources insuffisantes pour construire une centrale " + type.getName() + ". Coût: " + cout
                    + " crédits.");
            return false;
        }

        gameState.spendResources(cout);
        PowerPlant nouvelleCentrale = new PowerPlant(type);
        gameState.getCity().addPowerPlant(nouvelleCentrale);
        addLog("Centrale " + type.getName() + " construite ! Production: "
                + Math.round(nouvelleCentrale.getProduction()) + " kW, Coût: " + cout + " crédits.");

        return true;
    }

    public boolean upgradePowerPlant(PowerPlant powerPlant) {
        if (!powerPlant.canUpgrade()) {
            addLog("Cette centrale est déjà au niveau maximum !");
            return false;
        }

        int cout = powerPlant.getUpgradeCost();

        if (!gameState.hasEnoughResources(cout)) {
            addLog("Ressources insuffisantes pour améliorer cette centrale. Coût: " + cout + " crédits.");
            return false;
        }

        int ancienNiveau = powerPlant.getLevel();
        double ancienneProduction = powerPlant.getProduction();

        gameState.spendResources(cout);
        powerPlant.upgrade();

        addLog("Centrale " + powerPlant.getType().getName() + " améliorée: Niv." + ancienNiveau + " → Niv."
                + powerPlant.getLevel() + ". Production: " + Math.round(ancienneProduction) + " → "
                + Math.round(powerPlant.getProduction()) + " kW.");

        return true;
    }

    public void processCycle() {
        gameState.nextCycle();
        addLog("\n=== CYCLE " + gameState.getCurrentCycle() + " ===");

        City city = gameState.getCity();

        double revenus = city.distributeEnergy();
        int coutEntretien = city.getTotalMaintenanceCost();
        int bilanNet = (int) revenus - coutEntretien;
        gameState.addResources(bilanNet);

        addLog("Revenus de vente: " + Math.round(revenus) + " crédits | Entretien: " + coutEntretien
                + " crédits | Net: " + bilanNet + " crédits");

        double satisfactionMoyenne = city.getAverageSatisfaction();
        double nouveauBonheur = satisfactionMoyenne * 0.7 + gameState.getHappiness() * 0.3;

        double ratioEnergie = gameState.getEnergyRatio();
        if (ratioEnergie < 0.8) {
            nouveauBonheur -= GameState.HAPPINESS_DECAY_RATE;
            addLog("⚠ Production insuffisante ! Le moral baisse...");
        }

        gameState.setHappiness(nouveauBonheur);
        city.simulateGrowth();

        if (random.nextDouble() < 0.2) {
            declencherEvenementAleatoire();
        }

        addLog("Énergie: " + Math.round(city.getTotalEnergyProduction()) + "/" + Math.round(city.getTotalEnergyDemand())
                + " kW | Bonheur: " + Math.round(gameState.getHappiness() * 100) + "% ("
                + gameState.getHappinessStatus() + ")");

        if (gameState.getResources() < 0 && bilanNet < 0) {
            gameState.endGame("Vous êtes en faillite ! Les créanciers ont repris le contrôle de la colonie.");
        }

        int cycleActuel = gameState.getCurrentCycle();
        if (cycleActuel % 5 == 0 && satisfactionMoyenne > 0.7) {
            int nouvelId = city.getResidenceCount() + 1;
            int niveauAleatoire = 1 + random.nextInt(3);

            Residence nouvelleResidence = new Residence(nouvelId, niveauAleatoire);
            city.addResidence(nouvelleResidence);

            addLog("✨ Nouvelle résidence construite (Niv." + niveauAleatoire + ") ! La colonie s'agrandit.");
        }
    }

    private void declencherEvenementAleatoire() {
        boolean estPositif = random.nextBoolean();

        if (estPositif) {
            int index = random.nextInt(evenementsPositifs.length);
            String evenement = evenementsPositifs[index];
            addLog("📰 " + evenement);

            if (evenement.contains("crédits")) {
                gameState.addResources(100);
            }
            if (evenement.contains("Moral")) {
                gameState.adjustHappiness(0.05);
            }
        } else {
            int index = random.nextInt(evenementsNegatifs.length);
            String evenement = evenementsNegatifs[index];
            addLog("📰 " + evenement);

            if (evenement.contains("Satisfaction")) {
                gameState.adjustHappiness(-0.05);
            }
        }
    }

    public void addLog(String message) {
        eventLog.add(message);
        if (eventLog.size() > 50) {
            eventLog.remove(0);
        }
    }

    public List<String> getRecentLogs(int count) {
        int taille = eventLog.size();
        int debut = taille <= count ? 0 : taille - count;

        ArrayList<String> resultat = new ArrayList<>();
        for (int i = debut; i < taille; i++) {
            resultat.add(eventLog.get(i));
        }
        return resultat;
    }

    public void resetGame(String colonyName) {
        this.gameState = new GameState(colonyName);
        this.eventLog.clear();
        initialiserJeu();
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<String> getAllLogs() {
        return new ArrayList<>(eventLog);
    }
}
