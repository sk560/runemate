package com.runemate.geashawscripts.LazyChopFletchAlch.Utils;

import com.runemate.game.api.hybrid.entities.Actor;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.script.Execution;

/**
 * Created by Deka on 6-2-2015.
 */
public class Methods {
    /**
     * Helper method used to replace System.out.println(text);
     *
     * @param text The text to send to the console.
     */
    public static void debug(String text) {
        System.out.println(text);
    }

    /**
     * Method to check if player is busy.
     * @param player
     *   The local player.
     */
    public static boolean isBusy(final Actor player) {
        return Constants.player.isMoving() || Constants.player.getAnimationId() != -1;
    }

    /**
     * Presses space bar.
     */
    public static boolean pressSpacebar() {
        com.runemate.geashawscripts.LazyAutoTanner.Constants.STATUS = "Pressing spacebar.";
        if (Keyboard.typeKey(" ")) {
            if (com.runemate.geashawscripts.LazyAutoTanner.Methods.interfaceTextIsVisible(Constants.fletchInterfaceText)) {
                com.runemate.geashawscripts.LazyAutoTanner.Constants.STATUS = "Pressing spacebar.";
                Execution.delayUntil(() -> !com.runemate.geashawscripts.LazyAutoTanner.Methods.interfaceTextIsVisible(Constants.fletchInterfaceText), 0, 500);
            }
            return true;
        }

        return false;
    }

    /**
     * Check if an interface with a specific text is visible.
     */
    public static boolean interfaceTextIsVisible(String text) {
        InterfaceComponent string = Interfaces.newQuery().textContains(text).visible().results().first();
        return string != null && string.isValid() && string.isVisible();
    }
}