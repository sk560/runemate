package com.runemate.geashawscripts.LazyAlcoholic.Tasks;

import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.SlotAction;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAlcoholic.Utils.Constants;
import com.runemate.geashawscripts.LazyAlcoholic.Utils.Methods;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class Drink extends Task {

    @Override
    public boolean validate() {
        return !Bank.isOpen() && Inventory.getQuantity(Constants.wine) > 0;
    }

    @Override
    public void execute() {
        drinkWine();
    }

    /**
     * Activate the drink wine from the actionbar.
     */
    private boolean drinkWine() {
        SlotAction action = ActionBar.getFirstAction(Constants.drinkAction);

        if (action != null) {
            Constants.status = "Drinking wine.";
            if (Keyboard.typeKey(action.getSlot().getKeyBind())) {
                Execution.delayUntil(() -> !Methods.isBusy(), 1000, 1500);
            }

            return true;
        }
        return false;
    }
}
