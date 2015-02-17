package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

/**
 * Created by Deka on 17-2-2015.
 */
public class WalkToDruids extends Task {

    @Override
    public boolean validate() {
        return Methods.canWalkToDruids();
    }

    @Override
    public void execute() {
        Npc banker = Npcs.newQuery().names("Banker").results().nearest();

        if (banker != null) {
            BresenhamPath.buildTo(banker).step(true);
        }
    }

}
