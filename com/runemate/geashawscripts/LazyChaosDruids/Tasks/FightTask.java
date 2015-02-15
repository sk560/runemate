package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.LazyChaosDruids;
import com.runemate.geashawscripts.LazyChaosDruids.Utils.Methods;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class FightTask extends Task {

    @Override
    public boolean validate() {
        Npc druid = Npcs.newQuery().names("Chaos druid").results().nearest();
        return druid != null && (druid.getTarget() == null || druid.getTarget().equals(Players.getLocal()));
    }

    @Override
    public void execute() {
        attachDruid();
    }

    /**
     * Method to attach the Chaos Druid.
     */
    private boolean attachDruid() {
        Npc druid = Npcs.newQuery().names("Chaos druid").reachable().results().nearest();

        if (!Methods.isBusy()) {
            if (druid != null) {
                if (druid.isVisible()) {
                    if (druid.getTarget() == null) {
                        if (druid.interact("Attack")) {
                            LazyChaosDruids.status = "Attacking druid";
                            if (!druid.getTarget().equals(Players.getLocal())) {
                                Execution.delayUntil(() -> druid.getTarget().equals(Players.getLocal()));
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
