package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.framework.task.Task;

/**
 * Created by Deka on 18-2-2015.
 */
public class DropTask extends Task {
    @Override
    public boolean validate() {
        final String[] possibleJunk = new String[]{"Bones"};
        return Inventory.containsAnyOf(possibleJunk);
    }

    @Override
    public void execute() {
        final String[] possibleJunk = new String[]{"Bones"};

        if (Inventory.containsAnyOf(possibleJunk)) {
            Inventory.getItems(possibleJunk).first();
        }
    }
}
