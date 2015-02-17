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
        return !Inventory.isFull() && Methods.canLoot();
    }

    @Override
    public void execute() {
        Methods.lootItems();
    }
}