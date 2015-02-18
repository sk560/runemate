package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.LazyChaosDruids;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

/**
 * Created by Deka on 17-2-2015.
 */
public class WalkToBank extends Task {

    @Override
    public boolean validate() {
        return Methods.canWalkToBank() && !Methods.canWalkToDruids();
    }

    @Override
    public void execute() {
        final Area FALADOR_AREA = new Area.Rectangular(new Coordinate(2942, 3366, 0), new Coordinate(2947, 3369, 0));
        GameObject bank = GameObjects.newQuery().names("Bank booth").within(FALADOR_AREA).results().nearest();

        if (!bank.isVisible()) {
            Methods.debug("Bank not visible");
            LazyChaosDruids.isWalkingToBank = true;
            BresenhamPath.buildTo(new Coordinate(2946, 3368, 0)).step(true);
        } else {
            Methods.debug("Bank not visible");
            Bank.open();
        }
    }
}