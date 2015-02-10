package com.runemate.geashawscripts.LazyChopFletchAlch.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.*;

/**
 * Created by Geashaw on 7-2-2015.
 */
public class Chop extends Task {
    @Override
    public boolean validate() {
        return !Inventory.isFull() && !Methods.isBusy(Constants.player) && !Methods.gotShieldBows();
    }

    @Override
    public void execute() {
        Methods.debug("Executing the Chop Task.");
        GameObject tree = GameObjects.newQuery().names("Yew").actions("Chop down").results().nearest();

        if (tree != null) {
            if (tree.isVisible()) {
                if (!Methods.isBusy(Constants.player)) {
                    Constants.status = "Chopping down tree.";
                    tree.interact("Chop down");
                }
            } else {
                Camera.turnTo(tree);
            }
        }
    }
}
