package com.runemate.geashawscripts.LazyChopFletchAlch.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
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
            if (Methods.clickShieldBow()) {
                Execution.delayUntil(() -> !Methods.isBusy(Constants.player), 500, 800);
            }
        } else {
            Methods.selectSpell();
        }
    }




}