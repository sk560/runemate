package com.runemate.geashawscripts.LazyGildedAltar;

import com.runemate.game.api.client.paint.PaintListener;
import com.runemate.game.api.hybrid.entities.Actor;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.Skill;
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
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.LoopingScript;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;

import java.awt.*;
import java.text.NumberFormat;

public class LazyGildedAltar extends LoopingScript implements PaintListener, InventoryListener {

    /**
     * 1. Teleport to own house.
     * 2. Open door to Gilded Altar.
     * 3. Light first Incence Burner.
     * 4. Light second Incence Burner.
     * 5. Right click altar and click offer.
     * 6. After 15 bones use keybind for take BoB.
     * 7. After 15 bones use keybind for take BoB.
     * 8. Use keybind for teleport to Fight Cave.
     * 9. Open bank and use preset.
     * 10. Open bank again and withdraw x to BoB
     * 11. Type 111 or something.
     * 12. Close bank.
     */

    private String status = "Loading...", bones = "Dragon bones", marrentill = "Clean marrentill";
    private int startXp = -1, xpGained, bonesOffered;
    private final StopWatch runtime = new StopWatch();

    final Area fightCaveArea = new Area.Rectangular(new Coordinate(4606, 5125, 0), new Coordinate(4618, 5136, 0));
    final Player me = Players.getLocal();
    private Path walkToGildedAltar, walkToBanker;

    @Override
    public void onStart(String... args) {
        // Add the listener for the paint.
        getEventDispatcher().addListener(this);
        runtime.start();
        setLoopDelay(100, 200);
        debug("Release test.");
    }

    @Override
    public void onLoop() {

        if (startXp == -1 && Skill.CONSTITUTION.getExperience() > 0) {
            runtime.start();
            startXp = Skill.PRAYER.getExperience();
        }

        if (atFightCaveBanker()) {
            if (canTeleportToHouse()) {
                teleportToHouse();
            } else {
                if (Bank.isOpen()) {
                    performBankPreset();
                } else {
                    openBank();
                }
            }
        } else if (atYourPortal()) {
            walkToGildedAltar();
        } else if (atGildedAltar()) {
            if (canLightFirstBurner()) {
                lightFirstBurner();
            }
            if (canLightSecondBurner()) {
                lightSecondBurner();
            }
            if (canOfferBones()) {
                offerBones();
            } else {
                if (canTeleportToFightCaves()) {
                    teleportToFightCaves();
                }
            }
        }
    }

    /**
     * Check if you're at the fight cave banker area.
     */
    private boolean atFightCaveBanker() {
        return fightCaveArea.contains(me);
    }

    /**
     * Checks if player can teleport to house.
     */
    private boolean canTeleportToHouse() {
        return Inventory.getQuantity("Teleport to house") >= 1
                && Inventory.getQuantity(marrentill) == 2
                && Inventory.getQuantity(bones) == 25
                && atFightCaveBanker();
    }

    /**
     * Break house teleport tablet to teleport.
     */
    private boolean teleportToHouse() {
        SlotAction action = ActionBar.getFirstAction("Teleport to house");

        if (action.activate() && Execution.delayUntil(() -> Players.getLocal().getAnimationId() != -1, 3000)){
            status = "Breaking house teleport tablet.";
            Execution.delayUntil(() -> Players.getLocal().getAnimationId() == -1, 3000);

            return true;
        }
        return false;
    }

