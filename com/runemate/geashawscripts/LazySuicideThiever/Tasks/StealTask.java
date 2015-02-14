package com.runemate.geashawscripts.LazySuicideThiever.Tasks;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazySuicideThiever.LazySuicideThiever;
import com.runemate.geashawscripts.LazySuicideThiever.Utils.Methods;

/**
 * Created by Geashaw on 14-2-2015.
 */
public class StealTask extends Task {

    @Override
    public boolean validate() {
        Npc victim = Npcs.newQuery().names("Man", "Woman").results().nearest();
        return victim != null && victim.isValid();
    }

    @Override
    public void execute() {
        Npc victim = Npcs.newQuery().names("Man", "Woman").results().nearest();
        Path walkToVictim = Traversal.getDefaultWeb().getPathBuilder().buildTo(victim);

        if (victim.isVisible()) {
            if (!Methods.PlayerIsStunned()) {
                if (victim.interact("Pickpocket")) {
                    LazySuicideThiever.status = "Pickpocketing";
                    Execution.delay(500, 1000);
                }
            } else {
                LazySuicideThiever.status = "Being stunned";
                Execution.delayUntil(() -> !Methods.PlayerIsStunned());
            }
        } else {
            if (walkToVictim != null) {
                walkToVictim.step(true);
            }
        }
    }
}
