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
        return Methods.gotShieldBows() && Inventory.getQuantity(Constants.logs) == 0;
    }

    @Override
    public void execute() {
        if (Methods.spellIsSelected()) {
            if (clickShieldBow()) {
                Execution.delayUntil(() -> !Methods.isBusy(Constants.player), 500, 800);
            }
        } else {
            selectSpell();
        }
    }

    /**
     * Select the High Level Alchemy spell from the ability bar.
     */
    private boolean selectSpell() {
        SlotAction action = ActionBar.getFirstAction(Constants.spell);
        if (action != null) {
            Constants.status = "Activating " + Constants.spell + ".";
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
        final SpriteItem bow = Inventory.getItems(Constants.shieldBow).last();
        if (bow != null) {
            if (bow.interact("Cast")) {
                Constants.status = "Alching " + Constants.shieldBow + ".";
                Execution.delayUntil(() -> !Methods.gotShieldBows(), 500, 1000);
                return true;
            }
        }
        return false;
    }


}