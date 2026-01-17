package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represente la colonie spatiale.
 */
public class City {

    private String name;
    private ArrayList<Residence> residences;
    private ArrayList<PowerPlant> powerPlants;

    public City(String name) {
        this.name = name;
        this.residences = new ArrayList<>();
        this.powerPlants = new ArrayList<>();
    }

    public void addResidence(Residence residence) {
        residences.add(residence);
    }

    public void addPowerPlant(PowerPlant powerPlant) {
        powerPlants.add(powerPlant);
    }

    public void removePowerPlant(PowerPlant powerPlant) {
        powerPlants.remove(powerPlant);
    }

    public double getTotalEnergyDemand() {
        double total = 0;
        for (int i = 0; i < residences.size(); i++) {
            total += residences.get(i).getEnergyNeed();
        }
        return total;
    }

    public double getTotalEnergyProduction() {
        double total = 0;
        for (int i = 0; i < powerPlants.size(); i++) {
            total += powerPlants.get(i).getProduction();
        }
        return total;
    }

    public int getTotalMaintenanceCost() {
        int total = 0;
        for (int i = 0; i < powerPlants.size(); i++) {
            total += powerPlants.get(i).getMaintenanceCost();
        }
        return total;
    }

    public int getTotalPopulation() {
        int total = 0;
        for (int i = 0; i < residences.size(); i++) {
            total += residences.get(i).getInhabitants();
        }
        return total;
    }

    public double getAverageSatisfaction() {
        if (residences.isEmpty()) {
            return 1.0;
        }

        double somme = 0;
        for (int i = 0; i < residences.size(); i++) {
            somme += residences.get(i).getSatisfaction();
        }
        return somme / residences.size();
    }

    /**
     * Distribue l'energie et retourne le revenu genere.
     */
    public double distributeEnergy() {
        double productionTotale = getTotalEnergyProduction();
        double demandeTotale = getTotalEnergyDemand();
        double revenuTotal = 0;

        if (demandeTotale == 0) {
            return 0;
        }

        if (productionTotale >= demandeTotale) {
            for (int i = 0; i < residences.size(); i++) {
                Residence residence = residences.get(i);
                double energieFournie = residence.getEnergyNeed();
                residence.updateSatisfaction(energieFournie);
                revenuTotal += residence.calculateRevenue(energieFournie);
            }
        } else {
            double ratio = productionTotale / demandeTotale;
            for (int i = 0; i < residences.size(); i++) {
                Residence residence = residences.get(i);
                double energieFournie = residence.getEnergyNeed() * ratio;
                residence.updateSatisfaction(energieFournie);
                revenuTotal += residence.calculateRevenue(energieFournie);
            }
        }

        return revenuTotal;
    }

    public void simulateGrowth() {
        for (int i = 0; i < residences.size(); i++) {
            residences.get(i).simulateGrowth();
        }
    }

    public String getName() {
        return name;
    }

    public List<Residence> getResidences() {
        return new ArrayList<>(residences);
    }

    public List<PowerPlant> getPowerPlants() {
        return new ArrayList<>(powerPlants);
    }

    public int getResidenceCount() {
        return residences.size();
    }

    public int getPowerPlantCount() {
        return powerPlants.size();
    }
}
