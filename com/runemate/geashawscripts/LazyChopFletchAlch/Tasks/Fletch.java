package com.runemate.geashawscripts.LazyChopFletchAlch.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAutoTanner.Methods;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.Constants;

/**
 * Created by Deka on 7-2-2015.
 */
public class Fletch extends Task {

    @Override
    public boolean validate() {
        GameObject tree = GameObjects.newQuery().names("Yew").actions("Chop down").results().nearest();

        return tree == null || !tree.isVisible() || Inventory.isFull();
    }

    @Override
    public void execute() {
        SpriteItem log = Inventory.getItems("Yew logs").first();

        if (Methods.interfaceTextIsVisible(Constants.fletchInterfaceText)) {
            pressSpacebar();
        } else if (Methods.interfaceTextIsVisible(Constants.toolInterfaceText)) {
            clickToolOption();
        } else {
            if (log != null) {
                if (!Methods.interfaceTextIsVisible(Constants.toolInterfaceText)) {
                    if (!Methods.isBusy()) {
                        if (log.interact("Craft")) {
                            Execution.delayUntil(() -> !gotLogs(), 5000, 10000);
                        }
                    }
                }
            }
        }
    }



    /**
     * Presses space bar.
     */
    public static boolean pressSpacebar() {
        Constants.status = "Pressing spacebar.";
        if (Keyboard.typeKey(" ")) {
            if (Methods.interfaceTextIsVisible(Constants.fletchInterfaceText)) {
                Constants.status = "Pressing spacebar.";
                Execution.delayUntil(() -> Methods.interfaceTextIsVisible(Constants.fletchInterfaceText), 0, 500);
            }
            return true;
        }
        return false;
    }

    /**
     * Click on the tooloption for fletching.
     */
    public static boolean clickToolOption() {
        InterfaceComponent knifeImage = Interfaces.getAt(1179, 33, 1);
        if (knifeImage != null) {
            if (knifeImage.interact("Select")) {
                Execution.delayUntil(() -> !knifeImage.isVisible(), 1000);
            }
            return true;
        }
        return false;
    }

    /**
     * Check if player has logs.
     */
    private boolean gotLogs() {
        return Inventory.getQuantity("Yew logs") > 0;
    }
}