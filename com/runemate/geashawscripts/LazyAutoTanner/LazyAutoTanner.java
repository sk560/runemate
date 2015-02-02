package com.runemate.geashawscripts.LazyAutoTanner;

//Imports are all the classes that we are going to use methods from

import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.hybrid.RuneScape;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.local.hud.interfaces.*;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.SlotAction;
import com.runemate.game.api.rs3.net.GrandExchange;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.LoopingScript;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import javafx.application.Platform;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;

public class LazyAutoTanner extends LoopingScript implements PaintListener, InventoryListener {

    private String status = "Loading...";

    private final String makeLeatherAction = "Make Leather";
    private final String dragonHide = "Green dragonhide";
    private final String tannedHide = "Green dragon leather";
    private String userName;

    private final StopWatch runtime = new StopWatch();
    private StopWatch updateTimer = new StopWatch();
    private final int updateInterval = 15000;

    private long timeRanSoFar, lastRunTime;

    private int xpGained = 0,
            hidesTanned = 0,
            hidesTannedSoFar = 0,
            lastHidesTanned = 0,
            expGainedSoFar = 0,
            profitMadeSoFar = 0,
            startExp = -1,
            runeCostPerHide,
            profitPerHide,
            profitMade,
            leatherPrice,
            hidePrice,
            bodyRunePrice,
            astralRunePrice,
            userId,
            lastExpGained,
            lastProfitMade;

