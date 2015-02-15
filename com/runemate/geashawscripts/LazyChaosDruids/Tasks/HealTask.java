package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.status.CombatGauge;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class HealTask extends Task {

    @Override
    public boolean validate() {
        final CombatGauge health = Players.getLocal().getHealthGauge();
        return health != null  && health.getPercent() <= 60;
    }

    @Override
    public void execute() {
        heal();
    }

    /**
     * Method for healing.
     */
    private boolean heal() {
        SpriteItem food = Inventory.getItems("Salmon").last();
        final CombatGauge health = Players.getLocal().getHealthGauge();

        if (food != null) {
            if (food.interact("Eat")) {
                Execution.delayUntil(() -> health.getPercent() >= 70);
            }
        }

        return false;
    }
}
