package com.runemate.geashawscripts.LazyAIOMiner.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAIOMiner.LazyAIOMiner;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class DropHandler extends Task {

    @Override
    public boolean validate() {
        return Inventory.containsAnyOf(LazyAIOMiner.oreName);
    }

    @Override
    public void execute() {
        dropOres();
    }

    private boolean dropOres() {
        SpriteItem ore = Inventory.newQuery().names(LazyAIOMiner.oreName).results().first();

        while (Inventory.containsAnyOf(LazyAIOMiner.oreName)) {
            if (ore != null) {
                if (ore.interact("Drop", ore.getDefinition().getName())) {
                    Execution.delayUntil(() -> ore == null, 500, 800);
                    return true;
                }
            }
        }

        return false;
    }
}
