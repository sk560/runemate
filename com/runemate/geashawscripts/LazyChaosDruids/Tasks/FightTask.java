package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.LazyChaosDruids;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class FightTask extends Task {

    @Override
    public boolean validate() {
        return !Methods.canLoot() && Methods.canFight();
    }

    @Override
    public void execute() {
        Npc druid = Npcs.newQuery().names("Chaos druid").visible().reachable().results().nearestTo(Players.getLocal());

        if (druid != null) {
            if (Players.getLocal().getTarget() == null) {
                if (druid.isVisible()) {
                    if (druid.getTarget() == null) {
                        LazyChaosDruids.status = "Fighting";
                        if (druid.interact("Attack")) {
                            Execution.delayUntil(() -> druid.getTarget() != null, 500, 1500);
                        }
                    }
                } else {
                    Camera.turnTo(druid);
                }
            }
        }
    }
}