package com.runemate.geashawscripts.LazyChopFletchAlch.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.*;

/**
 * Created by Geashaw on 7-2-2015.
 */
public class Fletch extends Task {

    @Override
    public boolean validate() {
        GameObject tree = GameObjects.newQuery().names("Yew").actions("Chop down").results().nearest();
        return Inventory.isFull() || tree == null;
    }
    @Override
    public void execute() {
        SpriteItem log = Inventory.getItems(Constants.logs).random();

        if (Methods.toolbeltInterfaceIsVisible()) {
            Methods.clickToolOption();
        } else if (Methods.fletchInterfaceIsVisible()) {
            Methods.pressSpacebar();
        } else {
            if (log != null) {
                if (log.interact("Craft")) {
                    if (Methods.gotLogs()) {
                        Execution.delayUntil(() -> !Methods.gotLogs());
                    }
                }
            }
        }
    }
}