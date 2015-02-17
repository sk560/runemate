package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

/**
 * Created by Geashaw on 17-2-2015.
 */
public class TeleportTask extends Task {

    @Override
    public boolean validate() {
        return Methods.canTeleport();
    }

    @Override
    public void execute() {
        if(InterfaceWindows.getMagic().isOpen()) {
            InterfaceComponent teleport = Interfaces.getAt(218, 22);
            if (teleport != null) {
                if (teleport.interact("Cast")) {
                    Execution.delayUntil(() -> !Methods.isBusy(), 3000, 5000);
                }
            }
        } else {
            InterfaceWindows.getMagic().open();
        }
    }
}
