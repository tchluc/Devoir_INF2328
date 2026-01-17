package model;

/**
 * Centrale electrique.
 */
public class PowerPlant {

    private static int prochainId = 1;

    private int id;
    private PowerPlantType type;
    private int level;
    private double production;
    private int buildCost;
    private int upgradeCost;
    private int maintenanceCost;

    public PowerPlant(PowerPlantType type, int level) {
        if (level < 1)
            level = 1;
        if (level > 3)
            level = 3;

        this.id = prochainId++;
        this.type = type;
        this.level = level;
        calculerStatistiques();
    }

    public PowerPlant(PowerPlantType type) {
        this(type, 1);
    }

    private void calculerStatistiques() {
        if (type == PowerPlantType.SOLAR) {
            if (level == 1) {
                production = 80;
                buildCost = 100;
                upgradeCost = 80;
                maintenanceCost = 5;
            } else if (level == 2) {
                production = 150;
                buildCost = 180;
                upgradeCost = 120;
                maintenanceCost = 8;
            } else {
                production = 250;
                buildCost = 300;
                upgradeCost = 0;
                maintenanceCost = 12;
            }
        } else if (type == PowerPlantType.WIND) {
            if (level == 1) {
                production = 100;
                buildCost = 120;
                upgradeCost = 100;
                maintenanceCost = 7;
            } else if (level == 2) {
                production = 180;
                buildCost = 200;
                upgradeCost = 150;
                maintenanceCost = 10;
            } else {
                production = 300;
                buildCost = 350;
                upgradeCost = 0;
                maintenanceCost = 15;
            }
        } else if (type == PowerPlantType.COAL) {
            if (level == 1) {
                production = 150;
                buildCost = 150;
                upgradeCost = 120;
                maintenanceCost = 15;
            } else if (level == 2) {
                production = 280;
                buildCost = 250;
                upgradeCost = 180;
                maintenanceCost = 25;
            } else {
                production = 450;
                buildCost = 400;
                upgradeCost = 0;
                maintenanceCost = 40;
            }
        } else if (type == PowerPlantType.NUCLEAR) {
            if (level == 1) {
                production = 200;
                buildCost = 300;
                upgradeCost = 250;
                maintenanceCost = 20;
            } else if (level == 2) {
                production = 400;
                buildCost = 500;
                upgradeCost = 400;
                maintenanceCost = 35;
            } else {
                production = 700;
                buildCost = 800;
                upgradeCost = 0;
                maintenanceCost = 60;
            }
        } else {
            if (level == 1) {
                production = 300;
                buildCost = 500;
                upgradeCost = 400;
                maintenanceCost = 30;
            } else if (level == 2) {
                production = 600;
                buildCost = 900;
                upgradeCost = 700;
                maintenanceCost = 50;
            } else {
                production = 1000;
                buildCost = 1500;
                upgradeCost = 0;
                maintenanceCost = 80;
            }
        }
    }

    public boolean upgrade() {
        if (level >= 3) {
            return false;
        }
        level++;
        calculerStatistiques();
        return true;
    }

    public boolean canUpgrade() {
        return level < 3;
    }

    public static int getBuildCost(PowerPlantType type) {
        if (type == PowerPlantType.SOLAR)
            return 100;
        else if (type == PowerPlantType.WIND)
            return 120;
        else if (type == PowerPlantType.COAL)
            return 150;
        else if (type == PowerPlantType.NUCLEAR)
            return 300;
        else
            return 500;
    }

    public static double getBaseProduction(PowerPlantType type) {
        if (type == PowerPlantType.SOLAR)
            return 80;
        else if (type == PowerPlantType.WIND)
            return 100;
        else if (type == PowerPlantType.COAL)
            return 150;
        else if (type == PowerPlantType.NUCLEAR)
            return 200;
        else
            return 300;
    }

    public int getId() {
        return id;
    }

    public PowerPlantType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public double getProduction() {
        return production;
    }

    public int getBuildCost() {
        return buildCost;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public int getMaintenanceCost() {
        return maintenanceCost;
    }

    @Override
    public String toString() {
        return "Centrale " + type.getName() + " #" + id
                + " (Niv." + level + ")"
                + " - Production: " + Math.round(production) + " kW"
                + " - Entretien: " + maintenanceCost + " cr/cycle";
    }
}
