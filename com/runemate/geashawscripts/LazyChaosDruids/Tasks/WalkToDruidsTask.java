package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.location.navigation.web.Web;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.location.navigation.web.vertex_types.objects.BasicObjectVertex;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.Usable;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

/**
 * Created by Geashaw on 18-2-2015.
 */
public class WalkToDruidsTask extends Task {

    @Override
    public boolean validate() {
        return Methods.gotAllSupplies() && !Methods.canFight() && !Methods.atDruids();
    }

    @Override
    public void execute() {
        if (Methods.atFaladorBank()) {
            walkToLadder();
        }
        /*if (Methods.atFaladorBank()) {
            walkToShortcut();
        } else if (atWallShortcut()) {
            if (!atTheOtherSide()) {
                useWallShortcut();
            }
        } else if (canWalkToLadder() && !canWalkToDruids() && !isWalkingToDruids) {
            Methods.debug("Can walk to ladder");
            walkToLadder();
        } else if (canClimbDownLadder()) {
            climbDownLadder();
        } else if (canWalkToDruids()) {
            Methods.debug("Can walk to Druids");
            runToDruids();
        } else {
            runToDruids();
        }*/
    }

    /**
     * Method for walking to the wall shortcut.
     */
    private boolean walkToShortcut() {
        GameObject shortcut = GameObjects.newQuery().names("Crumbling wall").results().nearest();
        return shortcut != null && BresenhamPath.buildTo(shortcut).step(true);
    }

    /**
     * Method for using the wall shortcut
     */
    private boolean useWallShortcut() {
        GameObject shortcut = GameObjects.newQuery().names("Crumbling wall").results().nearest();
        if (shortcut != null) {
            if (shortcut.interact("Climb-over")) {
                Execution.delayUntil(() -> atTheOtherSide(), 2000, 3000);
            }
        }
        return false;
    }

    /**
     * Method for checking whether player went over the wall
     */
    private boolean atTheOtherSide() {
        final Area LEFT_SIDE = new Area.Rectangular(new Coordinate(2933, 3354, 0), new Coordinate(2935, 3356, 0));
        return LEFT_SIDE.contains(Players.getLocal());
    }

    /**
     * Check if player is at Falador teleport area.
     */
    private boolean atWallShortcut() {
        final Area RIGHT_SIDE = new Area.Rectangular(new Coordinate(2936, 3353, 0), new Coordinate(2939, 3358, 0));
        return RIGHT_SIDE.contains(Players.getLocal());
    }

    /**
     * Method for walking to the ladder.
     */
    private boolean walkToLadder() {

        final Web web = Traversal.getDefaultWeb();

        final Coordinate geSidePosition = new Coordinate(2934, 3355, 0);
        final Coordinate edgevilleSidePosition = new Coordinate(2936, 3355, 0);
        final Usable requirement = new Usable() {
            @Override
            public boolean isUsable() {
                return Skill.AGILITY.getBaseLevel() >= 5;
            }
        };

        final BasicObjectVertex geSideObject = new BasicObjectVertex(geSidePosition, "Crumbling wall", "Climb-over", requirement);
        final BasicObjectVertex edgevilleSideObject = new BasicObjectVertex(edgevilleSidePosition, "Crumbling wall", "Climb-over", requirement);

        geSideObject.addBidirectionalEdge(edgevilleSideObject);

        web.getVertexNearestTo(geSidePosition).addBidirectionalEdge(geSideObject);
        web.getVertexNearestTo(edgevilleSidePosition).addBidirectionalEdge(edgevilleSideObject);

        Traversal.getDefaultWeb().addVertices(geSideObject, edgevilleSideObject);

        final Area LADDER_AREA = new Area.Rectangular(new Coordinate(2882, 3395, 0), new Coordinate(2886, 3401, 0));
        final WebPath path = Traversal.getDefaultWeb().getPathBuilder().buildTo(LADDER_AREA.getCenter());

        Methods.debug("Checking path");
        if (path != null) {
            Methods.debug("Path not null");
            return path.step(true);
        }

        return false;
    }

    /**
     * Check if player can walk to ladder.
     */
    private boolean canWalkToLadder() {
        return !atClimbDownLadder() && !Methods.atFaladorBank() && !canClimbDownLadder() && !isWalkingToDruids;
    }

    /**
     * Check if player is at the ladder
     */
    private boolean atClimbDownLadder() {
        final Area LADDER_AREA = new Area.Rectangular(new Coordinate(2882, 3395, 0), new Coordinate(2886, 3401, 0));
        GameObject ladder = GameObjects.newQuery().names("Ladder").actions("Climb-down").within(LADDER_AREA).results().nearest();
        return LADDER_AREA.contains(Players.getLocal()) && ladder != null;
    }

    /**
     * Method to climb down the ladder.
     */
    private boolean climbDownLadder() {
        final Area LADDER_AREA = new Area.Rectangular(new Coordinate(2882, 3395, 0), new Coordinate(2886, 3401, 0));
        GameObject ladder = GameObjects.newQuery().names("Ladder").within(LADDER_AREA).results().nearest();

        if (ladder != null) {
            if (ladder.click()) {
                Execution.delayUntil(() -> !atClimbDownLadder(), 1000, 1500);
                return true;
            }
        }
        return false;
    }

    /**
     * Check if player can climb down ladder.
     */
    private boolean canClimbDownLadder() {
        final Area LADDER_AREA = new Area.Rectangular(new Coordinate(2882, 3395, 0), new Coordinate(2886, 3401, 0));
        GameObject ladder = GameObjects.newQuery().names("Ladder").actions("Climb-down").within(LADDER_AREA).results().nearest();
        return ladder != null && ladder.isVisible();
    }

    /**
     * Check if player can climb up ladder.
     */
    private boolean canClimbUpLadder() {
        final Area LADDER_AREA = new Area.Rectangular(new Coordinate(2882, 3395, 0), new Coordinate(2886, 3401, 0));
        GameObject ladder = GameObjects.newQuery().names("Ladder").actions("Climb-up").within(LADDER_AREA).results().nearest();
        return ladder != null && ladder.isVisible();
    }

    /**
     * Check if player can walk to druids.
     */
    private boolean canWalkToDruids() {
        final Area LADDER_AREA = new Area.Rectangular(new Coordinate(2882, 9796, 0), new Coordinate(2886, 9799, 0));
        GameObject ladder = GameObjects.newQuery().names("Ladder").actions("Climb-up").within(LADDER_AREA).results().nearest();
        return ladder != null && ladder.isVisible();
    }

    /**
     * Walk to Falador west bank method.
     */
    private boolean runToDruids() {
        isWalkingToDruids = true;
        final Area DRUID_AREA = new Area.Rectangular(new Coordinate(2926, 9842, 0), new Coordinate(2940, 9854, 0));
        Path walkToDruids = Traversal.getDefaultWeb().getPathBuilder().buildTo(DRUID_AREA.getCenter());
        return walkToDruids.step(true);
    }

    private boolean isWalkingToDruids = false;
}