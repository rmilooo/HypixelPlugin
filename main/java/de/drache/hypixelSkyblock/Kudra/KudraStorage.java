package de.drache.hypixelSkyblock.Kudra;

public class KudraStorage {
    public static KudraStorage instance;

    public KudraStorage() {
        instance = this;
    }
    public static KudraStorage getInstance() {
        if (instance == null) {
            instance = new KudraStorage();
        }
        return instance;
    }
    public Kudra kudra;
}
