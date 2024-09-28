package de.drache.hypixelSkyblock.Items;

public enum ItemTypes {
    Staff("Staff"),
    Shortbow("Shortbow"),
    Sword("Sword"),
    None("");
    private final String name;

    ItemTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
