package com.runemate.geashawscripts.LazyAutoTanner;

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.rs3.net.GrandExchange;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import com.runemate.game.api.script.framework.task.TaskScript;
import com.runemate.geashawscripts.LazyAutoTanner.Tasks.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Geashaw on 6-2-2015.
 */
public class LazyAutoTanner extends TaskScript implements PaintListener, InventoryListener, MouseListener, MouseMotionListener {

    public void onStart(String... args) {
        // Add all tasks.
        add(new Cast(), new Bank(), new Update(), new Exit());

        Constants.hidePrice = GrandExchange.lookup(1753).getPrice();
        Constants.leatherPrice = GrandExchange.lookup(1745).getPrice();
        Constants.bodyRunePrice = GrandExchange.lookup(559).getPrice();
        Constants.astralRunePrice = GrandExchange.lookup(9075).getPrice();

        // Getting the forum data.
        Constants.USER_ID = com.runemate.game.api.hybrid.Environment.getForumId();
        Constants.USERNAME = com.runemate.game.api.hybrid.Environment.getForumName();

        // Add the listener for the paint.
        getEventDispatcher().addListener(this);

        // Starting both timers.
        Constants.runtime.start();
        Constants.updateTimer.start();

        if (Constants.startExp == -1 && Skill.CONSTITUTION.getExperience() > 0) {
            Constants.runtime.start();
            Constants.startExp = Skill.MAGIC.getExperience();
        }

        setLoopDelay(100, 300);
        getEventDispatcher().addListener(this);
    }

    /**
     * Count items that are added to inventory.
     */
    @Override
    public void onItemAdded(ItemEvent arg0) {
        if (arg0.getItem().getDefinition().getName().equals(Constants.TANNED_HIDE)) {
            Constants.hidesTanned++;
        }
    }

    /**
     * This is where we put everything that we want to draw to the screen.
     */
    @Override
    public void onPaint(Graphics2D g) {
        int TextXLocation = Constants.startX + 5;
        int TextYLocation = Constants.startY + 5;

        final Color color1 = new Color(51, 102, 255, 155);
        final Color color2 = new Color(0, 0, 0);
        final Color color3 = new Color(255, 255, 255);
        final BasicStroke stroke1 = new BasicStroke(1);
        final Font font1 = new Font("Tahoma", 0, 12);

        Constants.xpGained = Skill.MAGIC.getExperience() - Constants.startExp;

        Constants.runeCostPerHide = ((2 * Constants.bodyRunePrice) / 5) - ((2 * Constants.astralRunePrice) / 5);
        Constants.profitPerHide = Constants.leatherPrice - Constants.hidePrice - Constants.runeCostPerHide;
        Constants.profitMade = Constants.profitPerHide * Constants.hidesTanned;

        g.setColor(color1);
        g.fillRect(Constants.startX + 1, Constants.startY + 1, Constants.paintWidth, Constants.paintHeight);
        g.fillRect(Constants.startX + 1, Constants.startY + 1, Constants.paintWidth, Constants.paintHeight);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRect(Constants.startX + 1, Constants.startY + 1, Constants.paintWidth, Constants.paintHeight);
        g.setFont(font1);
        g.setColor(color3);

        g.drawString(getMetaData().getName() + " - Version " + getMetaData().getVersion(), TextXLocation, TextYLocation += 10);
        g.drawString("Run time: " + Constants.runtime.getRuntimeAsString(), TextXLocation, TextYLocation += 15);
        g.drawString("Status: " + Constants.STATUS, TextXLocation, TextYLocation += 15);
        g.drawString("Exp gained: " + Methods.formatNumber(Constants.xpGained) + " (" + Methods.formatNumber(Methods.getHourly(Constants.xpGained, Constants.runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);
        g.drawString("Hides tanned: " + Methods.formatNumber(Constants.hidesTanned / 2) + " (" + Methods.formatNumber(Methods.getHourly(Constants.hidesTanned / 2, Constants.runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);
        g.drawString("Profit made: " + Methods.formatNumber(Constants.profitMade) + " (" + Methods.formatNumber(Methods.getHourly(Constants.profitMade, Constants.runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);

        //Username Coverupper
        g.setColor(Color.black);
        g.fillRect(0, ClientUI.getFrame().getHeight() - 103, Constants.userCoverWith, Constants.userCoverHeight);
    }

    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
        Constants.isMouseDown = true;
        Constants.relativeX = arg0.getX() - Constants.startX;
        Constants.relativeY = arg0.getY() - Constants.startY;
    }
    public void mouseReleased(MouseEvent arg0) {
        Constants.isMouseDown = false;
    }
    public void mouseDragged(MouseEvent e) {
        if (Constants.isMouseDown == true) {
            Constants.startX = e.getX() - Constants.relativeX;
            Constants.startY = e.getY() - Constants.relativeY;
        }
    }
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}
