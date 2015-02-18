package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.script.framework.task.Task;

/**
 * Created by Deka on 18-2-2015.
 */
public class DismissTask extends Task {

    @Override
    public boolean validate() {
        Npc random = Npcs.newQuery().names("").reachable().results().first();
        return random != null && random.isVisible();
    }

    @Override
    public void execute() {

    }
}
