package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

/**
 * Created by Geashaw on 18-2-2015.
 */
public class WalkToDruidsTask extends Task {

    @Override
    public boolean validate() {
        return Methods.gotAllSupplies();
    }

    @Override
    public void execute() {
        //Methods.debug("Executing walking to druids task.");
        walkToDruids();
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
    public static boolean atWallShortcut() {
        final Area RIGHT_SIDE = new Area.Rectangular(new Coordinate(2936, 3354, 0), new Coordinate(2938, 3356, 0));
        return RIGHT_SIDE.contains(Players.getLocal());
    }

    /**
     * Method for walking to the ladder.
     */
    private boolean walkToLadder() {
        final Area LADDER_AREA = new Area.Rectangular(new Coordinate(2882, 3395, 0), new Coordinate(2886, 3401, 0));
        GameObject ladder = GameObjects.newQuery().names("Ladder").within(LADDER_AREA).results().nearest();
        return ladder != null && BresenhamPath.buildTo(ladder).step(true);
    }

    /**
     * Check if player is at the ladder
     */
    private boolean atLadder() {
        final Area LADDER_AREA = new Area.Rectangular(new Coordinate(2882, 3395, 0), new Coordinate(2886, 3401, 0));
        return LADDER_AREA.contains(Players.getLocal());
    }

    /**
     * Method to climb down the ladder.
     */
    private boolean climbDownLadder() {
        final Area LADDER_AREA = new Area.Rectangular(new Coordinate(2882, 3395, 0), new Coordinate(2886, 3401, 0));
        GameObject ladder = GameObjects.newQuery().names("Ladder").within(LADDER_AREA).results().nearest();

        if (ladder != null) {
            if (ladder.click()) {
                Execution.delayUntil(() -> !atLadder(), 1000, 1500);
                return true;
            }
        }
        return false;
    }

    private boolean canClimbDownLadder() {
        final Area LADDER_AREA = new Area.Rectangular(new Coordinate(2882, 3395, 0), new Coordinate(2886, 3401, 0));
        GameObject ladder = GameObjects.newQuery().names("Ladder").actions("Climb-down").within(LADDER_AREA).results().nearest();
        return ladder != null && ladder.isVisible();
    }

    /**
     * Method to finally run the last part to the Chaos druids.
     */
    private boolean runToDruids() {
        Npc npc = Npcs.newQuery().names("Chaos druid").results().nearestTo(Players.getLocal());
        return npc != null && BresenhamPath.buildTo(npc).step(true);
    }

    /**
     * Method for walking to Chaos druids.
     */
    private boolean walkToDruids() {
        if (Methods.atFaladorBank()) {
            walkToShortcut();
        } else if (atWallShortcut() && !atTheOtherSide()) {
            if (!atTheOtherSide()) {
                useWallShortcut();
            }
        } else {
            if (!atLadder()) {
                walkToLadder();
            } else {
                if (canClimbDownLadder()) {
                    climbDownLadder();
                } else {
                    runToDruids();
                }
            }
        }
        return false;
    }
}
