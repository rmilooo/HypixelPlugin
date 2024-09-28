package de.drache.hypixelSkyblock.ArmorSystem;

public enum Types {
    Helmet("Helmet"),
    Chestplate("Chestplate"),
    Leggings("Leggings"),
    Boots("Boots");
    private final String name;

    Types(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
