package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.Filter;
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
        return !Methods.atFalador() && !Methods.canLoot() && Methods.canFight();
    }

    @Override
    public void execute() {
        //Methods.debug("Executing fighting task.");
        Npc npc = Npcs.newQuery().names("Chaos druid").results().nearestTo(Players.getLocal());

        if (!isInCombat()) {
            if (Players.getLocal().getTarget() == null) {
                if (npc.isVisible()) {
                    if (npc.getTarget() == null) {
                        LazyChaosDruids.status = "Fighting";
                        if (npc.interact("Attack")) {
                            Execution.delayUntil(() -> npc.getTarget() != null, 500, 1500);
                        }
                    }
                } else if (npc.distanceTo(Players.getLocal()) > 6) {
                    BresenhamPath.buildTo(npc).step(true);
                } else {
                    if (Camera.turnTo(npc)) {
                        Execution.delayUntil(() -> npc.isVisible(), 1000, 2500);
                    }
                }
            }
        }
    }

    /**
     * Check if in combat.
     */
    private boolean isInCombat() {
        return Players.getLocal().getTarget() != null || !Npcs.newQuery().reachable().filter(new Filter<Npc>() {
            @Override
            public boolean accepts(Npc npc) {
                return npc.getTarget() != null && npc.getTarget().equals(Players.getLocal());
            }
        }).results().isEmpty();
    }
}