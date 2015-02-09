package com.runemate.geashawscripts.LazyBananaPicker.Tasks;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyBananaPicker.Utils.*;

/**
 * Created by Geashaw on 9-2-2015.
 */
public class Exit extends Task {

    @Override
    public boolean validate() {
        return Methods.bankIsOpen() && !Methods.gotGloriesInBank() && !Methods.gotEmptyBasketsInBank();
    }

    @Override
    public void execute() {
        Environment.getScript().stop();
    }
}
