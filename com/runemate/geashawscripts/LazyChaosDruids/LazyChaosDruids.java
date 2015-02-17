package com.runemate.geashawscripts.LazyChaosDruids;

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.task.TaskScript;
import com.runemate.geashawscripts.LazyChaosDruids.Data.Food;
import com.runemate.geashawscripts.LazyChaosDruids.Data.Herb;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;
import com.runemate.geashawscripts.LazyChaosDruids.Tasks.FightTask;
import com.runemate.geashawscripts.LazyChaosDruids.Tasks.HealTask;
import com.runemate.geashawscripts.LazyChaosDruids.Tasks.LootTask;
import com.runemate.geashawscripts.LazyChaosDruids.Utilities.ExpTracker;
import com.runemate.geashawscripts.LazyChaosDruids.Utilities.ExpTrackerContainer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class LazyChaosDruids extends TaskScript implements PaintListener, InventoryListener, MouseListener, MouseMotionListener {

    public static String status = "Loading...";
    public static final StopWatch runtime = new StopWatch();

    public static Food food = Food.TROUT;
    public static Herb herb;

    ExpTracker constiution, strength;
    ExpTrackerContainer expTrackerContainer;

    public void onStart(String... args) {
        // Add the new tasks from the Tasks folder.
        add(new FightTask(), new HealTask(), new LootTask());
        // Add the listener for the paint.
        getEventDispatcher().addListener(this);
        // Set the default script loop delay.
        setLoopDelay(100, 300);
        // Start the timer.
        runtime.start();

        constiution = new ExpTracker(Skill.CONSTITUTION, Color.BLACK, new Color(0, 0, 0, 150), new Color(65, 4, 9), Color.WHITE);
        strength = new ExpTracker(Skill.STRENGTH, Color.BLACK, new Color(0,0,0, 150), new Color(0, 6, 73), Color.WHITE);
        expTrackerContainer = new ExpTrackerContainer(strength, constiution);
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
        final Font small = new Font("Tahoma", 0, 9);

        // Create the draggable blue rectangle
        /*g.setColor(color1);
        g.fillRect(startX + 1, startY + 1, paintWidth, paintHeight);
        g.fillRect(startX + 1, startY + 1, paintWidth, paintHeight);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRect(startX + 1, startY + 1, paintWidth, paintHeight);
        g.setFont(font1);
        g.setColor(color3);

        g.drawString(getMetaData().getName() + " - Version " + getMetaData().getVersion(), TextXLocation, TextYLocation += 10);
        g.drawString("Run time: " + runtime.getRuntimeAsString(), TextXLocation, TextYLocation += 15);
        g.drawString("Status: " + status, TextXLocation, TextYLocation += 15);*/

        // Test xp tracker.
        if (expTrackerContainer != null) {
            expTrackerContainer.draw(g, TextXLocation, TextYLocation);
        }

        // Trying to paint on the inventory.
        g.setColor(Color.WHITE);
        g.setFont(small);

        // Credits to SlashNHax
        if(InterfaceWindows.getInventory().isOpen()){
            for(SpriteItem item:Inventory.getItems()){
                Rectangle bounds = item.getBounds();
                String herbName = "";
                for(Herb herb:Herb.values()){
                    if(item.getDefinition().getUnnotedId() == herb.getId()){
                        herbName = herb.getName();
                        break;
                    }
                }
                //Methods.drawRotate(herbName, bounds.x + (bounds.width / 2), bounds.y + (bounds.height / 2), 0, g);
                int x = bounds.x - (bounds.width-Methods.getWidth(small, herbName)) / 2;
                int y = bounds.y - (bounds.width-Methods.getWidth(small, herbName)) / 2;
                g.drawString(herbName, x, y);
            }
        }

        //Username Coverupper
        g.setColor(Color.BLACK);
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