package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.hybrid.util.calculations.Distance;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.LazyChaosDruids;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class LootTask extends Task {

    private Area AREA = new Area.Rectangular(new Coordinate(2927, 9843, 0), new Coordinate(2940, 9854, 0));

    @Override
    public boolean validate() {
        return Methods.lootIsVisible() && !Inventory.isFull();
    }

    @Override
    public void execute() {
        GroundItem loot = GroundItems.newQuery().within(AREA).names("Herb").results().nearest();

        Path lootSpot = Traversal.getDefaultWeb().getPathBuilder().buildTo(loot);

        if (loot != null) {
            Methods.debug("Not null");
            if (loot.isVisible()) {
                if (loot.interact("Take", loot.getDefinition().getName())) {
                    LazyChaosDruids.status = "Looting";
                    Execution.delayUntil(() -> !loot.isVisible(), 1000, 12000);
                }
            } else if (Distance.to(loot) > 4) {
                lootSpot.step(true);
            } else {
                Camera.turnTo(loot);
            }
        }
    }
}