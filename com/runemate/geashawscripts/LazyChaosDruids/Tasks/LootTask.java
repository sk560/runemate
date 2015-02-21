package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.LazyChaosDruids;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class LootTask extends Task {

    @Override
    public boolean validate() {
        return Methods.canLoot();
    }

    @Override
    public void execute() {
        lootItems();
    }

    /**
     * Method to loot items.
     */
    private boolean lootItems() {

        GroundItem loot = GroundItems.newQuery().names("Herb", "Law rune", "Air rune", "Nature rune").results().nearest();

        if (loot != null) {
            int id = loot.getDefinition().getUnnotedId();
            String name = loot.getDefinition().getName();

            if (LazyChaosDruids.lootAirRune) {
                if (name.equals("Air rune")) {
                    Methods.debug("Can loot Air runes.");
                    if (loot.interact("Take", loot.getDefinition().getName())) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootLawRune) {
                if (name.equals("Law rune")) {
                    Methods.debug("Can loot Law runes.");
                    if (loot.interact("Take", loot.getDefinition().getName())) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootNatureRune) {
                if (name.equals("Nature rune")) {
                    Methods.debug("Can loot Nature runes.");
                    if (loot.interact("Take", loot.getDefinition().getName())) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootGuam) {
                if (id == 199) {
                    Methods.debug("Can loot Guam.");
                    if (loot.interact("Take", "Herb")) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootMarrentill) {
                if (id == 201) {
                    Methods.debug("Can loot Marrentill.");
                    if (loot.interact("Take", "Herb")) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootTarromin) {
                if (id == 203) {
                    Methods.debug("Can loot Tarromin.");
                    if (loot.interact("Take", "Herb")) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootHarralander) {
                if (id == 205) {
                    Methods.debug("Can loot Harralander.");
                    if (loot.interact("Take", "Herb")) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootRanarr) {
                if (id == 207) {
                    Methods.debug("Can loot Ranarr.");
                    if (loot.interact("Take", "Herb")) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootIrit) {
                if (id == 209) {
                    Methods.debug("Can loot Irit.");
                    if (loot.interact("Take", "Herb")) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootAvantoe) {
                if (id == 211) {
                    Methods.debug("Can loot Avantoe.");
                    if (loot.interact("Take", "Herb")) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootKwuarm) {
                if (id == 213) {
                    Methods.debug("Can loot Kwuarm.");
                    if (loot.interact("Take", "Herb")) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootCadantine) {
                if (id == 215) {
                    Methods.debug("Can loot Cadantine.");
                    if (loot.interact("Take", "Herb")) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootDwarfWeed) {
                if (id == 217) {
                    Methods.debug("Can loot Dwarf weed.");
                    if (loot.interact("Take", "Herb")) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootLantadyme) {
                if (id == 2485) {
                    Methods.debug("Can loot Lantadyme.");
                    if (loot.interact("Take", "Herb")) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }
            if (LazyChaosDruids.lootSnapdragon) {
                if (id == 3051) {
                    Methods.debug("Can loot Snapdragon.");
                    if (loot.interact("Take", "Herb")) {
                        LazyChaosDruids.status = "Looting";
                        Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                        return true;
                    }
                }
            }

            //return id == 207 || name.equals("Air rune") || name.equals("Law rune") || name.equals("Nature rune");
        }
        return false;

        /*if (loot != null) {
            int unnoted = loot.getDefinition().getUnnotedId();
            String name = loot.getDefinition().getName();

            if (unnoted == 207 || name.equals("Air rune") || name.equals("Law rune") || name.equals("Nature rune")) {
                if (loot.interact("Take", loot.getDefinition().getName())) {
                    LazyChaosDruids.status = "Looting";
                    Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                    return true;
                }
            }
        }
        return false;*/
    }
}