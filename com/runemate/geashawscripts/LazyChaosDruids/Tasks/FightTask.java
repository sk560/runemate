package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.entities.Player;
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
        return Methods.canFight() && !Methods.canLoot();
    }

    @Override
    public void execute() {
        final Player me = Players.getLocal();
        Npc druid = Npcs.newQuery().names("Chaos druid").reachable().results().nearestTo(me);

        if (Players.getLocal().getTarget() == null) {
            if (druid.getTarget() == null) {
                LazyChaosDruids.status = "Fighting";
                if (druid.interact("Attack")) {
                    Execution.delayUntil(() -> Players.getLocal().getTarget() != null, 1500, 2000);
                }
            }
        }
    }
}
