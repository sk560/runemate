package com.runemate.geashawscripts.LazyChopFletchAlch.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.Constants;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.Methods;

/**
 * Created by Geashaw on 7-2-2015.
 */
public class Chop extends Task {
    GameObject tree = GameObjects.newQuery().names("Yew").actions("Chop down").results().nearest();

    @Override
    public boolean validate() {
        return !Inventory.isFull() && !Methods.isBusy(Constants.player);
    }

    @Override
    public void execute() {
        if (tree != null) {
            if (tree.isVisible()) {
                if (!Methods.isBusy(Constants.player)) {
                    Constants.status = "Chopping down tree.";
                    tree.interact("Chop down");
                }
            }
        }
    }
}