    @Override
    public void onStart(String... args) {
        hidePrice = GrandExchange.lookup(1753).getPrice();
        leatherPrice = GrandExchange.lookup(1745).getPrice();
        bodyRunePrice = GrandExchange.lookup(559).getPrice();
        astralRunePrice = GrandExchange.lookup(9075).getPrice();

        // Getting the forum data.
        userId = com.runemate.game.api.hybrid.Environment.getForumId();
        userName = com.runemate.game.api.hybrid.Environment.getForumName();

        // Add the listener for the paint.
        getEventDispatcher().addListener(this);

        // Starting both timers.
        runtime.start();
        updateTimer.start();

        // Standard loop delay little slower than normal.
        setLoopDelay(100, 200);
        debug("Debugging dynamic signature.");
        Platform.runLater(() -> new LazyAutoTannerGUI(this));
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

            // Update the database.
            if (updateTimer.getRuntime() > updateInterval) {

                timeRanSoFar = (runtime.getRuntime() - lastRunTime);
                expGainedSoFar = xpGained - lastExpGained;
                profitMadeSoFar = profitMade - lastProfitMade;
                hidesTannedSoFar = hidesTanned - lastHidesTanned;

                updateDatabase(userId, userName, timeRanSoFar, expGainedSoFar, profitMadeSoFar, hidesTannedSoFar);

                updateTimer.reset();

                // Reset xp gained, profit made and hides tanned.
                lastRunTime = runtime.getRuntime();
                lastExpGained = xpGained;
                lastProfitMade = profitMade;
                lastHidesTanned = hidesTanned;
            }

            // Perform the loop actions.
            if (gotHides() && !gotLeather()) {
                if (startExp == -1 && Skill.CONSTITUTION.getExperience() > 0) {
                    runtime.start();
                    startExp = Skill.MAGIC.getExperience();
                }
                if (interfaceTextIsVisible("Tan")) {
                    pressSpacebar();
                } else {
                    if (spellSelected()) {
                        clickHide();
                    } else {
                        selectSpell();
                    }
                }
            } else if (gotLeather() && !gotHides()) {
                if (Bank.isOpen()) {
                    performBankPreset();
                } else {
                    openBank();
                }
            }
        }
    }

    /**
     * @return Whether or not the spell is selected.
     */
    public boolean spellSelected() {
        SlotAction action = ActionBar.getFirstAction(makeLeatherAction);

        if (action != null) {
            if (action.isSelected()) {

                return true;
            } else {
                Execution.delayUntil(() -> action.isSelected(), 0, 500);
            }
        }

        return false;
    }

    /**
     * Check if an interface with a specific text is visible.
     */

    public boolean interfaceTextIsVisible(String text) {
        InterfaceComponent x = Interfaces.newQuery().texts(text).visible().results().first();
        return x != null && x.isValid() && x.isVisible();
    }

    /**
     * Check if the inventory contains at least 5 hides.
     */
    private boolean gotHides() {
        return (Inventory.contains(dragonHide) && Inventory.getQuantity(dragonHide) >= 5);
    }

    /**
     * Check if the inventory contains at least 5 tanned hides.
     */
    private boolean gotLeather() {
        return (Inventory.contains(tannedHide) && Inventory.getQuantity(tannedHide) >= 5);
    }

    /**
     * Select the Make Leather spell from the ability bar.
     */
    private boolean selectSpell() {
        SlotAction action = ActionBar.getFirstAction(makeLeatherAction);

        if (action != null) {
            status = "Activating Make Leather.";
            if (Keyboard.typeKey(action.getSlot().getKeyBind())) {
                if (!action.isSelected()) {
                    Execution.delayUntil(() -> action.isSelected(), 0, 500);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Click the dragon hide in the inventory.
     */
    private boolean clickHide() {
        final SpriteItem hide = Inventory.getItems(dragonHide).random();
        if (hide != null) {
            status = "Interacting Make Leather.";
            if (hide.interact("Cast")) {
                Execution.delayUntil(() -> interfaceTextIsVisible("Tan"), 500, 1000);
            }
            return true;
        }
        return false;
    }

    /**
     * Presses space bar.
     */
    private boolean pressSpacebar() {
        status = "Pressing spacebar";
        if (Keyboard.typeKey(" ")) {
            if (interfaceTextIsVisible("Tan")) {
                status = "Tanning hides.";
                Execution.delayUntil(() -> !interfaceTextIsVisible("Tan"), 0, 500);
            }
            return true;
        }

        return false;
    }

    /**
     * Open the bank.
     */
    private boolean openBank() {
        status = "Opening the bank.";
        if (!Bank.isOpen()) {
            if (Bank.open()) {
                Execution.delayUntil(() -> Bank.isOpen(), 500);
            }

            return true;
        }
        return false;
    }

    /**
     * Perform quick banking with a bank preset.
     */
    private boolean performBankPreset() {
        InterfaceComponent component = Interfaces.getAt(762, 41);

        if (component != null && component.isVisible()) {
            if (component.click()) {
                status = "Performing bank preset";
                Execution.delayUntil(() -> !Bank.isOpen(), 0, 1000);
                return true;
            }
        }

        return false;
    }

    /**
     * Method to return hourly experience.
     *
     * @param amount  The amount of experience.
     * @param elapsed The elapsed time.
     * @return Returns experience per hour.
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
        if (arg0.getItem().getDefinition().getName().equals(tannedHide)) {
            hidesTanned++;
        }
    }

    private void updateDatabase(int userId, String userName, long time, int exp, int profit, int hidesTanned) {

        System.out.println("--- Inserting data into the database ---");
        System.out.println("1. Runtime: " + time);
        System.out.println("2. Experience: " + exp);
        System.out.println("3. Profit made: " + profit);
        System.out.println("4. Hides tanned: " + hidesTanned);

        try {
            String website = "http://erikdekamps.nl";
            URL submit = new URL(website + "/update?uid="+userId+"&username="+userName+"&runtime="+(time/1000)+"&exp="+exp+"&profit="+profit+"&hides="+hidesTanned);
            URLConnection connection = submit.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            final BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null) {
                if (rd.readLine().contains("success")) {
                    System.out.println("Successfully updated!");
                } else if (line.toLowerCase().contains("fuck off")) {
                    System.out.println("Something is fucked up, couldn't update!");
                }
                rd.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     * This is where we put everything that we want to draw to the screen
	 * Graphics2D is the class that contains all the paint methods
	 */
    @Override
    public void onPaint(Graphics2D g) {
        int x = 5, y = 15;

        xpGained = Skill.MAGIC.getExperience() - startExp;

        runeCostPerHide = ((2 * bodyRunePrice) / 5) - ((2 * astralRunePrice) / 5);
        profitPerHide = leatherPrice - hidePrice - runeCostPerHide;
        profitMade = profitPerHide * hidesTanned;

        g.drawString("Version " + getMetaData().getVersion(), x, y);
        g.drawString("Run time: " + runtime.getRuntimeAsString(), x, y + 15);
        g.drawString("Status: " + status, x, y + 30);
        g.drawString("Exp gained: " + formatNumber(xpGained) + " (" + formatNumber(getHourly(xpGained, runtime.getRuntime())) + ")", x, y + 45);
        g.drawString("Hides tanned: " + formatNumber(hidesTanned) + " (" + formatNumber(getHourly(hidesTanned, runtime.getRuntime())) + ")", x, y + 60);
        g.drawString("Profit made: " + formatNumber(profitMade) + " (" + formatNumber(getHourly(profitMade, runtime.getRuntime())) + ")", x, y + 75);
    }
}