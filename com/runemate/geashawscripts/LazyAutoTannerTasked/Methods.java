package com.runemate.geashawscripts.LazyAutoTannerTasked;

import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.SlotAction;
import com.runemate.game.api.script.Execution;

import java.text.NumberFormat;

/**
 * Created by Deka on 6-2-2015.
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
    private static void debug(String text) {
        System.out.println(text);
    }

    /**
     * Check if an interface with a specific text is visible.
     */

    public static boolean interfaceTextIsVisible(String text) {
        InterfaceComponent x = Interfaces.newQuery().texts(text).visible().results().first();
        return x != null && x.isValid() && x.isVisible();
    }

    /**
     * @return Whether or not the spell is selected.
     */
    public static boolean spellIsSelected() {
        SlotAction action = ActionBar.getFirstAction(Constants.MAKE_LEATHER_ACTION);

        if (action != null) {
            if (action.isSelected()) {

                return true;
            } else {
                Execution.delayUntil(() -> action.isSelected(), 0, 500);
            }
        }

        return false;
    }

    /**
     * Checks if the player is busy.
     */
    public static boolean isBusy() {
        return Constants.player.isMoving() || Constants.player.getAnimationId() != -1;
    }
}
