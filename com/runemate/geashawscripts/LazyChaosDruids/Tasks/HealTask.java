package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Health;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.LazyChaosDruids;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class HealTask extends Task {

    @Override
    public boolean validate() {
        return Health.getCurrentPercent() <= 30;
    }

    @Override
    public void execute() {
        /*SpriteItem food = Inventory.getItems("Salmon").last();
        if (food != null) {
            if (food.interact("Eat")) {
                LazyChaosDruids.status = "Eating";
                Execution.delayUntil(() -> Health.getCurrentPercent() > 30);
            }
        }*/

        if (Inventory.contains(LazyChaosDruids.food.getName())) {
            final int startHealth = Health.getCurrent();
            SpriteItem i = Inventory.getItems(LazyChaosDruids.food.getName()).random();
            if (i != null) {
                if (i.interact("Eat")) {
                    Execution.delayUntil(() -> Health.getCurrent() != startHealth, 1600,2000);
                }
            }
        }
    }
}
