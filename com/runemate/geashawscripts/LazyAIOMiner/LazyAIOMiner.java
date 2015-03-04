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
import com.runemate.geashawscripts.LazyAIOMiner.Tasks.BankHandler;
import com.runemate.geashawscripts.LazyAIOMiner.Tasks.DropHandler;
import com.runemate.geashawscripts.LazyAIOMiner.Tasks.MineHandler;
import com.runemate.geashawscripts.LazyAIOMiner.Tasks.RandomHandler;
import com.runemate.geashawscripts.LazyAIOMiner.Utils.PaintTracker;
import com.runemate.geashawscripts.LazyAIOMiner.gui.Loader;
import javafx.application.Platform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class LazyAIOMiner extends TaskScript implements PaintListener, InventoryListener, MouseListener, MouseMotionListener {

    public static boolean guiOpen;
    public boolean showPaint = true;

    public static String status = "Loading...";

    public static String selectedOre = "";
    public static String oreName = "";
    public static Coordinate preferedTile;

    public static Area mineArea;
    public static Area bankArea;

    public static boolean powermine;
    public static boolean dropgems;

    public static int dropCounter = 1;

    public static ArrayList<Integer> oreObjectIds = new ArrayList<>();

    public static final StopWatch runtime = new StopWatch();
    public static long startTime = 0;

    public static int oresMined = 0, startExp = 0, startLevel = 0;

    public static int paintWidth = 190;
    public static int paintHeight = 95;
    public static int userCoverWith = 100;
    public static int userCoverHeight = 15;

    public void onStart(String... args) {

        startExp = Skill.MINING.getExperience();
        startLevel = Skill.MINING.getCurrentLevel();

        guiOpen = true;

        Platform.runLater(() -> new Loader());

        while (guiOpen) {
            Execution.delay(200);
        }

        if (!guiOpen) {
            runtime.start();
            startTime = System.currentTimeMillis();
        }

        add(new RandomHandler(), new MineHandler(), new DropHandler(), new BankHandler());
        getEventDispatcher().addListener(this);
        setLoopDelay(100, 300);

        if (!LazyAIOMiner.mineArea.contains(Players.getLocal())) {
            System.out.println("Please start the script at the selected mining spot.");
            Environment.getScript().stop();
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

    private final Image paint = getImage("http://puu.sh/gmnfh/46e6f62854.png");

    private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void onPaint(Graphics2D g) {
        PaintTracker.drawRotatingMouse(g);

        if (showPaint) {
            PaintTracker.drawPaint(g);
            g.setFont(new Font("Arial", 2, 12));
            g.drawString("Version: " + getMetaData().getVersion(), 408, 415);
        } else {
            g.setColor(Color.BLACK);
            g.drawString("Click to show", 431, 521);
        }

        PaintTracker.drawDraggablePaint(g);
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