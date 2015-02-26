package com.runemate.geashawscripts.LazyAIOMiner.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.queries.results.LocatableEntityQueryResults;
import com.runemate.game.api.hybrid.region.GameObjects;
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
        return !Inventory.isFull() && !Methods.isBusy();
    }

    @Override
    public void execute() {
        mineOres();
    }

    private boolean mineOres() {
        LocatableEntityQueryResults<GameObject> rocks = GameObjects.getLoaded(new Filter<GameObject>() {
            @Override
            public boolean accepts(GameObject gameObject) {
                return LazyAIOMiner.oreObjectIds.contains(gameObject.getId());
            }
        });

        if (rocks != null) {
            GameObject firstRock = rocks.sortByDistance().limit(2).first();
            if (firstRock != null) {
                if (!Methods.isBusy()) {
                    if (firstRock.interact("Mine", firstRock.getDefinition().getName())) {
                        //Execution.delay(2000);
                        Execution.delayUntil(() -> !Methods.isBusy(), 1500, 3000);
                        return true;
                    }
                }
            }

            GameObject nextRock = rocks.sortByDistance().limit(2).last();
            if (nextRock != null && nextRock.isVisible()) {
                nextRock.hover();
            }
        }

        return false;
    }
}