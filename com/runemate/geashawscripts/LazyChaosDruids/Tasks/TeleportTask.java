package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

/**
 * Created by Geashaw on 18-2-2015.
 */
public class TeleportTask extends Task {

    @Override
    public boolean validate() {
        return Methods.atDruids() && !Methods.gotFood();
    }

    @Override
    public void execute() {
        //Methods.debug("Executing teleport task.");
        if (Methods.atDruids()) {
            teleport();
        }
    }

    /**
     * Teleport to Falador method.
     */
    private boolean teleport() {
        if(InterfaceWindows.getMagic().isOpen()) {
            InterfaceComponent teleport = Interfaces.getAt(218, 22);
            if (teleport != null) {
                if (Methods.canTeleport()) {
                    if (teleport.interact("Cast")) {
                        Execution.delayUntil(() -> !Methods.canTeleport(), 3000, 5000);
                    }
                }
            }
        } else {
            InterfaceWindows.getMagic().open();
        }
        return false;
    }
}
