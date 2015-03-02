package com.runemate.geashawscripts.LazyAIOMiner.Utils;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.geashawscripts.LazyAIOMiner.LazyAIOMiner;

import java.text.NumberFormat;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class Methods {

    /**
     * Method to return hourly experience.
     *
     * @param amount  The amount of experience.
     * @param elapsed The elapsed time.
     * @return Returns experience per hour.
     */
    public static int getHourly(final int amount, final long elapsed) {
        return (int) (amount * 3600000.0D / elapsed);
    }

    /**
     * Method to format thousands decimal.
     *
     * @param i The integer to format.
     * @return Returns the integer as formatted number.
     */
    public static String formatNumber(int i) {
        return NumberFormat.getIntegerInstance().format(i);
    }

    /**
     * Helper method used to replace System.out.println(text);
     *
     * @param text The text to send to the console.
     */
    public static void debug(String text) {
        System.out.println(text);
    }

    /**
     * Checks if the player is busy.
     */
    public static boolean isBusy() {
        return Players.getLocal().isMoving() || Players.getLocal().getAnimationId() != -1;
    }

    public static boolean atPreferedTile() {
        final Coordinate tile = LazyAIOMiner.preferedTile;
        return Players.getLocal().getPosition().equals(tile);
    }

    public static boolean walkToPreferedTile() {
        final Coordinate tile = LazyAIOMiner.preferedTile;
        if (!Players.getLocal().getPosition().equals(tile)) {
            final WebPath path = Traversal.getDefaultWeb().getPathBuilder().buildTo(tile);
            LazyAIOMiner.status = "Walking to spot";
            return path != null && path.step(true);
        }
        return false;
    }

    public static boolean atBank() {
        return LazyAIOMiner.bankArea.contains(Players.getLocal());
    }

    public static boolean atMiningSpot() {
        return LazyAIOMiner.mineArea.contains(Players.getLocal());
    }

    public static boolean activateRun() {
        return false;
    }

    public static boolean walkToArea(final Area area) {
        final Coordinate destination = area.getRandomCoordinate();
        final WebPath path = Traversal.getDefaultWeb().getPathBuilder().buildTo(destination);
        return path != null && path.step(true);
    }
}