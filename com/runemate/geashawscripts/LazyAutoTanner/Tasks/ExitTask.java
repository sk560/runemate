package com.runemate.geashawscripts.LazyAutoTanner.Tasks;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAutoTanner.Constants;
import com.runemate.geashawscripts.LazyAutoTanner.Methods;

/**
 * Created by Geashaw on 6-2-2015.
 */
public class ExitTask extends Task {

    @Override
    public boolean validate() {
        return Bank.isOpen() && Bank.getQuantity(Constants.hide) < 23;
    }

    @Override
    public void execute() {
        Methods.debug("Ran out of material, stopping script.");
        Environment.getScript().stop();
    }
}
