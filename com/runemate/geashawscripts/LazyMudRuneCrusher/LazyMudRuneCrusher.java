package com.runemate.geashawscripts.LazyMudRuneCrusher;

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import com.runemate.game.api.script.framework.task.TaskScript;
import com.runemate.geashawscripts.LazyMudRuneCrusher.Tasks.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class LazyMudRuneCrusher extends TaskScript implements PaintListener, InventoryListener, MouseListener, MouseMotionListener {

    public static String status = "Loading...";
    public static final StopWatch runtime = new StopWatch();
    public static int runesCrushed;

    public void onStart(String... args) {
        add(new CrushTask());
        getEventDispatcher().addListener(this);
        setLoopDelay(100, 300);
        runtime.start();
        System.out.println("Start test");
    }

    /**
     * Count items that are removed from inventory.
     */
    @Override
    public void onItemRemoved(ItemEvent arg0) {
        if (arg0.getItem().getDefinition().getName().equals("Mud rune")) {
            runesCrushed++;
        }
    }

    /**
     * This is where we put everything that we want to draw to the screen.
     */
    @Override
    public void onPaint(Graphics2D g) {

        int TextXLocation = startX + 5;
        int TextYLocation = startY + 5;

        final Color color1 = new Color(51, 102, 255, 155);
        final Color color2 = new Color(0, 0, 0);
        final Color color3 = new Color(255, 255, 255);
        final BasicStroke stroke1 = new BasicStroke(1);
        final Font font1 = new Font("Tahoma", 0, 12);

        g.setColor(color1);
        g.fillRect(startX + 1, startY + 1, paintWidth, paintHeight);
        g.fillRect(startX + 1, startY + 1, paintWidth, paintHeight);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRect(startX + 1, startY + 1, paintWidth, paintHeight);
        g.setFont(font1);
        g.setColor(color3);

        g.drawString(getMetaData().getName() + " - Version " + getMetaData().getVersion(), TextXLocation, TextYLocation += 10);
        g.drawString("Run time: " + runtime.getRuntimeAsString(), TextXLocation, TextYLocation += 15);
        g.drawString("Status: " + status, TextXLocation, TextYLocation += 15);
        g.drawString("Runes Crushed: " + Methods.formatNumber(runesCrushed) + " (" + Methods.formatNumber(Methods.getHourly(runesCrushed, runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);

        //Username Coverupper
        g.setColor(Color.black);
        g.fillRect(0, ClientUI.getFrame().getHeight() - 103, userCoverWith, userCoverHeight);
    }

    public static int startX, startY = 0;
    public static int relativeX, relativeY;
    public static int paintWidth = 210;
    public static int paintHeight = 65;
    public static int userCoverWith = 100;
    public static int userCoverHeight = 50;
    public static boolean isMouseDown = false;

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
