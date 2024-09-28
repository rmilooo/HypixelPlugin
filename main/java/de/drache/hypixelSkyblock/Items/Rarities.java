package de.drache.hypixelSkyblock.Items;

public enum Rarities {
    Common("Common", "7"),
    Uncommon("Uncommon", "a"),
    Rare("Rare", "9"),
    Epic("Epic", "5"),
    Legendary("Legendary", "6"),
    Mythical("Mythical", "d"),
    Divine("Divine", "b");

    private final String name;
    private final String colorCode;

    private Rarities(String name, String colorCode) {
        this.name = name;
        this.colorCode = colorCode;
    }

    public String getName() {
        return name;
    }

    public String getColorCode() {
        return colorCode;
    }
}
