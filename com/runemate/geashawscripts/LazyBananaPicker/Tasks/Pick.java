package com.runemate.geashawscripts.LazyBananaPicker.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyBananaPicker.Utils.*;

/**
 * Created by Geashaw on 9-2-2015.
 */
public class Pick extends Task {

    @Override
    public boolean validate() {
        return !Methods.isBusy(Constants.player) && Methods.atKaramja() && !Methods.canPutBananasInBasket() && Methods.canPickBananasFromTree();
    }

    @Override
    public void execute() {
        pickBananasFromTree();
    }

    /**
     * Pick bananas from a tree.
     */
    private boolean pickBananasFromTree() {
        GameObject tree = GameObjects.newQuery().names("Banana Tree").actions("Pick").results().nearest();

        if (tree != null && Constants.KARAMJA_AREA.contains(tree)) {
            if (tree.distanceTo(Constants.player) > 7) {
                Constants.walkToTree = Traversal.getDefaultWeb().getPathBuilder().buildTo(tree);
                if (Constants.walkToTree != null) {
                    Constants.status = "Walking to tree";
                    Constants.walkToTree.step(true);
                } else {
                    Methods.debug("Can't walk to tree...");
                    Constants.status = "Can't walk to tree...";
                }
            } else {
                if (tree.isVisible()) {
                    if (!Methods.isBusy(Constants.player)) {
                        if (Camera.getPitch() < 0.35) {
                            Camera.passivelyTurnTo(0, 0.60, 0.65);
                        } else {
                            Constants.status = "Picking bananas.";
                            return tree.interact("Pick");
                        }
                    }
                } else {
                    Constants.status = "Turning camera to tree";
                    Camera.turnTo(tree);
                }
            }
        } else {
            Constants.status = "Waiting for bananas to grow.";
        }
        return false;
    }
}
