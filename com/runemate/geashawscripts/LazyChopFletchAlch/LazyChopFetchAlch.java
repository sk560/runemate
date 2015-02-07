package com.runemate.geashawscripts.LazyChopFletchAlch;

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import com.runemate.game.api.script.framework.task.TaskScript;
import com.runemate.geashawscripts.LazyAutoTanner.Constants;
import com.runemate.geashawscripts.LazyAutoTanner.Tasks.Exit;
import com.runemate.geashawscripts.LazyChopFletchAlch.Tasks.Alch;
import com.runemate.geashawscripts.LazyChopFletchAlch.Tasks.Chop;
import com.runemate.geashawscripts.LazyChopFletchAlch.Tasks.Fletch;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Deka on 7-2-2015.
 */
public class LazyChopFetchAlch extends TaskScript implements PaintListener, MouseListener, MouseMotionListener, InventoryListener {

    public void onStart(String... args) {
        // Add all tasks.
        add(new Chop(), new Fletch(), new Alch(), new Exit());
        // Add the listener for the paint.
        getEventDispatcher().addListener(this);
        // Starting both timers.
        setLoopDelay(100, 300);
        getEventDispatcher().addListener(this);
    }

    /**
     * This is where we put everything that we want to draw to the screen.
     */
    @Override
    public void onPaint(Graphics2D g) {
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
