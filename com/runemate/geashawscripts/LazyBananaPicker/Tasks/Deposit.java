package com.runemate.geashawscripts.LazyBananaPicker.Tasks;

import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.SlotAction;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyBananaPicker.Constants;
import com.runemate.geashawscripts.LazyBananaPicker.Methods;

/**
 * Created by Geashaw on 9-2-2015.
 */
public class Deposit extends Task {

    @Override
    public boolean validate() {
        return Methods.canPutBananasInBasket();
    }

    @Override
    public void execute() { putBananasIntoBasket(); }

    /**
     * Put banana into an empty basket using the action bar.
     */
    private boolean putBananasIntoBasket() {
        SlotAction action = ActionBar.getFirstAction("Basket");

        if (action != null) {
            Constants.status = "Putting bananas into basket.";
            if (Keyboard.typeKey(action.getSlot().getKeyBind())) {
                if (Methods.gotBananas()) {
                    Execution.delayUntil(() -> !Methods.gotBananas(), 0, 500);
                    return true;
                }
            }
        }
        return false;
    }
}
