package com.runemate.geashawscripts.LazyChopFletchAlch.Utils;

import com.runemate.game.api.hybrid.entities.Actor;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.SlotAction;
import com.runemate.game.api.script.Execution;

import java.text.NumberFormat;

/**
 * Created by Geashaw on 6-2-2015.
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
     *
     * @param player The local player.
     */
    public static boolean isBusy(final Actor player) {
        return Constants.player.isMoving() || Constants.player.getAnimationId() != -1;
    }
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
     * Presses space bar.
     */
    public static boolean pressSpacebar() {
        Constants.status = "Pressing spacebar.";
        if (Keyboard.typeKey(" ")) {
            Constants.status = "Pressing spacebar.";
            return true;
        }
        return false;
    }
    /**
     * Check if an interface with a specific text is visible.
     */
    public static boolean interfaceTextIsVisible(String text) {
        InterfaceComponent x = Interfaces.newQuery().textContains(text).results().first();
        return x != null && x.isValid() && x.isVisible();
    }
    /**
     * Check if the fletching interface is visible.
     */
    public static boolean fletchInterfaceIsVisible() {
        InterfaceComponent fletch = Interfaces.getAt(1370, 40, 0);
        if (fletch != null) {
            if (fletch.isValid() && fletch.isVisible()) {
                debug("Fletch interface visible");
                return true;
            }
        }
        return false;
    }
    /**
     * Check if the toolbelt interface is visible.
     */
    public static boolean toolbeltInterfaceIsVisible() {
        InterfaceComponent knife = Interfaces.getAt(1179, 33);
        return knife != null && knife.isValid() && knife.isVisible();
    }
    /**
     * Check if player has logs.
     */
    public static boolean gotLogs() {
        return Inventory.getQuantity(Constants.logs) > 0;
    }
    /**
     * Check if player has any Yew Shieldbows (u).
     */
    public static boolean gotShieldBows() {
        return Inventory.getQuantity(Constants.shieldBow) > 0;
    }
    /**
     * @return Whether or not the spell is selected.
     */
    public static boolean spellIsSelected() {
        SlotAction action = ActionBar.getFirstAction(Constants.spell);
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
     * Click on the tooloption for fletching.
     */
    public static boolean clickToolOption() {
        InterfaceComponent knifeImage = Interfaces.getAt(1179, 33);
        if (knifeImage != null && knifeImage.isVisible()) {
            if (knifeImage.click()) {
                Execution.delayUntil(() -> !knifeImage.isVisible(), 1000);
            }
            return true;
        }
        return false;
    }
    /**
     * Select the High Level Alchemy spell from the ability bar.
     */
    public static boolean selectSpell() {
        SlotAction action = ActionBar.getFirstAction(Constants.spell);
        if (action != null) {
            Constants.status = "Activating " + Constants.spell + ".";
            if (Keyboard.typeKey(action.getSlot().getKeyBind())) {
                if (!action.isSelected()) {
                    Execution.delayUntil(() -> action.isSelected(), 500, 800);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Click the yew shieldbow (u) in the inventory.
     */
    public static boolean clickShieldBow() {
        final SpriteItem bow = Inventory.getItems(Constants.shieldBow).last();
        if (bow != null) {
            if (bow.interact("Cast")) {
                Constants.status = "Alching " + Constants.shieldBow + ".";
                Execution.delayUntil(() -> !Methods.gotShieldBows(), 500, 1000);
                return true;
            }
        }
        return false;
    }
}