package com.runemate.geashawscripts.LazyAIOMiner;

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.hybrid.util.calculations.CommonMath;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import com.runemate.game.api.script.framework.task.TaskScript;
import com.runemate.geashawscripts.LazyAIOMiner.Tasks.BankHandler;
import com.runemate.geashawscripts.LazyAIOMiner.Tasks.DropHandler;
import com.runemate.geashawscripts.LazyAIOMiner.Tasks.MineHandler;
import com.runemate.geashawscripts.LazyAIOMiner.Tasks.RandomHandler;
import com.runemate.geashawscripts.LazyAIOMiner.gui.Loader;
import javafx.application.Platform;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class LazyAIOMiner extends TaskScript implements PaintListener, InventoryListener, MouseListener, MouseMotionListener {

    public static boolean guiOpen;

    public static String status = "Loading...";

    public static String selectedOre = "";
    public static String oreName = "";
    public static Coordinate preferedTile;

    public static Area mineArea;
    public static Area bankArea;

    public static boolean powermine;

    public static ArrayList<Integer> oreObjectIds = new ArrayList<>();

    public static final StopWatch runtime = new StopWatch();

    int startExp, gainedExp;

    public int oresMined;

    public void onStart(String... args) {
        startExp = Skill.MINING.getExperience();
        guiOpen = true;
        add(new RandomHandler(), new MineHandler(), new DropHandler(), new BankHandler());
        getEventDispatcher().addListener(this);
        setLoopDelay(100, 300);
        Platform.runLater(() -> new Loader());

        while (guiOpen) {
            Execution.delay(200);
        }

        if (!guiOpen) {
            runtime.start();
        }

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

    /**
     * This is where we put everything that we want to draw to the screen.
     */
    @Override
    public void onPaint(Graphics2D g) {
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

        gainedExp = Skill.MINING.getExperience() - startExp;

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
        g.drawString("Mined: " + displayFormatted(oresMined, runtime), TextXLocation, TextYLocation += 15);
        g.drawString("Gained: " + displayFormatted(gainedExp, runtime), TextXLocation, TextYLocation += 15);

        //Username Coverupper
        g.setColor(Color.BLACK);
        g.fillRect(7, ClientUI.getFrame().getHeight() - 126, userCoverWith, userCoverHeight);

        drawMouse(g);
    }

    /**
     * Method to format integers nicely with values per hour.
     * @param value
     *   Value to format.
     * @param runtime
     *   The runtime Stopwatch.
     */
    private String displayFormatted(int value, StopWatch runtime) {
        NumberFormat numFormat = NumberFormat.getIntegerInstance();
        return numFormat.format(value) + " (" + numFormat.format((int) CommonMath.rate(TimeUnit.HOURS, runtime.getRuntime(), value)) + ")";
    }

    public void drawMouse(Graphics g) {
        g.setColor(Mouse.isPressed() ? Color.RED : Color.GREEN);
        final Point m = Mouse.getPosition();
        g.drawLine(m.x -5, m.y + 5, m.x + 5, m.y - 5);
        g.drawLine(m.x -5, m.y - 5, m.x + 5, m.y + 5);
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