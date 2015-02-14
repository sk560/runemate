package com.runemate.geashawscripts.LazySuicideThiever;

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.MessageEvent;
import com.runemate.game.api.script.framework.task.TaskScript;
import com.runemate.geashawscripts.LazySuicideThiever.Tasks.StealTask;
import com.runemate.geashawscripts.LazySuicideThiever.Utils.Methods;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Geashaw on 14-2-2015.
 */
public class LazySuicideThiever extends TaskScript implements PaintListener, InventoryListener, MouseListener, MouseMotionListener {

    public static String status = "Loading...";
    public static final StopWatch runtime = new StopWatch();
    int startExp = -1, xpGained;

    public void onStart(String... args) {

        // Check if player is logged in.
        if (startExp == -1 && Skill.CONSTITUTION.getExperience() > 0) {
            // Start the timer.
            runtime.start();
            startExp = Skill.THIEVING.getExperience();
        }

        // Add the new tasks from the Tasks folder.
        add(new StealTask());

        // Add the listener for the paint.
        getEventDispatcher().addListener(this);

        // Set the default script loop delay.
        setLoopDelay(100, 300);
    }

    /**
     * This is where we put everything that we want to draw to the screen.
     */
    @Override
    public void onPaint(Graphics2D g) {
        int paintWidth = 200;
        int paintHeight = 95;
        int userCoverWith = 100;
        int userCoverHeight = 15;

        int TextXLocation = startX + 5;
        int TextYLocation = startY + 5;

        final Color color1 = new Color(51, 102, 255, 155);
        final Color color2 = new Color(0, 0, 0);
        final Color color3 = new Color(255, 255, 255);
        final BasicStroke stroke1 = new BasicStroke(1);
        final Font font1 = new Font("Tahoma", 0, 12);

        xpGained = Skill.THIEVING.getExperience() - startExp;

        // Create the draggable blue rectangle
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
        g.drawString("Exp gained: " + Methods.formatNumber(xpGained) + " (" + Methods.formatNumber(Methods.getHourly(xpGained, runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);
        g.drawString("Status: " + status, TextXLocation, TextYLocation += 15);

        //Username Coverupper
        g.setColor(Color.black);
        g.fillRect(7, ClientUI.getFrame().getHeight() - 126, userCoverWith, userCoverHeight);
    }

    boolean isMouseDown = false;
    int relativeX, relativeY;
    int startX = 0, startY = 0;

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
