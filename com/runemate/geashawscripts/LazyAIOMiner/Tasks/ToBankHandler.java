package com.runemate.geashawscripts.LazyAIOMiner.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAIOMiner.LazyAIOMiner;
import com.runemate.geashawscripts.LazyAIOMiner.Utils.Methods;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class ToBankHandler extends Task {

    @Override
    public boolean validate() {
        return canWalkToBank();
    }

    @Override
    public void execute() {
        if (!Methods.atBank()) {
            walkToBank();
        }
    }

    private void walkToBank() {
        LazyAIOMiner.status = "Walking to bank";
        WebPath path = Traversal.getDefaultWeb().getPathBuilder().buildTo(LazyAIOMiner.bankArea.getArea().getRandomCoordinate());
        if (path != null) {
            path.step(true);
        }
    }

    private boolean canWalkToBank() { return !LazyAIOMiner.powermine && !Methods.atBank() && Inventory.isFull(); }
}
