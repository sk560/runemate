package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
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
        return !Inventory.isFull() && Methods.canLoot();
    }

    @Override
    public void execute() {
        String[] possibleLoot = new String[]{"Herb", "Law rune", "Air rune"};
        GroundItem loot = GroundItems.newQuery().names(possibleLoot).results().nearest();
        if (loot != null) {
            if (loot.interact("Take", loot.getDefinition().getName())) {
                LazyChaosDruids.status = "Looting";
                Execution.delayUntil(() -> !loot.isVisible(), 1000, 12000);
            }
        }
    }
}