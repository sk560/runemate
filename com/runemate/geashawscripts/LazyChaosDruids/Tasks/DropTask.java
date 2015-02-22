package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.queries.results.SpriteItemQueryResults;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

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
        //Methods.debug("Executing drop task.");
        final String[] possibleJunk = new String[]{"Bones"};

        if (Inventory.containsAnyOf(possibleJunk)) {
            Inventory.getItems(possibleJunk).first();
        }
    }

}
