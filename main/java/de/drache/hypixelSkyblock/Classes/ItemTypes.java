package de.drache.hypixelSkyblock.Classes;

public enum ItemTypes {
    Staff("Staff"),
    Sword("Sword"),
    Shortbow("Shortbow"),
    Boots("Boots");
    private final String name;

    ItemTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