    /**
     * Check if you are inside your own house.
     */
    private boolean atYourPortal() {
        GameObject portal = GameObjects.newQuery().names("Portal").results().first();

        if (portal != null) {
            if (me.distanceTo(portal) < 4) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if you are inside your own house.
     */
    private boolean atGildedAltar() {
        GameObject altar = GameObjects.newQuery().names("Altar").actions("Offer").results().first();

        if (altar != null) {
            if (me.distanceTo(altar) < 5) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if you are inside your own house.
     */
    private boolean walkToGildedAltar() {
        GameObject altar = GameObjects.newQuery().names("Altar").actions("Offer").results().first();
        walkToGildedAltar = Traversal.getDefaultWeb().getPathBuilder().buildTo(altar);

        if (altar != null) {
            status = "Walking to altar.";
            if (walkToGildedAltar.step(true)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if you can light the first burner.
     */
    private boolean canLightFirstBurner() {
        GameObject portal = GameObjects.newQuery().names("Portal").results().first();
        GameObject burner1 = GameObjects.newQuery().names("Incense burner").actions("Light").results().nearestTo(portal);

        if (burner1 != null) {
            if (!burner1.getDefinition().getActions().contains("Re-Light")) {
                if (Inventory.getQuantity(marrentill) >= 1) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if you can light the second burner.
     */
    private boolean canLightSecondBurner() {
        GameObject portal = GameObjects.newQuery().names("Portal").results().first();
        GameObject burner2 = GameObjects.newQuery().names("Incense burner").actions("Light").results().nearestTo(portal);

        if (burner2 != null) {
            if (!burner2.getDefinition().getActions().contains("Re-Light")) {
                if (Inventory.getQuantity(marrentill) >= 1) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Light the first burner.
     */
    private boolean lightFirstBurner() {
        GameObject portal = GameObjects.newQuery().names("Portal").results().first();
        GameObject burner1 = GameObjects.newQuery().names("Incense burner").actions("Light").results().nearestTo(portal);

        if (burner1 != null) {
            if (!burner1.getDefinition().getActions().contains("Re-light")) {
                status = "Lighting first burner.";
                Camera.turnTo(burner1);
                if (burner1.interact("Light")) {
                    Execution.delayUntil(() -> burner1.getDefinition().getActions().contains("Re-light"), 1500, 2500);

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Light the second burner.
     */
    private boolean lightSecondBurner() {
        GameObject portal = GameObjects.newQuery().names("Portal").results().first();
        GameObject burner2 = GameObjects.newQuery().names("Incense burner").actions("Light").results().furthestFrom(portal);

        if (burner2 != null) {
            if (!burner2.getDefinition().getActions().contains("Re-light")) {
                status = "Lighting second burner.";
                Camera.turnTo(burner2);

                if (burner2.interact("Light")) {
                    Execution.delayUntil(() -> burner2.getDefinition().getActions().contains("Re-light"), 1500, 2500);

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if the player is ready to start offering bones.
     */
    private boolean canOfferBones() {
        return Inventory.getQuantity(bones) >= 25;
    }

    /**
     * Check if the player actually has any bones.
     */
    private boolean gotBones() {
        return Inventory.contains(bones);
    }

    /**
     * Interact with the altar to offer bones.
     */
    private boolean offerBones() {
        GameObject altar = GameObjects.newQuery().names("Altar").actions("Offer").results().first();

        if (altar != null) {
            status = "Offering bones.";
            if (gotBones()) {
                if (altar.interact("Offer")) {
                    Execution.delay(2000, 3000);
                }

                return true;
            }
        }
        return false;
    }

    /**
     * Check if the player can teleport to fight caves.
     */
    private boolean canTeleportToFightCaves() {
        return Inventory.getQuantity(bones) == 0;
    }

    /**
     * Teleport to fight caves using ring.
     */
    private boolean teleportToFightCaves() {
        SlotAction action = ActionBar.getFirstAction("TokKul-Zo (Charged)");

        if (action != null) {
            status = "Activating TokKul-Zo.";
            if (action.activate()) {
                if (teleportOptionIsVisible()) {
                    // A little extra delay.
                    status = "Selecting Fight Cave teleport.";
                    if (Keyboard.pressKey(51)) {
                        // A little extra delay.
                        Execution.delay(3000, 4000);
                        if (!atFightCaveBanker()) {
                            Execution.delayUntil(() -> atFightCaveBanker(), 0, 500);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Open the bank.
     */
    private boolean openBank() {
        status = "Opening the bank.";

        Npc banker = Npcs.newQuery().names("TzHaar-Mej-Jal").results().nearest();

        if (banker != null) {
            if (!Bank.isOpen()) {
                if (!banker.isVisible()) {
                    if (banker.distanceTo(me) > 5) {
                        walkToBanker = Traversal.getDefaultWeb().getPathBuilder().buildTo(banker);
                        if (walkToBanker.step(true)) {
                            Execution.delayUntil(() -> banker.distanceTo(me) < 4, 500, 1000);
                        }
                    } else {
                        if (Camera.turnTo(banker)) {
                            Execution.delayUntil(() -> banker.isVisible(), 500, 1000);
                        }
                    }
                } else {
                    if (Bank.open()) {
                        Execution.delayUntil(() -> Bank.isOpen(), 500, 1000);
                        return true;
                    }
                }
            }
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
     * Check if the teleport interface is visible.
     */
    private boolean teleportOptionIsVisible() {
        InterfaceComponent component = Interfaces.newQuery().texts("Fight Cave.").visible().results().first();
        return (component != null && component.isValid());
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
     * Replace System.out.println(text);
     *
     * @param text The text to send to the console.
     */
    public void debug(String text) {
        System.out.println(text);
    }

    private boolean isBusy(final Actor player) {
        return player.isMoving() || player.getAnimationId() != -1 || player.getAnimationId() != 24897;
    }

    /**
     * Count items that are added to inventory.
     */
    @Override
    public void onItemRemoved(ItemEvent arg0) {
        if (!atFightCaveBanker()) {
            if (arg0.getItem().getDefinition().getName().equals(bones)) {
                bonesOffered++;
            }
        }
    }

    @Override
    public void onPaint(Graphics2D g) {
        int x = 5, y = 15;

        xpGained = Skill.PRAYER.getExperience() - startXp;

        g.drawString("Version " + getMetaData().getVersion(), x, y);
        g.drawString("Run time: " + runtime.getRuntimeAsString(), x, y + 15);
        g.drawString("Status: " + status, x, y + 30);
        g.drawString("Exp gained: " + formatNumber(xpGained) + " (" + formatNumber(getHourly(xpGained, runtime.getRuntime())) + ")", x, y + 45);
        g.drawString("Bones offered: " + formatNumber(bonesOffered) + " (" + formatNumber(getHourly(bonesOffered, runtime.getRuntime())) + ")", x, y + 60);
    }

    @Override
    public void onPause() {
        runtime.stop();
    }

    @Override
    public void onResume() {
        runtime.start();
    }
}