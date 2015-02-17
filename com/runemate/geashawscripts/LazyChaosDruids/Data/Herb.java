package com.runemate.geashawscripts.LazyChaosDruids.Data;

public enum Herb {

    GUAM("Guam", 199),
    MARRENTILL("Marrentill", 201),
    TARROMIN("Tarromin", 203),
    HARRALANDER("Harralander", 205),
    RANARR("Ranarr", 207),
    IRIT("Irit", 209),
    AVANTOE("Avantoe", 211),
    KWUARM("Kwuarm", 213),
    CADANTINE("Cadantine", 215),
    DWARF_WEED("Dwarf weed", 217),
    TORSTOL("Torstol", 219);

    private String name;
    private int	id;
    private Herb(String name, int id) {
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