package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.hybrid.util.calculations.Distance;
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
        return Methods.lootIsVisible() && !Inventory.isFull();
    }

    @Override
    public void execute() {
        String[] possibleLoot = new String[]{"Herb", "Law rune", "Air rune"};
        GroundItem loot = GroundItems.newQuery().names(possibleLoot).reachable().results().first();
        Path lootSpot = Traversal.getDefaultWeb().getPathBuilder().buildTo(loot);

        if (loot != null) {
            if (loot.isVisible()) {
                if (loot.interact("Take")) {
                    LazyChaosDruids.status = "Looting";
                    Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                } else if (Distance.to(loot) > 2) {
                    lootSpot.step(true);
                } else {
                    Camera.turnTo(loot);
                }
            }
        }
    }
}