package com.runemate.geashawscripts.LazyChaosDruids.Data;

public enum Food {

    ANCHOVIES("Anchovies", 319, 1),
    CRAYFISH("Crayfish", 13433, 1),
    SHRIMPS("Shrimps", 315, 3),
    SARDINE("Sardine", 325, 4),
    HERRING("Herring", 347, 5),
    SWEETCORN("Sweetcorn", 5986, 5),
    TROUT("Trout", 333, 7),
    SALMON("Salmon", 329, 9),
    TUNA("Tuna", 361, 10),
    LOBSTER("Lobster", 379, 12),
    BASS("Bass", 365, 13),
    SWORDFISH("Swordfish", 373, 14),
    MONKFISH("Monkfish", 7946, 16),
    SHARK("Shark", 385, 20),
    SUMMER_PIE("Summer pie", 7218, 22),
    TUNA_POTATO("Tuna potato", 7060, 22),
    ROCKTAIL("Rocktail", 15272, 23),
    CAKE("Cake", 1891, 4),
    CAKE_2_3("2/3 cake", 1893, 4),
    SLICE_OF_CAKE("Slice of cake", 1895, 4),
    TRIANGLE_SANDWICH("Triangle sandwich", 6962, 6);

    private String name;
    private int	id;
    private int	heal;
    private Food(String name, int id, int heal) {
        this.name = name;
        this.id = id;
        this.heal = heal;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public int getHeal() {
        return heal;
    }
}