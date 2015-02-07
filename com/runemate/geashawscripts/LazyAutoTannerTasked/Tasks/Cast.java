package com.runemate.geashawscripts.LazyAutoTannerTasked.Tasks;

import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.*;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.SlotAction;
import com.runemate.game.api.script.Execution;
import com.runemate.geashawscripts.LazyAutoTannerTasked.Constants;
import com.runemate.geashawscripts.LazyAutoTannerTasked.Methods;

/**
 * Created by Geashaw on 6-2-2015.
 */
public class Cast extends com.runemate.game.api.script.framework.task.Task {

    @Override
    public boolean validate() {
        return gotHides() && !com.runemate.game.api.hybrid.local.hud.interfaces.Bank.isOpen();
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
        return (Inventory.contains(Constants.DRAGON_HIDE) && Inventory.getQuantity(Constants.DRAGON_HIDE) >= 5);
    }

    /**
     * Check if the inventory contains at least 23 hides.
     */
    private boolean gotAllHides() {
        return (Inventory.contains(Constants.DRAGON_HIDE) && Inventory.getQuantity(Constants.DRAGON_HIDE) >= 23);
    }

    /**
     * Click the dragon hide in the inventory.
     */
    private boolean clickHide() {
        final SpriteItem hide = Inventory.getItems(Constants.DRAGON_HIDE).random();
        if (hide != null) {
            Constants.STATUS = "Interacting Make Leather.";
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
        Constants.STATUS = "Pressing spacebar.";
        if (Keyboard.typeKey(" ")) {
            if (Methods.interfaceTextIsVisible("Tan")) {
                Constants.STATUS = "Tanning hides.";
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
        SlotAction action = ActionBar.getFirstAction(Constants.MAKE_LEATHER_ACTION);

        if (action != null) {
            Constants.STATUS = "Activating Make Leather.";
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