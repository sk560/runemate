package com.runemate.geashawscripts.LazyBananaPicker;

//Imports are all the classes that we are going to use methods from

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.hybrid.RuneScape;
import com.runemate.game.api.hybrid.entities.Actor;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.SlotAction;
import com.runemate.game.api.rs3.net.GrandExchange;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.LoopingScript;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.NumberFormat;

public class LazyBananaPicker extends LoopingScript implements PaintListener, InventoryListener, MouseListener, MouseMotionListener {

    private String status = "Starting up...";
    private final StopWatch runtime = new StopWatch();

    private final String BANANA = "Banana";
    private final String BASKET = "Basket";
    private final String FILLED_BASKET = "Bananas (5)";
    private final String GLORY = "Amulet of glory";

    private int BANANA_COUNT, BANANA_BASKET_PRICE, PROFIT_MADE;

    final Area KARAMJA_AREA = new Area.Rectangular(new Coordinate(2905, 3154), new Coordinate(2935, 3180, 0));
    final Area EDGEVILLE_AREA = new Area.Rectangular(new Coordinate(3082, 3487, 0), new Coordinate(3097, 3499, 0));
    final Player player = Players.getLocal();
    private Path walkToTree;

    @Override
    public void onStart(String... args) {
        setLoopDelay(100, 200);
        getEventDispatcher().addListener(this);
        BANANA_BASKET_PRICE = GrandExchange.lookup(5416).getPrice();
        runtime.start();
        debug("Blie bloe!");
    }

    @Override
    public void onPause() {
        runtime.stop();
    }

    @Override
    public void onResume() {
        runtime.start();
    }

    @Override
    public void onLoop() {
        // Check if the user is logged in.
        if (RuneScape.isLoggedIn()) {
            if (atEdgeville()) {
                if (gotFilledBaskets()) {
                    Npc banker = Npcs.newQuery().names("Banker").results().nearest();
                    if (banker != null) {
                        if (banker.isVisible()) {
                            if (Bank.isOpen()) {
                                performBankPreset();
                            } else {
                                openBank();
                            }
                        } else {
                            Camera.setPitch(0.234);
                            Camera.turnTo(banker);
                        }
                    }
                } else {
                    if (gotAllEmptyBaskets()) {
                        if (!isBusy(player)) {
                            gloryTeleportTo("Karamja");
                        }
                    }
                }
            } else if (atKaramja()) {
                if (gotFilledBaskets()) {
                    if (!isBusy(player)) {
                        gloryTeleportTo("Edgeville");
                    }
                } else if (canPutBananasInBasket()) {
                    putBananasIntoBasket();
                } else if (canPickBananasFromTree()) {
                    pickBananasFromTree();
                }
            }
        }
    }

