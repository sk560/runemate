package com.runemate.geashawscripts.LazyChopFletchAlch.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.*;

/**
 * Created by Geashaw on 7-2-2015.
 */
public class Fletch extends Task {

    @Override
    public boolean validate() {
        return Methods.gotLogs() && !Methods.isBusy(Constants.player);
    }
    @Override
    public void execute() {
        if (Methods.toolbeltInterfaceIsVisible()) {
            Methods.clickToolOption();
        } else if (Methods.fletchInterfaceIsVisible()) {
            if (Methods.pressSpacebar()) {
                Execution.delayUntil(() -> !Methods.gotLogs());
            }
        } else {
            if (Methods.gotLogs() && !Methods.isBusy(Constants.player)) {
                clickLog();
            }
        }
    }
    /**
     * Click on the log to fletch.
     */
    private boolean clickLog() {
        SpriteItem log = Inventory.getItems(Constants.logs).random();

        if (log != null) {
            if (log.interact("Craft")) {
                Constants.status = "Clicking on the log";
                if (Methods.gotLogs()) {
                    Execution.delayUntil(() -> !Methods.gotLogs(), 1000, 3000);
                }
                return true;
            }
        }
        return false;
    }
}