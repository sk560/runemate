package com.runemate.geashawscripts.LazyChopFletchAlch.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.Constants;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.Methods;

/**
 * Created by Geashaw on 7-2-2015.
 */
public class Fletch extends Task {

    @Override
    public boolean validate() {
        GameObject tree = GameObjects.newQuery().names("Yew").actions("Chop down").results().nearest();
        return !Methods.isBusy(Constants.player) && !Methods.gotShieldBows() && (Inventory.isFull() || tree == null);
    }

    @Override
    public void execute() {
        SpriteItem log = Inventory.getItems(Constants.logs).random();

        if (log != null) {
            if (Methods.fletchInterfaceIsVisible()) {
                Methods.pressSpacebar();
            } else if (Methods.toolbeltInterfaceIsVisible()) {


            } else if (Methods.interfaceTextIsVisible(Constants.toolInterfaceText)) {
                clickToolOption();
            } else {
                if (log.interact("Craft")) {
                    Execution.delayUntil(() -> !Methods.gotLogs(), 5000, 10000);
                }
            }
        }
    }



    /**
     * Click on the tooloption for fletching.
     */
    private boolean clickToolOption() {
        InterfaceComponent knifeImage = Interfaces.getAt(1179, 33, 1);
        if (knifeImage != null) {
            if (knifeImage.interact("Select")) {
                Execution.delayUntil(() -> !knifeImage.isVisible(), 1000);
            }
            return true;
        }
        return false;
    }
}