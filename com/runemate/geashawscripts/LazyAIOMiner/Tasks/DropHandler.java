package com.runemate.geashawscripts.LazyAIOMiner.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows;
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
        return LazyAIOMiner.powermine;
    }

    @Override
    public void execute() {
        dropItems();
    }

    public static final String[] GEMS = {"Uncut sapphire", "Uncut emerald", "Uncut ruby", "Uncut diamond"};

    private boolean dropItems() {
        if (LazyAIOMiner.dropgems) {
            if (Inventory.containsAnyOf(GEMS)) {
                for (SpriteItem i : Inventory.getItems(GEMS)) {
                    LazyAIOMiner.status = "Dropping " + i.getDefinition().getName();
                    if(i.interact("Drop")) {
                        Execution.delayUntil(()->!i.isValid(), 3000);
                    }
                }
            }
        }

        if (Inventory.getQuantity(LazyAIOMiner.oreName) >= LazyAIOMiner.dropCounter) {
            for (SpriteItem i : Inventory.getItems(LazyAIOMiner.oreName)) {
                LazyAIOMiner.status = "Dropping " + i.getDefinition().getName();
                if(i.interact("Drop")) {
                    Execution.delayUntil(()->!i.isValid(), 3000);
                }
            }
        }
        return false;
    }

    private void dropOres() {
        if (InterfaceWindows.getInventory().isOpen()) {
            if (LazyAIOMiner.dropgems) {
                for (SpriteItem i : Inventory.getItems(GEMS)) {
                    LazyAIOMiner.status = "Dropping " + i.getDefinition().getName();
                    if(i.interact("Drop")) {
                        Execution.delayUntil(()->!i.isValid(), 3000);
                    }
                }
            }
            for (SpriteItem i : Inventory.getItems(LazyAIOMiner.oreName)) {
                LazyAIOMiner.status = "Dropping " + i.getDefinition().getName();
                if(i.interact("Drop")) {
                    Execution.delayUntil(()->!i.isValid(), 3000);
                }
            }
        } else {
            InterfaceWindows.getInventory().open();
        }
    }
}