    /**
     * Teleport to one of the four glory locations.
     */
    private boolean gloryTeleportTo(String location) {
        SlotAction action = ActionBar.getFirstAction(GLORY);
        if (action != null) {
            status = "Activating glory.";
            if (action.activate()) {
                Execution.delay(500, 1000);
                if (gloryInterfaceIsVisible(location)) {
                    InterfaceComponent com = Interfaces.newQuery().textContains(location).results().first();
                    Execution.delay(500, 1000);
                    if (com != null) {
                        status = "Selecting " + location + " teleport";
                        if (com.click()) {
                            Execution.delay(1500, 3000);
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Check if the teleport interface is visible.
     */
    private boolean gloryInterfaceIsVisible(String location) {
        InterfaceComponent component = Interfaces.newQuery().textContains(location).visible().results().first();
        return (component != null && component.isValid());
    }

    /**
     * Perform quick banking with a bank preset.
     */
    private boolean performBankPreset() {
        InterfaceComponent component = Interfaces.getAt(762, 41);

        if (component != null && component.isVisible()) {
            if (component.click()) {
                status = "Performing bank preset";
                debug("Performing bank preset");
                Execution.delayUntil(() -> !Bank.isOpen(), 0, 1000);
                return true;
            }
        }

        return false;
    }

    /**
     * Open the bank.
     */
    private boolean openBank() {
        status = "Opening the bank.";
        debug("Opening the bank");
        if (Bank.open()) {
            if (!Bank.isOpen()) {
                Execution.delayUntil(() -> Bank.isOpen(), 500);
                return true;
            }
        }
        return false;
    }

    /**
     * Pick bananas from a tree.
     */
    private boolean pickBananasFromTree() {
        GameObject tree = GameObjects.newQuery().names("Banana Tree").actions("Pick").results().nearest();

        if (tree != null && KARAMJA_AREA.contains(tree)) {
            if (tree.distanceTo(player) > 7) {
                status = "Tree is further than 7 steps";
                walkToTree = Traversal.getDefaultWeb().getPathBuilder().buildTo(tree);
                if (walkToTree != null) {
                    status = "Walking to tree";
                    walkToTree.step(true);
                } else {
                    debug("Can't walk to tree...");
                    status = "Can't walk to tree...";
                }
            } else {
                if (tree.isVisible()) {
                    if (!isBusy(player)) {
                        if (Camera.getPitch() < 0.35) {
                            Camera.setPitch(0.60, 0.65);
                        } else {
                            status = "Picking bananas.";
                            return tree.interact("Pick");
                        }
                    }
                } else {
                    debug("Turning camera to tree");
                    Camera.turnTo(tree);
                }
            }
        }

        return false;
    }

    /**
     * Put banana into an empty basket using the action bar.
     */
    private boolean putBananasIntoBasket() {
        SlotAction action = ActionBar.getFirstAction("Basket");

        if (action != null) {
            status = "Putting bananas into basket.";
            if (Keyboard.typeKey(action.getSlot().getKeyBind())) {
                if (gotBananas()) {
                    Execution.delayUntil(() -> !gotBananas(), 0, 500);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if whether the player is at Edgeville or not.
     */
    private boolean atEdgeville() {
        return EDGEVILLE_AREA.contains(player);
    }

    /**
     * Check if the player is at Karamja or not.
     */
    private boolean atKaramja() {
        return KARAMJA_AREA.contains(player);
    }

    /**
     * Check if the player has at least 5 bananas in inventory.
     */
    private boolean gotBananas() {
        return (Inventory.contains(BANANA) && Inventory.getQuantity(BANANA) >= 5);
    }

    /**
     * Check if the player has at least one empty basket
     */
    private boolean gotEmptyBaskets() {
        return (Inventory.contains(BASKET) && Inventory.getQuantity(BASKET) >= 1);
    }

    /**
     * Check if the player has at least 23 empty baskets
     */
    private boolean gotAllEmptyBaskets() {
        return (Inventory.contains(BASKET) && Inventory.getQuantity(BASKET) >= 23);
    }

    /**
     * Check if the the player has at least 23 filled baskets.
     */
    private boolean gotFilledBaskets() {
        return (Inventory.contains(FILLED_BASKET) && Inventory.getQuantity(FILLED_BASKET) >= 23);
    }

    /**
     * Check if the player can pick bananas from a tree.
     */
    private boolean canPickBananasFromTree() {
        return !Inventory.isFull() && !gotBananas();
    }

    /**
     * Check if the player can put bananas into an empty basket.
     */
    private boolean canPutBananasInBasket() {
        return gotBananas() && gotEmptyBaskets();
    }

    private boolean isBusy(final Actor player) {
        return player.isMoving() || player.getAnimationId() != -1;
    }

    /**
     * Turn amount gained into amount gained per hour.
     *
     * @param amount  The amount of experience.
     * @param elapsed The elapsed time.
     * @return Returns the int amount per hour.
     */
    public int getHourly(final int amount, final long elapsed) {
        return (int) (amount * 3600000.0D / elapsed);
    }

    /**
     * Method to format thousands decimal.
     *
     * @param i The integer to format.
     * @return Returns the integer as formatted number.
     */
    protected String formatNumber(int i) {
        return NumberFormat.getIntegerInstance().format(i);
    }

    /**
     * Helper method used to replace System.out.println(text);
     *
     * @param text The text to send to the console.
     */
    private void debug(String text) {
        System.out.println(text);
    }

    /**
     * Count items that are added to inventory.
     */
    @Override
    public void onItemAdded(ItemEvent arg0) {
        if (arg0.getItem().getDefinition().getName().equals("Banana")) {
            BANANA_COUNT++;
        }
    }

    private int startX, startY = 0;
    private int relativeX, relativeY;
    public int paintWidth = 200;
    public int paintHeight = 95;
    public int userCoverWith = 85;
    public int userCoverHeight = 19;

    private static boolean isMouseDown = false;

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

        PROFIT_MADE = BANANA_COUNT / 5 * BANANA_BASKET_PRICE;
        g.drawString(getMetaData().getName() + " - Version " + getMetaData().getVersion(), TextXLocation, TextYLocation += 10);
        g.drawString("Run time: " + runtime.getRuntimeAsString(), TextXLocation, TextYLocation += 15);
        g.drawString("Status: " + status, TextXLocation, TextYLocation += 15);
        g.drawString("Bananas: " + formatNumber(BANANA_COUNT)  + " (" + formatNumber(getHourly(BANANA_COUNT, runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);
        g.drawString("Baskets: " + formatNumber(BANANA_COUNT / 5)  + " (" + formatNumber(getHourly(BANANA_COUNT /5, runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);
        g.drawString("Profits: " + formatNumber(PROFIT_MADE)  + " (" + formatNumber(getHourly(PROFIT_MADE, runtime.getRuntime())) + ")", TextXLocation, TextYLocation += 15);

        //Username Coverupper
        g.setColor(Color.black);
        g.fillRect(0, ClientUI.getFrame().getHeight() - 103, userCoverWith, userCoverHeight);
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
        isMouseDown = true;
        relativeX = arg0.getX() - startX;
        relativeY = arg0.getY() - startY;
    }
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
        isMouseDown = false;
    }
    public void mouseDragged(MouseEvent e) {
        if (isMouseDown == true) {
            startX = e.getX() - relativeX;
            startY = e.getY() - relativeY;
        }
    }
    public void mouseMoved(MouseEvent e) {

    }
}