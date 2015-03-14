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
public class ToMineHandler extends Task {

    @Override
    public boolean validate() {
        return canWalkToMine();
    }

    @Override
    public void execute() {
        if (!Methods.atMine()) {
            walkToMine();
        }
    }

    private void walkToMine() {
        LazyAIOMiner.status = "Walking to mining spot";
        WebPath path = Traversal.getDefaultWeb().getPathBuilder().buildTo(LazyAIOMiner.mineArea.getArea().getRandomCoordinate());
        if (path != null) {
            path.step(true);
        }
    }

    private boolean canWalkToMine() { return !LazyAIOMiner.powermine && !Methods.atMine() && Inventory.isEmpty(); }
}