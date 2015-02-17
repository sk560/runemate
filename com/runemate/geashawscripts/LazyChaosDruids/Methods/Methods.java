package com.runemate.geashawscripts.LazyChaosDruids.Methods;

import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.hud.interfaces.Health;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.geashawscripts.LazyChaosDruids.LazyChaosDruids;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
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

    /**
     * Method to rotate text
     * @param text
     *   The text to display.
     * @param x
     *   The x position
     * @param y
     *   The y position
     * @param angle
     *   The angle in degrees
     * @param g
     *   The graphics.
     */
    public static void drawRotate(String text, double x, double y, int angle, Graphics2D g) {
        g.translate((float)x,(float)y);
        g.rotate(Math.toRadians(angle));
        g.drawString(text,0,0);
        g.rotate(-Math.toRadians(angle));
        g.translate(-(float)x,-(float)y);
    }

    /**
     * Method to get the width of a string of text.
     * @param font
     *   The font type.
     * @param text
     *   The text String.
     * @return
     *   Returns an integer for the width.
     */
    public static int getWidth(Font font, String text){
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        return (int)(font.getStringBounds(text, frc).getWidth());
    }

    /**
     * Method to get the height of a string of text.
     * @param font
     *   The font type.
     * @param text
     *   The text String.
     * @return
     *   Returns an integer for the height.
     */
    public static int getHeight(Font font, String text){
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        return (int)(font.getStringBounds(text, frc).getHeight());
    }

    /**
     * Check if player can loot.
     */
    public static boolean canLoot() {
        final String[] possibleLoot = new String[]{"Herb", "Law rune", "Air rune", "Nature rune"};
        GroundItem loot = GroundItems.newQuery().names(possibleLoot).results().nearest();
        if (loot != null) {
            int id = loot.getDefinition().getUnnotedId();

            if (id != 199 && id != 201 && id != 203 && id != 205) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the player has food.
     */
    public static boolean gotFood() {
        SpriteItem food = Inventory.getItems(LazyChaosDruids.food.getName()).random();
        return food != null;
    }

    /**
     * Check if player can fight.
     */
    public static boolean canFight() {
        Npc druid = Npcs.newQuery().names("Chaos druid").results().nearest();
        return Players.getLocal().getTarget() == null && Health.getCurrentPercent() >= 60 && druid != null;
    }

    /**
     * Check if player can heal.
     */
    public static boolean canHeal() {
        return Health.getCurrentPercent() <= 60;
    }

    /**
     * Check if player can teleport.
     */
    public static boolean canTeleport() {
        return hasTeleportRunes() && (Inventory.isFull() || Health.getCurrentPercent() <= 30 || !gotFood() && !atFalador());
    }

    /**
     * Check if player has teleport runes.
     */
    public static boolean hasTeleportRunes() {
        return Inventory.getQuantity("Law rune") >= 1 && Inventory.getQuantity("Water rune") >= 1 && Inventory.getQuantity("Air rune") >= 3;
    }

    /**
     * Check if player can walk to bank.
     */
    public static boolean canWalkToBank() {
        return !gotFood() && atFalador();
    }

    /**
     * Check if the player is walking to the bank.
     */
    public static boolean isWalkingToBank() {
        return LazyChaosDruids.isWalkingToBank;
    }

    /**
     * Check if player can walk to bank.
     */
    public static boolean canWalkToDruids() {
        return false; //TODO: Create the check.
    }

    /**
     * Method to loot items.
     */
    public static boolean lootItems() {
        final String[] possibleLoot = new String[]{"Herb", "Law rune", "Air rune", "Nature rune"};
        GroundItem loot = GroundItems.newQuery().names(possibleLoot).results().nearest();
        if (loot != null) {
            int id = loot.getDefinition().getUnnotedId();
            if (id != 199 && id != 201 && id != 203 && id != 205) {
                if (loot.interact("Take", loot.getDefinition().getName())) {
                    LazyChaosDruids.status = "Looting";
                    Execution.delayUntil(() -> !loot.isVisible(), 1000, 1200);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if player is at Falador.
     */
    public static boolean atFalador() {
        final Area FALADOR = new Area.Rectangular(new Coordinate(2961, 3376, 0), new Coordinate(2970, 3386, 0));
        return FALADOR.contains(Players.getLocal());
    }
}