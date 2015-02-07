package com.runemate.geashawscripts.LazyChopFletchAlch.Utils;

import com.runemate.game.api.hybrid.entities.Actor;

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
}