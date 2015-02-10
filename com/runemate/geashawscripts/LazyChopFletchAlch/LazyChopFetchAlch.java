package com.runemate.geashawscripts.LazyChopFletchAlch;

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.rs3.net.GrandExchange;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import com.runemate.game.api.script.framework.task.TaskScript;
import com.runemate.geashawscripts.LazyChopFletchAlch.Tasks.*;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Geashaw on 7-2-2015.
 */
public class LazyChopFetchAlch extends TaskScript implements PaintListener, MouseListener, MouseMotionListener, InventoryListener {

    public void onStart(String... args) {
        // Add all tasks.
        add(new Chop(), new Fletch(), new Alch(), new Misc(), new Exit());
        // Add the listener for the paint.
        getEventDispatcher().addListener(this);
        // Starting both timers.
        setLoopDelay(100, 300);
        getEventDispatcher().addListener(this);
        Constants.runtime.start();
        Constants.naturePrice = GrandExchange.lookup(561).getPrice();
        Constants.shieldbowPrice = GrandExchange.lookup(66).getPrice();
        Methods.debug("Interface.");
    }
    /**
     * Count items that are removed from inventory.
     */
    @Override
    public void onItemRemoved(ItemEvent arg0) {
        if (arg0.getItem().getDefinition().getName().contains("Yew shieldbow")) {
            Constants.bowsAlched++;
        } else if (arg0.getItem().getDefinition().getName().contains("Yew log")) {
            Constants.bowsFletched++;
        }
    }
    /**
     * Count items that are added to inventory.
     */
    @Override
    public void onItemAdded(ItemEvent arg0) {
        if (arg0.getItem().getDefinition().getName().contains("Yew log")) {
            Constants.logsChopped++;
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

        int profitMade = (Constants.bowsAlched / 2 * Constants.naturePrice) - (Constants.bowsAlched / 2 * Constants.shieldbowPrice);

        g.setColor(color1);
        g.fillRect(Constants.startX + 1, Constants.startY + 1, Constants.paintWidth, Constants.paintHeight);
        g.fillRect(Constants.startX + 1, Constants.startY + 1, Constants.paintWidth, Constants.paintHeight);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRect(Constants.startX + 1, Constants.startY + 1, Constants.paintWidth, Constants.paintHeight);
        g.setFont(font1);
        g.setColor(color3);

        g.drawString("Run time: " + Constants.runtime.getRuntimeAsString(), TextXLocation, TextYLocation += 10);
        g.drawString("Status: " + Constants.status, TextXLocation, TextYLocation += 15);
        g.drawString("Chopped: " + Methods.formatNumber(Constants.logsChopped / 2) + " (" + Methods.formatNumber(Methods.getHourly(Constants.logsChopped / 2, Constants.runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);
        g.drawString("Fletched: " + Methods.formatNumber(Constants.bowsFletched / 2) + " (" + Methods.formatNumber(Methods.getHourly(Constants.bowsFletched / 2, Constants.runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);
        g.drawString("Alched: " + Methods.formatNumber(Constants.bowsAlched / 2) + " (" + Methods.formatNumber(Methods.getHourly(Constants.bowsAlched / 2, Constants.runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);
        g.drawString("Profit: " + Methods.formatNumber(profitMade) + " (" + Methods.formatNumber(Methods.getHourly(profitMade, Constants.runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);

        //Username Coverupper
        g.setColor(Color.black);
        g.fillRect(0, ClientUI.getFrame().getHeight() - 103, Constants.userCoverWith, Constants.userCoverHeight);
    }
    public void mousePressed(MouseEvent arg0) {
        Constants.isMouseDown = true;
        Constants.relativeX = arg0.getX() - Constants.startX;
        Constants.relativeY = arg0.getY() - Constants.startY;
    }

    public void mouseDragged(MouseEvent e) {
        if (Constants.isMouseDown == true) {
            Constants.startX = e.getX() - Constants.relativeX;
            Constants.startY = e.getY() - Constants.relativeY;
        }
    }
    public void mouseReleased(MouseEvent arg0) {
        Constants.isMouseDown = false;
    }
    public void mouseClicked(MouseEvent arg0) {}
    public void mouseEntered(MouseEvent arg0) {}
    public void mouseExited(MouseEvent arg0) {}
    public void mouseMoved(MouseEvent e) {}
}
