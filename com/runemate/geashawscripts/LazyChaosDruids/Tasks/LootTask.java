package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.Data.Loot;
import com.runemate.geashawscripts.LazyChaosDruids.LazyChaosDruids;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class LootTask extends Task {

    @Override
    public boolean validate() {
        return Methods.canLoot();
    }

    @Override
    public void execute() {
        lootItems();
    }

    /**
     * Method to loot items.
     */
    private boolean lootItems() {

        /*for(String s:LazyChaosDruids.lootList){
            Loot loot = Loot.valueOf(s.toUpperCase().replace(" ", "_"));
            if(loot != null) {
                int id = loot.getId();
                String name = loot.getName();
                System.out.println(name);
            }
        }*/

        GroundItem loot = GroundItems.newQuery().names("Herb", "Law rune", "Air rune", "Nature rune").results().nearest();

        if (loot != null) {
            int unnoted = loot.getDefinition().getUnnotedId();
            String name = loot.getDefinition().getName();

            if (unnoted == 207 || name.equals("Air rune") || name.equals("Law rune") || name.equals("Nature rune")) {
                if (loot.interact("Take", loot.getDefinition().getName())) {
                    LazyChaosDruids.status = "Looting";
                    Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
                    return true;
                }
            }
        }
        return false;
    }

    /*int id = loot.getDefinition().getUnnotedId();
    if (id != 199 && id != 201 && id != 203 && id != 205) {
        if (loot.interact("Take", loot.getDefinition().getName())) {
            LazyChaosDruids.status = "Looting";
            Execution.delayUntil(() -> !loot.isVisible(), 1500, 2000);
            return true;
        }
    }*/
}