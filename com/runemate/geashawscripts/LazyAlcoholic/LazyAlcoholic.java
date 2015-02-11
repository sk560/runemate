package com.runemate.geashawscripts.LazyAlcoholic;

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.rs3.net.GrandExchange;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import com.runemate.game.api.script.framework.task.TaskScript;
import com.runemate.geashawscripts.LazyAlcoholic.Tasks.*;
import com.runemate.geashawscripts.LazyAlcoholic.Utils.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class LazyAlcoholic extends TaskScript implements PaintListener, InventoryListener, MouseListener, MouseMotionListener {

    public void onStart(String... args) {

        // Add the new tasks from the Tasks folder.
        add(new Drink(), new Bank(), new Exit());

        // Get the prices of the Jug and the Jug of wine.
        Constants.jugPrice = GrandExchange.lookup(1935).getPrice();
        Constants.winePrice = GrandExchange.lookup(1993).getPrice();

        // Add the listener for the paint.
        getEventDispatcher().addListener(this);

        // Set the default script loop delay.
        setLoopDelay(100, 300);

        // Start the timer.
        Constants.runtime.start();
    }

    /**
     * Count items that are removed from inventory.
     */
    @Override
    public void onItemRemoved(ItemEvent arg0) {
        // If the item name equals the String for wine.
        if (arg0.getItem().getDefinition().getName().equals(Constants.wine)) {
            // Increment the wineDrunk counter by one.
            Constants.wineDrunk++;
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

        // Calculate the profit by subtracting the wine price from the jug price.
        Constants.profitPerJug = Constants.jugPrice - Constants.winePrice;

        // Calculate the profit made by multiplying the profit per jug with the wine drunk.
        Constants.profitMade = Constants.profitPerJug * Constants.wineDrunk;

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
        g.drawString("Status: " + Constants.status, TextXLocation, TextYLocation += 15);
        g.drawString("Wine drunk: " + Methods.formatNumber(Constants.wineDrunk) + " (" + Methods.formatNumber(Methods.getHourly(Constants.wineDrunk, Constants.runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);
        g.drawString("Profit made: " + Methods.formatNumber(Constants.profitMade) + " (" + Methods.formatNumber(Methods.getHourly(Constants.profitMade, Constants.runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);

        //Username Coverupper
        g.setColor(Color.black);
        g.fillRect(0, ClientUI.getFrame().getHeight() - 103, com.runemate.geashawscripts.LazyAutoTanner.Constants.userCoverWith, com.runemate.geashawscripts.LazyAutoTanner.Constants.userCoverHeight);
    }

    public void mouseClicked(MouseEvent arg0) {}
    public void mouseEntered(MouseEvent arg0) {}
    public void mouseExited(MouseEvent arg0) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseReleased(MouseEvent arg0) {Constants.isMouseDown = false;}

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

}
