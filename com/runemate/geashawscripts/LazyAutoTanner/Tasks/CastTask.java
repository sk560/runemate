package com.runemate.geashawscripts.LazyAutoTanner.Tasks;

import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.SlotAction;
import com.runemate.game.api.script.Execution;
import com.runemate.geashawscripts.LazyAutoTanner.Constants;
import com.runemate.geashawscripts.LazyAutoTanner.Methods;

/**
 * Created by Geashaw on 6-2-2015.
 */
public class CastTask extends com.runemate.game.api.script.framework.task.Task {

    @Override
    public boolean validate() {
        return gotAllHides() && !Bank.isOpen();
    }

    @Override
    public void execute() {

        if (Methods.isBusy()) {
            Execution.delayUntil(() -> !gotHides(), 1000);
        } else {
            if (gotAllHides()) {
                if (Methods.interfaceTextIsVisible("Tan")) {
                    pressSpacebar();
                } else if (Methods.spellIsSelected()) {
                    clickHide();
                } else {
                    selectSpell();
                }
            }
        }
    }

    /**
     * Check if the inventory contains at least 5 hides.
     */
    private boolean gotHides() {
        return (Inventory.contains(Constants.hide) && Inventory.getQuantity(Constants.hide) >= 5);
    }

    /**
     * Check if the inventory contains at least 23 hides.
     */
    private boolean gotAllHides() {
        return (Inventory.contains(Constants.hide) && Inventory.getQuantity(Constants.hide) >= 23);
    }

    /**
     * Click the dragon hide in the inventory.
     */
    private boolean clickHide() {
        final SpriteItem hide = Inventory.getItems(Constants.hide).random();
        if (hide != null) {
            Constants.status = "Interacting Make Leather.";
            if (hide.interact("Cast")) {
                Execution.delayUntil(() -> Methods.interfaceTextIsVisible("Tan"), 500, 1000);
            }
            return true;
        }
        return false;
    }

    /**
     * Presses space bar.
     */
    private boolean pressSpacebar() {
        Constants.status = "Pressing spacebar.";
        if (Keyboard.typeKey(" ")) {
            if (Methods.interfaceTextIsVisible("Tan")) {
                Constants.status = "Tanning " + Constants.hide;
                Execution.delayUntil(() -> !Methods.interfaceTextIsVisible("Tan"), 0, 500);
            }
            return true;
        }

        return false;
    }

    /**
     * Select the Make Leather spell from the ability bar.
     */
    private boolean selectSpell() {
        SlotAction action = ActionBar.getFirstAction(Constants.makeLeatherAction);

        if (action != null) {
            Constants.status = "Activating Make Leather.";
            if (Keyboard.typeKey(action.getSlot().getKeyBind())) {
                if (!action.isSelected()) {
                    Execution.delayUntil(() -> action.isSelected(), 0, 500);
                }
                return true;
            }
        }
        return false;
    }
}