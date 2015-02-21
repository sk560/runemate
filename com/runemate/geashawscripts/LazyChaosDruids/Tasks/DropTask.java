package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.queries.results.SpriteItemQueryResults;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

/**
 * Created by Deka on 18-2-2015.
 */
public class DropTask extends Task {
    @Override
    public boolean validate() {
        final String[] possibleJunk = new String[]{"Bones"};
        return Inventory.containsAnyOf(possibleJunk);

        /*SpriteItemQueryResults inv = Inventory.getItems();
        String[] keep = new String[]{"Herb", "Law rune", "Air rune", "Nature rune", "Trout", "Salmon", "Tuna", "Lobster"};

        for (int i = 0; i < inv.size(); i++) {
            if (inv.get(i) != null) {
                if (!inv.get(i).getDefinition().getName().equals(keep)) {
                    return true;
                }
            }
        }
        return false;*/
    }

    @Override
    public void execute() {
        final String[] possibleJunk = new String[]{"Bones"};

        if (Inventory.containsAnyOf(possibleJunk)) {
            Inventory.getItems(possibleJunk).first();
        }

        /*SpriteItemQueryResults inv = Inventory.getItems();
        String[] keep = new String[]{"Herb", "Law rune", "Air rune", "Nature rune", "Trout", "Salmon", "Tuna", "Lobster"};

        for (int i = 0; i < inv.size(); i++){
            if (inv.get(i) != null) {
                if (!inv.get(i).getDefinition().getName().equals(keep)) {
                    if (inv.get(i).interact("Drop")) {
                        Execution.delay(1000, 1500);
                    }
                }
            }
        }*/
    }

}
