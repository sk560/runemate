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
        return LazyAIOMiner.powermine && Inventory.getQuantity(LazyAIOMiner.oreName) > 0;
    }

    @Override
    public void execute() {
//Methods.debug("Executing drop handler");
        dropOres();
    }

    public static final String[] GEMS = {"Uncut sapphire", "Uncut emerald", "Uncut ruby", "Uncut diamiond"};

    private boolean dropOres() {
        SpriteItem gem = Inventory.newQuery().names(GEMS).results().first();
        SpriteItem ore = Inventory.newQuery().names(LazyAIOMiner.oreName).results().first();

        if (Inventory.containsAnyOf(GEMS)) {
            if (gem != null) {
                if (gem.interact("Drop", gem.getDefinition().getName())) {
                    LazyAIOMiner.status = "Dropping " + gem.getDefinition().getName();
                    Execution.delayUntil(() -> gem == null, 1000, 1500);
                    return true;
                }
            }
        }

        if (Inventory.containsAnyOf(LazyAIOMiner.oreName)) {
            if (ore != null) {
                if (ore.interact("Drop", ore.getDefinition().getName())) {
                    LazyAIOMiner.status = "Dropping " + LazyAIOMiner.oreName;
                    Execution.delayUntil(() -> ore == null, 1000, 1500);
                    return true;
                }
            }
        }
        return false;
    }
}
