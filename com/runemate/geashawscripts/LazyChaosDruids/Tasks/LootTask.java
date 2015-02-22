package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.hybrid.region.Players;
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
        //Methods.debug("Executing looting task.");
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
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", loot.getDefinition().getName())) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootLawRune) {
                if (name.equals("Law rune")) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", loot.getDefinition().getName())) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootNatureRune) {
                if (name.equals("Nature rune")) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", loot.getDefinition().getName())) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootGuam) {
                if (id == 199) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", "Herb")) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootMarrentill) {
                if (id == 201) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", "Herb")) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootTarromin) {
                if (id == 203) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", "Herb")) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootHarralander) {
                if (id == 205) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", "Herb")) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootRanarr) {
                if (id == 207) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", "Herb")) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootIrit) {
                if (id == 209) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", "Herb")) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootAvantoe) {
                if (id == 211) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", "Herb")) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootKwuarm) {
                if (id == 213) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", "Herb")) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootCadantine) {
                if (id == 215) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", "Herb")) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootDwarfWeed) {
                if (id == 217) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", "Herb")) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootLantadyme) {
                if (id == 2485) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", "Herb")) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
                    }
                }
            }
            if (LazyChaosDruids.lootSnapdragon) {
                if (id == 3051) {
                    if (loot.distanceTo(Players.getLocal()) < LazyChaosDruids.maxLootTiles) {
                        if (loot.interact("Take", "Herb")) {
                            LazyChaosDruids.status = "Looting";
                            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                            return true;
                        }
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