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

    int healPercentage = 60;

    @Override
    public boolean validate() {
        return Health.getCurrentPercent() <= healPercentage;
    }

    @Override
    public void execute() {
        SpriteItem food = Inventory.getItems("Trout").last();
        if (food != null) {
            if (food.interact("Eat")) {
                LazyChaosDruids.status = "Eating";
                Execution.delayUntil(() -> Health.getCurrentPercent() >= healPercentage, 1600,2000);
            }
        }
    }
}