package com.runemate.geashawscripts.LazyAIOMiner;

import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.local.Screen;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import com.runemate.game.api.script.framework.task.TaskScript;
import com.runemate.geashawscripts.LazyAIOMiner.Tasks.*;
import com.runemate.geashawscripts.LazyAIOMiner.Utils.PaintTracker;
import com.runemate.geashawscripts.LazyAIOMiner.gui.Loader;
import javafx.application.Platform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class LazyAIOMiner extends TaskScript implements PaintListener, InventoryListener, MouseListener, MouseMotionListener {

    public static final String[] GEMS = {"Uncut sapphire", "Uncut emerald", "Uncut ruby", "Uncut diamond"};

    public static boolean guiOpen;
    public boolean showPaint = true;

    public static String status = "Loading...";
    public static String username = "";
    public static int userId;

    public static String location = "";
    public static String oreName = "";

    public static Area mineArea;
    public static Area bankArea;

    public static boolean powermine;
    public static boolean dropgems;
    public static boolean noMove;

    public static final Coordinate spot = Players.getLocal().getPosition();

    public static ArrayList<Integer> oreObjectIds = new ArrayList<>();

    public static final StopWatch runtime = new StopWatch();
    public static StopWatch updateTimer = new StopWatch();
    public static final int updateIntervalMilliSeconds = 60000;

    public static long startTime = 0;
    public static long timeRanSoFar, lastRunTime;

    public static int oresMined, oresMinedSoFar, lastOresMined,startExp, startLevel, xpGained, expGainedSoFar, lastExpGained;

    public static int paintWidth = 190;
    public static int paintHeight = 95;
    public static int userCoverWith = 100;
    public static int userCoverHeight = 15;

    public void onStart(String... args) {

        setLoopDelay(100, 300);

        startExp = Skill.MINING.getExperience();
        startLevel = Skill.MINING.getCurrentLevel();

        // Getting the forum data.
        userId = Environment.getForumId();
        username = Environment.getForumName();

        guiOpen = true;

        Platform.runLater(() -> new Loader());

        while (guiOpen) {
            Execution.delay(200);
        }

        if (!guiOpen) {

            // Starting both timers.
            runtime.start();
            updateTimer.start();

            startTime = System.currentTimeMillis();

            if (!mineArea.contains(Players.getLocal()) && !bankArea.contains(Players.getLocal())) {
                System.out.println("Please start the script at the bank or the selected mining spot.");
                Environment.getScript().stop();
            }

            add(new UpdateTask(), new RandomHandler(), new MineHandler(), new DropHandler(), new ToBankHandler(), new BankHandler(), new ToMineHandler());
            getEventDispatcher().addListener(this);

            System.out.println("Powermine: " + LazyAIOMiner.powermine);
            System.out.println("Don't move: " + LazyAIOMiner.noMove);
            System.out.println("Drop gems: " + LazyAIOMiner.dropgems);
            System.out.println("Ore name: " + oreName);
            System.out.println("Location: " + location);
        }
    }

    /**
     * Count items that are added to inventory.
     */
    @Override
    public void onItemAdded(ItemEvent arg0) {
        if (arg0.getItem().getDefinition().getName().equals(oreName)) {
            oresMined++;
        }
    }

    @Override
    public void onPaint(Graphics2D g) {

        if (showPaint) {
            PaintTracker.drawPaint(g);
            g.setFont(new Font("Arial", 2, 12));
            g.drawString("Version: " + getMetaData().getVersion(), 406, 415);
        } else {
            g.setColor(Color.BLACK);
            g.drawString("Click to show", 431, 521);
        }

        PaintTracker.drawRotatingMouse(g);
    }

    final Rectangle hideButton = new Rectangle(0, 388, 518, 215);

    boolean isMouseDown = false;
    public static int startX, startY = 0;
    public static int relativeX, relativeY;

    public void mouseClicked(MouseEvent e) {
        if (hideButton.contains(e.getPoint())) {
            showPaint = !showPaint;
        }
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) { isMouseDown = false; }

    public void mousePressed(MouseEvent e) {
        isMouseDown = true;
        relativeX = e.getX() - startX;
        relativeY = e.getY() - startY;
    }

    public void mouseDragged(MouseEvent e) {
        if (isMouseDown == true && Screen.getBounds().contains(new Rectangle(e.getX() - relativeX, e.getY() - relativeY, paintWidth, paintHeight))) {
            startX = e.getX() - relativeX;
            startY = e.getY() - relativeY;
        }
    }
}