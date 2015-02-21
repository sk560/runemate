package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

/**
 * Created by Geashaw on 18-2-2015.
 */
public class DismissTask extends Task {

    final String[] dudes = new String[]{"Genie", "Flippa", "Leo", "Evil Bob", "Mysterious Old Man", "Freaky Forester", "Sergeant Damien"};

    @Override
    public boolean validate() {
        Npc random = Npcs.newQuery().names(dudes).reachable().results().first();
        return random != null && random.isVisible();
    }

    @Override
    public void execute() {
        Npc random = Npcs.newQuery().names(dudes).reachable().results().first();

        if (random != null) {
            if (random.interact("Dismiss")) {
                Execution.delayUntil(() -> !random.isVisible(), 1000, 1500);
            }
        }
    }
}
