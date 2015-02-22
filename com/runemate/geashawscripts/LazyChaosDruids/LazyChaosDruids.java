package com.runemate.geashawscripts.LazyChaosDruids;

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.local.hud.interfaces.Health;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.task.TaskScript;
import com.runemate.geashawscripts.LazyChaosDruids.Data.Loot;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;
import com.runemate.geashawscripts.LazyChaosDruids.Tasks.*;
import com.runemate.geashawscripts.LazyChaosDruids.gui.Loader;
import javafx.application.Platform;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class LazyChaosDruids extends TaskScript implements PaintListener, InventoryListener, MouseListener, MouseMotionListener {

    public static boolean guiOpen;

    public static String status = "Loading...";
    public static final StopWatch runtime = new StopWatch();

    public static Loot loot;

    public static boolean lootAirRune, lootLawRune, lootNatureRune;
    public static boolean lootGuam, lootMarrentill, lootTarromin, lootHarralander, lootRanarr, lootToadflax, lootIrit,
                          lootAvantoe, lootKwuarm, lootCadantine, lootDwarfWeed, lootLantadyme, lootSnapdragon;

    public int startAttackLevel = 0, startStrengthLevel = 0, startDefenceLevel = 0, startConstitutionLevel = 0;
    public int startAttackExp = -1, startStrengthExp = -1, startDefenceExp = -1, startConstitutionExp = -1;
    public int attackExpGained = -1, strengthExpGained = -1, defenceExpGained = -1, constitutionExpGained = -1;

    public static int healPercentage;
    public static String foodName = "";
    public static int foodAmount;
    public static int maxLootTiles = 10;

    public static ArrayList<String> lootList = new ArrayList<>();

    public void onStart(String... args) {
        // Set the Gui to be open.
        guiOpen = true;

        // Launch the GUI on the JavaFX Application thread.
        Platform.runLater(() -> new Loader());
        while (guiOpen) {
            Execution.delay(200);
        }

        startAttackExp = Skill.ATTACK.getExperience();
        startStrengthExp = Skill.STRENGTH.getExperience();
        startDefenceExp = Skill.DEFENCE.getExperience();
        startConstitutionExp = Skill.CONSTITUTION.getExperience();

        startAttackLevel = Skill.ATTACK.getBaseLevel();
        startStrengthLevel = Skill.STRENGTH.getBaseLevel();
        startDefenceLevel = Skill.DEFENCE.getBaseLevel();
        startConstitutionLevel = Skill.CONSTITUTION.getBaseLevel();

        setLoopDelay(100, 300);
        getEventDispatcher().addListener(this);

        add(new FightTask(), new HealTask(), new LootTask(), new DropTask(), new DismissTask(), new TeleportTask(), new BankTask(), new WalkToDruidsTask());

        runtime.start();

        System.out.println("Using: " + foodName + " as food.");
        System.out.println("Healing at: " + healPercentage + " % hp.");
        System.out.println("Looting: " + lootList);
    }

    /**
     * This is where we put everything that we want to draw to the screen.
     */
    @Override
    public void onPaint(Graphics2D g) {
        attackExpGained = Skill.ATTACK.getExperience() - startAttackExp;
        strengthExpGained = Skill.STRENGTH.getExperience() - startStrengthExp;
        defenceExpGained = Skill.DEFENCE.getExperience() - startDefenceExp;
        constitutionExpGained = Skill.CONSTITUTION.getExperience() - startConstitutionExp;

        int paintWidth = 200;
        int paintHeight = 80;
        int userCoverWith = 100;
        int userCoverHeight = 15;

        int TextXLocation = startX + 5;
        int TextYLocation = startY + 5;

        final Font small = new Font("Tahoma", 0, 12);

        final Color color1 = new Color(51, 102, 255, 155);
        final Color color2 = new Color(0, 0, 0);
        final Color color3 = new Color(255, 255, 255);
        final BasicStroke stroke1 = new BasicStroke(1);

        // Debugging purposes
        g.setColor(color1);
        g.fillRect(startX + 1, startY + 1, paintWidth, paintHeight);
        g.fillRect(startX + 1, startY + 1, paintWidth, paintHeight);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRect(startX + 1, startY + 1, paintWidth, paintHeight);
        g.setFont(small);
        g.setColor(color3);

        // Standard paint.
        g.drawString(getMetaData().getName() + " - Version " + getMetaData().getVersion(), TextXLocation, TextYLocation += 10);
        g.drawString("Run time: " + runtime.getRuntimeAsString(), TextXLocation, TextYLocation += 15);
        g.drawString("Status: " + status, TextXLocation, TextYLocation += 15);

        if (attackExpGained > 0) {
            g.drawString("Attack: " + Methods.formatNumber(attackExpGained) + " (" + Methods.formatNumber(Methods.getHourly(attackExpGained, runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);
        }
        if (strengthExpGained > 0) {
            g.drawString("Strength: " + Methods.formatNumber(strengthExpGained) + " (" + Methods.formatNumber(Methods.getHourly(strengthExpGained, runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);
        }
        if (defenceExpGained > 0) {
            g.drawString("Defence: " + Methods.formatNumber(defenceExpGained) + " (" + Methods.formatNumber(Methods.getHourly(defenceExpGained, runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);
        }
        if (constitutionExpGained > 0) {
            g.drawString("Constitution: " + Methods.formatNumber(constitutionExpGained) + " (" + Methods.formatNumber(Methods.getHourly(constitutionExpGained, runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);
        }

        // Drawing Ground Items.
        DrawItemName(g);

        //Username Coverupper
        g.setColor(Color.BLACK);
        g.fillRect(7, ClientUI.getFrame().getHeight() - 126, userCoverWith, userCoverHeight);
    }

    /**
     * Drawing GroundItem names.
     */
    private void DrawItemName(Graphics2D g) {
        for (GroundItem loot:GroundItems.getLoaded()) {
            String s = loot.getDefinition().getName();
            String item = s.toLowerCase();
            int plus = 0;

            if (!item.equals("bones") && !item.equals("coins")) {
                Rectangle bound = loot.getModel().getBoundingRectangle();

                if (bound != null) {
                    if (loot.isVisible()) {
                        g.setColor(Color.BLACK);
                        g.setStroke(new BasicStroke(2));
                        g.setColor(Color.WHITE);
                        g.setStroke(new BasicStroke(1));
                        g.drawString(s, bound.x, bound.y);
                    }
                }
            }
        }
    }

    /**
     * Drawing herb names in Inventory.
     * Credits to SlashNHax.
     */
    private void DrawInventoryName(Graphics2D g) {

        final Font small = new Font("Tahoma", 0, 12);

        if(InterfaceWindows.getInventory().isOpen()){
            for(SpriteItem item:Inventory.getItems()){
                Rectangle bounds = item.getBounds();
                String lootName = "";
                for(Loot loot: Loot.values()){
                    if (item != null) {
                        if(item.getDefinition().getUnnotedId() == loot.getId()){
                            lootName = loot.getName();
                            break;
                        }
                    }
                }

                int x = bounds.x + (bounds.width-Methods.getWidth(small, lootName)) / 2;
                int y = bounds.y + (bounds.height-Methods.getHeight(small, lootName));

                g.setFont(new Font("Tahoma", Font.BOLD, 12));
                g.setColor(Color.YELLOW);
                //g.drawString(lootName, x, y);
                Methods.drawRotate(lootName, x, y, 15, g);
            }
        }
    }

    boolean isMouseDown = false;
    public static int startX, startY = 0;
    public static int relativeX, relativeY;

    public void mouseClicked(MouseEvent arg0) {}
    public void mouseEntered(MouseEvent arg0) {}
    public void mouseExited(MouseEvent arg0) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseReleased(MouseEvent arg0) {isMouseDown = false;}

    public void mousePressed(MouseEvent arg0) {
        isMouseDown = true;
        relativeX = arg0.getX() - startX;
        relativeY = arg0.getY() - startY;
    }
    public void mouseDragged(MouseEvent e) {
        if (isMouseDown == true) {
            startX = e.getX() - relativeX;
            startY = e.getY() - relativeY;
        }
    }
}