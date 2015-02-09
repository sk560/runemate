package com.runemate.geashawscripts.LazyBananaPicker.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.SlotAction;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyBananaPicker.Utils.*;

/**
 * Created by Geashaw on 9-2-2015.
 */
public class Teleport extends Task {

    @Override
    public boolean validate() {
        return (Methods.atEdgeville() && Methods.gotAllEmptyBaskets()) || (Methods.atKaramja() && Methods.gotFilledBaskets() && Inventory.isFull());
    }

    @Override
    public void execute() {
        if (!Methods.isBusy(Constants.player)) {
            if (Methods.atEdgeville()) {
                gloryTeleportTo("Karamja");
            } else if (Methods.atKaramja()) {
                gloryTeleportTo("Edgeville");
            }
        }
    }

    /**
     * Teleport to one of the four glory locations.
     */
    private boolean gloryTeleportTo(String location) {
        SlotAction action = ActionBar.getFirstAction(Constants.glory);

        if (action != null) {
            Constants.status = "Activating glory.";
            if (action.activate()) {
                Execution.delay(500, 1000);
                if (Methods.gloryInterfaceIsVisible(location)) {
                    InterfaceComponent com = Interfaces.newQuery().textContains(location).results().first();
                    Execution.delay(500, 1000);
                    if (com != null) {
                        Constants.status = "Selecting " + location + " teleport";
                        if (com.click()) {
                            Execution.delay(1500, 3000);
                        }

                        return true;
                    }
                }
            }
        }
        return false;
    }
}
