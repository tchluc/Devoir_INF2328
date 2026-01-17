package model;

import java.util.Random;

/**
 * Residence - batiment ou vivent des habitants.
 */
public class Residence {

    private Random random;
    private int id;
    private int level;
    private int inhabitants;
    private double energyNeed;
    private double purchasingPower;
    private double satisfaction;

    public Residence(int id, int level) {
        this.random = new Random();

        if (level < 1)
            level = 1;
        if (level > 3)
            level = 3;

        this.id = id;
        this.level = level;

        if (level == 1) {
            this.energyNeed = 50 + random.nextDouble() * 50;
            this.purchasingPower = 0.8 + random.nextDouble() * 0.4;
            this.inhabitants = 10 + random.nextInt(21);
        } else if (level == 2) {
            this.energyNeed = 100 + random.nextDouble() * 100;
            this.purchasingPower = 1.5 + random.nextDouble() * 0.5;
            this.inhabitants = 30 + random.nextInt(31);
        } else {
            this.energyNeed = 200 + random.nextDouble() * 150;
            this.purchasingPower = 2.5 + random.nextDouble() * 1.0;
            this.inhabitants = 60 + random.nextInt(41);
        }

        this.satisfaction = 0.8 + random.nextDouble() * 0.2;
    }

    public void updateSatisfaction(double energyReceived) {
        double ratio = energyReceived / energyNeed;

        if (ratio >= 1.0) {
            satisfaction = Math.min(satisfaction + 0.05, 1.0);
        } else if (ratio >= 0.8) {
            satisfaction = Math.max(satisfaction - 0.02, 0.0);
        } else if (ratio >= 0.5) {
            satisfaction = Math.max(satisfaction - 0.1, 0.0);
        } else {
            satisfaction = Math.max(satisfaction - 0.2, 0.0);
        }
    }

    public double calculateRevenue(double energyProvided) {
        double energieAFacturer = Math.min(energyProvided, energyNeed);
        return energieAFacturer * purchasingPower;
    }

    public void simulateGrowth() {
        double chanceDeCroissance = satisfaction * 0.1;

        if (random.nextDouble() < chanceDeCroissance) {
            double facteur = 1.02 + random.nextDouble() * 0.03;
            energyNeed *= facteur;
            inhabitants = (int) (inhabitants * facteur);
        }
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public int getInhabitants() {
        return inhabitants;
    }

    public double getEnergyNeed() {
        return energyNeed;
    }

    public double getPurchasingPower() {
        return purchasingPower;
    }

    public double getSatisfaction() {
        return satisfaction;
    }

    @Override
    public String toString() {
        return "Résidence #" + id
                + " (Niv." + level + ")"
                + " - " + inhabitants + " hab."
                + " - Besoin: " + Math.round(energyNeed) + " kW"
                + " - Satisfaction: " + Math.round(satisfaction * 100) + "%";
    }
}
