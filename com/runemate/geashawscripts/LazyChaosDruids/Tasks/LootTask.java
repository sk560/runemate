package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class LootTask extends Task {

    @Override
    public boolean validate() {
        return !Methods.isWalkingToBank() && !Methods.atFalador() && !Inventory.isFull() && Methods.canLoot() && !Methods.canTeleport();
    }

    @Override
    public void execute() {
        Methods.lootItems();
    }
}