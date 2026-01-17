package model;

/**
 * Types de centrales electriques disponibles.
 */
public enum PowerPlantType {

    SOLAR("Solaire", "☀️"),
    WIND("Éolienne", "🌪️"),
    COAL("Charbon", "⚫"),
    NUCLEAR("Nucléaire", "⚛️"),
    FUSION("Fusion", "✨");

    private String name;
    private String icon;

    PowerPlantType(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return name;
    }
}
