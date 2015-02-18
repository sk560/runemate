package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

/**
 * Created by Deka on 17-2-2015.
 */
public class WalkToDruids extends Task {

    private Path walkToShorcut;

    @Override
    public boolean validate() {
        return Methods.canWalkToDruids();
    }

    @Override
    public void execute() {
        GameObject wallShortcut = GameObjects.newQuery().names("Crumbling wall").results().first();
        walkToShorcut = Traversal.getDefaultWeb().getPathBuilder().buildTo(wallShortcut);

        if (wallShortcut != null) {
            if (!wallShortcut.isVisible()) {
                Methods.debug("Not visible.");
                walkToShorcut.step(true);
            } else {
                wallShortcut.interact("Climb-over");
            }
        }
    }
}
