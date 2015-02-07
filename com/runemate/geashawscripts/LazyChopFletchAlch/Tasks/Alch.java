package com.runemate.geashawscripts.LazyChopFletchAlch.Tasks;

import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.SlotAction;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.*;
/**
 * Created by Geashaw on 7-2-2015.
 */
public class Alch extends Task {

    @Override
    public boolean validate() {
        return Inventory.getQuantity("Yew shieldbow (u)") > 0;
    }

    @Override
    public void execute() {
        if (selectSpell()) {
            if (clickShieldBow()) {
                Execution.delayUntil(() -> !Methods.isBusy(Constants.player), 1000);
            }
        }
    }

    /**
     * Select the High Level Alchemy spell from the ability bar.
     */
    private boolean selectSpell() {
        SlotAction action = ActionBar.getFirstAction("High Level Alchemy");

        if (action != null) {
            Constants.status = "Activating High Alchemy.";
            if (Keyboard.typeKey(action.getSlot().getKeyBind())) {
                if (!action.isSelected()) {
                    Execution.delayUntil(() -> action.isSelected(), 0, 500);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Click the yew shieldbow (u) in the inventory.
     */
    private boolean clickShieldBow() {
        final SpriteItem bow = Inventory.getItems("Yew shieldbow (u)").first();
        if (bow != null) {
            Constants.status = "Alching bow.";
            if (bow.interact("Cast")) {
                Execution.delayUntil(() -> !gotShieldBows(), 500, 1000);
            }
            return true;
        }
        return false;
    }

    /**
     * Check if player has any Yew Shieldbows (u).
     */
    private boolean gotShieldBows() {
        return Inventory.getQuantity("Yew shieldbow (u)") > 0;
    }
}