package com.runemate.geashawscripts.LazyAIOMiner.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAIOMiner.LazyAIOMiner;
import com.runemate.geashawscripts.LazyAIOMiner.Utils.Methods;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class DropHandler extends Task {

    @Override
    public boolean validate() {
        return !Methods.atBank() && Methods.canDropOre();
    }

    @Override
    public void execute() {
        dropOre();
        dropGems(); }

    private boolean dropOre() {
        if (Inventory.getQuantity(LazyAIOMiner.oreName) >= 1) {
            SpriteItem ore = Inventory.newQuery().names(LazyAIOMiner.oreName).results().first();

            if (ore != null) {
                if (ore.interact("Drop", ore.getDefinition().getName())) {
                    LazyAIOMiner.status = "Dropping " + ore.getDefinition().getName();
                    Execution.delayUntil(() -> !Methods.canDropOre());
                    return true;
                }
            }
        }
        return false;
    }
    private boolean dropGems() {
        if (LazyAIOMiner.dropgems && Inventory.containsAnyOf(LazyAIOMiner.GEMS)) {
            SpriteItem gem = Inventory.newQuery().names(LazyAIOMiner.GEMS).results().first();
            if (gem != null) {
                if (gem.interact("Drop", gem.getDefinition().getName())) {
                    LazyAIOMiner.status = "Dropping " + gem.getDefinition().getName();
                    Execution.delayUntil(() -> !Methods.canDropGems());
                    return true;
                }
            }
        }
        return false;
    }
}
