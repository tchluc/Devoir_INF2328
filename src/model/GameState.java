package src.model;

/**
 * Etat global du jeu.
 */
public class GameState {

    public static final int INITIAL_RESOURCES = 500;
    public static final double MIN_HAPPINESS_THRESHOLD = 0.3;
    public static final double HAPPINESS_DECAY_RATE = 0.02;

    private int resources;
    private double happiness;
    private int currentCycle;
    private City city;
    private boolean gameOver;
    private String gameOverReason;

    public GameState(String cityName) {
        this.resources = INITIAL_RESOURCES;
        this.happiness = 0.8;
        this.currentCycle = 0;
        this.city = new City(cityName);
        this.gameOver = false;
        this.gameOverReason = "";
    }

    public void addResources(int amount) {
        this.resources += amount;
    }

    public boolean spendResources(int amount) {
        if (resources >= amount) {
            resources -= amount;
            return true;
        }
        return false;
    }

    public void setHappiness(double happiness) {
        if (happiness < 0.0) {
            this.happiness = 0.0;
        } else if (happiness > 1.0) {
            this.happiness = 1.0;
        } else {
            this.happiness = happiness;
        }

        if (this.happiness < MIN_HAPPINESS_THRESHOLD) {
            gameOver = true;
            gameOverReason = "Le bonheur de la population est tombé trop bas. Le conseil colonial vous a démis de vos fonctions.";
        }
    }

    public void adjustHappiness(double delta) {
        setHappiness(happiness + delta);
    }

    public void nextCycle() {
        currentCycle++;
    }

    public boolean hasEnoughResources(int amount) {
        return resources >= amount;
    }

    public void endGame(String reason) {
        this.gameOver = true;
        this.gameOverReason = reason;
    }

    public String getHappinessStatus() {
        if (happiness >= 0.8) {
            return "Excellente";
        } else if (happiness >= 0.6) {
            return "Bonne";
        } else if (happiness >= 0.4) {
            return "Moyenne";
        } else if (happiness >= 0.3) {
            return "Faible";
        } else {
            return "Critique";
        }
    }

    public double getEnergyBalance() {
        return city.getTotalEnergyProduction() - city.getTotalEnergyDemand();
    }

    public double getEnergyRatio() {
        double demande = city.getTotalEnergyDemand();
        if (demande == 0) {
            return 1.0;
        }
        return city.getTotalEnergyProduction() / demande;
    }

    public int getResources() {
        return resources;
    }

    public double getHappiness() {
        return happiness;
    }

    public int getCurrentCycle() {
        return currentCycle;
    }

    public City getCity() {
        return city;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getGameOverReason() {
        return gameOverReason;
    }
}
