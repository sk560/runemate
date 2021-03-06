package com.runemate.geashawscripts.LazyChaosDruids.Data;

public enum Loot {
    GUAM("Guam", 199),
    MARRENTILL("Marrentill", 201),
    TARROMIN("Tarromin", 203),
    HARRALANDER("Harralander", 205),
    RANARR("Ranarr", 207),
    TOADFLAX("Toadflax", 3049),
    IRIT("Irit", 209),
    AVANTOE("Avantoe", 211),
    KWUARM("Kwuarm", 213),
    CADANTINE("Cadantine", 215),
    DWARF_WEED("Dwarf weed", 217),
    LANTADYME("Lantadyme", 2485),
    SNAPDRAGON("Snapdragon", 3051),
    AIR_RUNE("Air rune", 556),
    WATER_RUNE("Water rune", 555),
    LAW_RUNE("Law rune", 563),
    NATURE_RUNE("Nature rune", 561);

    private String name;
    private int	id;
    private Loot(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
}