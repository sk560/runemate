package com.runemate.geashawscripts.LazyChaosDruids.Methods;

import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.hud.interfaces.Health;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
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
     * Check if player can fight.
     */
    public static boolean canFight() {
        Npc druid = Npcs.newQuery().names("Chaos druid").results().nearest();
        return !canLoot() && Players.getLocal().getTarget() == null && Health.getCurrentPercent() >= LazyChaosDruids.healPercentage && druid != null;
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
    public static boolean canLoot() {

        /*for(String s:LazyChaosDruids.lootList){
            Loot loot = Loot.valueOf(s.toUpperCase().replace(" ", "_"));
            if(loot != null) {
                int id = loot.getId();
                String name = loot.getName();
            }
        }*/

        GroundItem loot = GroundItems.newQuery().names("Herb", "Law rune", "Air rune", "Nature rune").results().nearest();

        if (loot != null) {
            int unnoted = loot.getDefinition().getUnnotedId();
            String name = loot.getDefinition().getName();
            return unnoted == 207 || name.equals("Air rune") || name.equals("Law rune") || name.equals("Nature rune");
        }
        return false;
    }
}