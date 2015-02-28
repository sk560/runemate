package com.runemate.geashawscripts.LazyAIOMiner.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.queries.results.LocatableEntityQueryResults;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.Filter;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAIOMiner.LazyAIOMiner;
import com.runemate.geashawscripts.LazyAIOMiner.Utils.Methods;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class MineHandler extends Task {

    @Override
    public boolean validate() {
        if (LazyAIOMiner.powermine) {
            return !Methods.atBank() && Methods.atMiningSpot() && !gotOre();
        } else {
            return !Methods.atBank() && Methods.atMiningSpot() && !Inventory.isFull();
        }
    }

    @Override
    public void execute() {
//Methods.debug("Executing mine handler");

        if (LazyAIOMiner.powermine) {
            if (!Methods.isBusy()) {
                mineOres();
            } else {
                if (!gotOre()) {
                    hoverInventoryItem();
                }
            }
        } else {
            if (!Methods.isBusy()) {
                mineOres();
            } else {
                hoverNextRock();
            }
        }
    }

    private boolean mineOres() {
        LocatableEntityQueryResults<GameObject> rocks = GameObjects.getLoaded(new Filter<GameObject>() {
            @Override
            public boolean accepts(GameObject gameObject) {
                return LazyAIOMiner.oreObjectIds.contains(gameObject.getId()) && LazyAIOMiner.mineArea.contains(gameObject);
            }
        });

        if (rocks != null) {
            //GameObject firstRock = rocks.sortByDistance().limit(1).nearest();
            GameObject firstRock = rocks.nearestTo(Players.getLocal());
            if (firstRock != null) {
                if (firstRock.interact("Mine", firstRock.getDefinition().getName())) {
                    LazyAIOMiner.status = "Mining " + LazyAIOMiner.oreName;
                    Execution.delayUntil(() -> !Methods.isBusy(), 1000, 1500);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hoverInventoryItem() {
        InterfaceComponent item = Interfaces.getAt(218, 91);
        LazyAIOMiner.status = "Hovering inventory";
        return item != null && item.hover();
    }

    private boolean hoverNextRock() {
        LocatableEntityQueryResults<GameObject> rocks = GameObjects.getLoaded(new Filter<GameObject>() {
            @Override
            public boolean accepts(GameObject gameObject) {
                return LazyAIOMiner.oreObjectIds.contains(gameObject.getId()) && LazyAIOMiner.mineArea.contains(gameObject) && !Players.getLocal().isFacing(gameObject);
            }
        });

        GameObject firstRock = rocks.nearestTo(Players.getLocal());
        GameObject nextRock = rocks.sortByDistance().limit(2).nearestTo(Players.getLocal());
        LazyAIOMiner.status = "Hovering next rock";
        return nextRock != null && !nextRock.equals(firstRock) && nextRock.hover();
    }

    private boolean gotOre() {
        return Inventory.getQuantity(LazyAIOMiner.oreName) > 0;
    }
}