package com.runemate.geashawscripts.LazyChopFletchAlch.Tasks;

import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.Constants;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.Methods;
/**
 * Created by Geashaw on 7-2-2015.
 */
public class Alch extends Task {

    @Override
    public boolean validate() {
        return Methods.gotShieldBows() && !Methods.gotLogs();
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