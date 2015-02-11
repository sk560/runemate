package com.runemate.geashawscripts.LazyAlcoholic.Tasks;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAlcoholic.Utils.*;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class Exit extends Task {

    @Override
    public boolean validate() {
        return Bank.isOpen() && Bank.getQuantity(Constants.wine) == 0;
    }

    @Override
    public void execute() {
        Methods.debug("Stopping script, ran out of wine to drink");
        Environment.getScript().stop();
    }
}
