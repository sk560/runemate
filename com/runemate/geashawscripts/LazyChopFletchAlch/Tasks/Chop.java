package com.runemate.geashawscripts.LazyChopFletchAlch.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.Constants;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.Methods;

/**
 * Created by Deka on 7-2-2015.
 */
public class Chop extends Task {

    @Override
    public boolean validate() {
        return !Inventory.isFull();
    }

    @Override
    public void execute() {
        GameObject tree = GameObjects.newQuery().names("Yew").actions("Chop down").results().nearest();

        if (tree != null/* && Constants.TREE_AREA.contains(tree)*/) {
            Methods.debug("Tree not null.");
            if (tree.distanceTo(Constants.player) > 7) {
                Constants.walkToTree = Traversal.getDefaultWeb().getPathBuilder().buildTo(tree);
                if (Constants.walkToTree != null) {
                    Constants.walkToTree.step(true);
                } else {
                    Methods.debug("Can't walk to tree...");
                }
            } else {
                Methods.debug("Tree in region.");
                if (tree.isVisible()) {
                    Methods.debug("Tree is visible.");
                    if (!Methods.isBusy(Constants.player)) {
                        if (Camera.getPitch() < 0.35) {
                            Camera.setPitch(0.60, 0.65);
                        } else {
                            Methods.debug("Clicking tree.");
                            Camera.turnTo(tree);
                            tree.interact("Chop down");
                        }
                    }
                } else {
                    Methods.debug("Turning camera to tree");
                    Camera.turnTo(tree);
                }
            }
        }

    }
}
