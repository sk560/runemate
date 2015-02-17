package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.hud.interfaces.Health;
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
        Npc druid = Npcs.newQuery().names("Chaos druid").reachable().results().nearest();
        return druid != null && Health.getCurrentPercent() > 30 && !Methods.lootIsVisible();
    }

    @Override
    public void execute() {
        final Player me = Players.getLocal();
        Npc druid = Npcs.newQuery().names("Chaos druid").reachable().results().nearestTo(me);
        if (druid != null) {
            if (!isInCombat()) {
                LazyChaosDruids.status = "Fighting";
                if (druid.interact("Attack")) {
                    Execution.delayUntil(() -> isInCombat(), 3000, 5000);
                }
            }
        }
    }

    /**
     * Check if player is in combat.
     */
    private boolean isInCombat() {
        return Players.getLocal().getTarget() != null && !Npcs.newQuery().visible().reachable().filter(new Filter<Npc>() {
            @Override
            public boolean accepts(Npc npc) {
                return npc.getTarget() != null && npc.getTarget().equals(Players.getLocal());
            }
        }).results().isEmpty();
    }
}
