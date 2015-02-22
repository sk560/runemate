package com.runemate.geashawscripts.LazyChaosDruids.Methods;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.hud.interfaces.Health;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.geashawscripts.LazyChaosDruids.Data.Loot;
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
     *
     * @param text  The text to display.
     * @param x     The x position
     * @param y     The y position
     * @param angle The angle in degrees
     * @param g     The graphics.
     */
    public static void drawRotate(String text, double x, double y, int angle, Graphics2D g) {
        g.translate((float) x, (float) y);
        g.rotate(Math.toRadians(angle));
        g.drawString(text, 0, 0);
        g.rotate(-Math.toRadians(angle));
        g.translate(-(float) x, -(float) y);
    }

    /**
     * Method to get the width of a string of text.
     *
     * @param font The font type.
     * @param text The text String.
     * @return Returns an integer for the width.
     */
    public static int getWidth(Font font, String text) {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        return (int) (font.getStringBounds(text, frc).getWidth());
    }

    /**
     * Method to get the height of a string of text.
     *
     * @param font The font type.
     * @param text The text String.
     * @return Returns an integer for the height.
     */
    public static int getHeight(Font font, String text) {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        return (int) (font.getStringBounds(text, frc).getHeight());
    }

    /**
     * Check if the player has food.
     */
    public static boolean gotFood() {
        return Inventory.containsAnyOf(LazyChaosDruids.foodName);
    }

    /**
     * Check if the player has all food.
     */
    public static boolean gotAllFood() {
        return Inventory.getQuantity(LazyChaosDruids.foodName) == LazyChaosDruids.foodAmount;
    }

    /**
     * Check if player can fight.
     */
    public static boolean canFight() {
        Npc druid = Npcs.newQuery().names("Chaos druid").results().nearest();
        return Players.getLocal().getTarget() == null && Health.getCurrentPercent() >= LazyChaosDruids.healPercentage && druid != null;
    }

    /**
     * Check if player can heal.
     */
    public static boolean canHeal() {
        return Health.getCurrentPercent() <= LazyChaosDruids.healPercentage && gotFood();
    }

    /**
     * Check if player can loot.
     */
    private static int note;

    public static boolean canLoot() {
        GroundItem loot = GroundItems.newQuery().names("Herb", "Law rune", "Air rune", "Nature rune").results().nearest();

        if (loot != null) {

            note = loot.getDefinition().getUnnotedId();
            String name = loot.getDefinition().getName();

            if (LazyChaosDruids.lootAirRune && name.equals("Air rune")) {
                Methods.debug("Can loot Air rune.");
                return true;
            }
            if (LazyChaosDruids.lootLawRune && name.equals("Law rune")) {
                Methods.debug("Can loot Law rune.");
                return true;
            }
            if (LazyChaosDruids.lootNatureRune && name.equals("Nature rune")) {
                Methods.debug("Can loot Nature rune.");
                return true;
            }
            if (LazyChaosDruids.lootGuam && note == 199) {
                Methods.debug("Can loot Guam.");
                return true;
            }
            if (LazyChaosDruids.lootMarrentill && note == 201) {
                Methods.debug("Can loot Marrentill.");
                return true;
            }
            if (LazyChaosDruids.lootTarromin && note == 203) {
                Methods.debug("Can loot Tarromin.");
                return true;
            }
            if (LazyChaosDruids.lootHarralander && note == 205) {
                Methods.debug("Can loot Harralander.");
                return true;
            }
            if (LazyChaosDruids.lootRanarr && note == 207) {
                Methods.debug("Can loot Ranarr.");
                return true;
            }
            if (LazyChaosDruids.lootIrit && note == 209) {
                Methods.debug("Can loot Irit.");
                return true;
            }
            if (LazyChaosDruids.lootAvantoe && note == 211) {
                Methods.debug("Can loot Avantoe.");
                return true;
            }
            if (LazyChaosDruids.lootKwuarm && note == 213) {
                Methods.debug("Can loot Kwuarm.");
                return true;
            }
            if (LazyChaosDruids.lootCadantine && note == 215) {
                Methods.debug("Can loot Cadantine.");
                return true;
            }
            if (LazyChaosDruids.lootDwarfWeed && note == 217) {
                Methods.debug("Can loot Dwarf weed.");
                return true;
            }
            if (LazyChaosDruids.lootLantadyme && note == 2485) {
                Methods.debug("Can loot Lantadyme.");
                return true;
            }
            if (LazyChaosDruids.lootSnapdragon && note == 3051) {
                Methods.debug("Can loot Snapdragon.");
                return true;
            }
        }
        return false;
    }

    /**
     * Check if player has Falador teleport runes.
     */
    public static boolean gotTeleportRunes() {
        return Inventory.containsAnyOf("Law rune") && Inventory.containsAnyOf("Water rune") && Inventory.getQuantity("Air rune") >= 3;
    }

    /**
     * Check if player has Falador teleport runes.
     */
    public static boolean gotAllSupplies() {
        return gotTeleportRunes() && gotAllFood();
    }

    /**
     * Check if player has Falador teleport runes.
     */
    public static boolean gotLoot() {
        return Inventory.getQuantity("Herb") >= 1;
    }

    /**
     * Check if player can teleport.
     */
    public static boolean canTeleport() {
        return atDruids() && gotTeleportRunes();
    }

    /**
     * Check if player is at Chaos druids.
     */
    public static boolean atDruids() {
        final Area DRUIDS = new Area.Rectangular(new Coordinate(2926, 9842, 0), new Coordinate(2940, 9854, 0));
        return DRUIDS.contains(Players.getLocal());
    }

    /**
     * Check if player is at Falador teleport area.
     */
    public static boolean atFalador() {
        final Area FALADOR_TELEPORT = new Area.Rectangular(new Coordinate(2941, 3367, 0), new Coordinate(2970, 3384, 0));
        return FALADOR_TELEPORT.contains(Players.getLocal());
    }

    /**
     * Check if player is at Falador teleport area.
     */
    public static boolean atFaladorBank() {
        GameObject box = GameObjects.newQuery().names("Bank Deposit Box").results().nearest();
        return box.distanceTo(Players.getLocal()) <= 5;
    }
}