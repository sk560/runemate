package com.runemate.geashawscripts.LazySuicideThiever.Tasks;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
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

        if (deathInterfaceVisible()) {
            closeDeathInterface();
        } else {
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

    /**
     * Close the interface.
     */
    private boolean closeDeathInterface() {
        InterfaceComponent component = Interfaces.getAt(153, 107);

        if (component != null) {
            return component.click();
        }
        return false;
    }

    /**
     * Check if the death interface is visible.
     */
    private boolean deathInterfaceVisible() {
        InterfaceComponent component = Interfaces.newQuery().textContains("Oh dear, you're dead, but don't despair!").visible().results().first();
        return component != null && component.isValid();
    }
}
