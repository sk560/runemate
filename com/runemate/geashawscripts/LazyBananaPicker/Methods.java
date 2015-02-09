package com.runemate.geashawscripts.LazyBananaPicker;

import com.runemate.game.api.hybrid.entities.Actor;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;

import java.text.NumberFormat;

/**
 * Created by Geashaw on 6-2-2015.
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
     * Checks if player is busy doing stuff.
     */
    public static boolean isBusy(final Actor player) {
        return player.isMoving() || player.getAnimationId() != -1;
    }

    /**
     * Check if the player is at Karamja or not.
     */
    public static boolean atKaramja() {
        return Constants.KARAMJA_AREA.contains(Constants.player);
    }

    /**
     * Check if whether the player is at Edgeville or not.
     */
    public static boolean atEdgeville() {
        return Constants.EDGEVILLE_AREA.contains(Constants.player);
    }

    /**
     * Check if the player has at least 5 bananas in inventory.
     */
    public static boolean gotBananas() {
        return (Inventory.contains(Constants.banana) && Inventory.getQuantity(Constants.banana) >= 5);
    }

    /**
     * Check if the the player has at least 23 filled baskets.
     */
    public static boolean gotFilledBaskets() {
        return (Inventory.contains(Constants.filledBasket) && Inventory.getQuantity(Constants.filledBasket) >= 23);
    }

    /**
     * Check if the player has at least 23 empty baskets
     */
    public static boolean gotAllEmptyBaskets() {
        return (Inventory.contains(Constants.basket) && Inventory.getQuantity(Constants.basket) >= 23);
    }

    /**
     * Check if the player has at least one empty basket
     */
    public static boolean gotEmptyBaskets() {
        return (Inventory.contains(Constants.basket) && Inventory.getQuantity(Constants.basket) >= 1);
    }

    /**
     * Check if the player can put bananas into an empty basket.
     */
    public static boolean canPutBananasInBasket() {
        return Methods.gotBananas() && Methods.gotEmptyBaskets();
    }

    /**
     * Check if the player can pick bananas from a tree.
     */
    public static boolean canPickBananasFromTree() {
        return !Inventory.isFull() && !Methods.gotBananas();
    }

    /**
     * Check if the teleport interface is visible.
     */
    public static boolean gloryInterfaceIsVisible(String location) {
        InterfaceComponent component = Interfaces.newQuery().textContains(location).visible().results().first();
        return (component != null && component.isValid());
    }

    /**
     * Check if the bank is open.
     */
    public static boolean bankIsOpen() {
        return Bank.isOpen();
    }

    /**
     * Check if the player has glories in bank.
     */
    public static boolean gotGloriesInBank() {
        return false;
    }

    /**
     * Check if the player has empty baskets in bank.
     */
    public static boolean gotEmptyBasketsInBank() {
        return false;
    }
}
