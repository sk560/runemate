package com.runemate.geashawscripts.LazyMudRuneCrusher.Tasks;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.*;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyMudRuneCrusher.LazyMudRuneCrusher;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class CrushTask extends Task {

    @Override
    public boolean validate() {
        return !Bank.isOpen() && Inventory.getQuantity("Mud rune") > 0;
    }

    @Override
    public void execute() {
        if (isBusy() || isCrushing()) {
            Execution.delayUntil(() -> !isBusy(), 1000);
        } else {
            if (Inventory.getQuantity("Mud rune") > 1) {
                if (interfaceTextIsVisible("Grind")) {
                    pressSpacebar();
                } else {
                    clickRune();
                }
            }
        }
    }

    /**
     * Click the dragon hide in the inventory.
     */
    private boolean clickRune() {
        final SpriteItem rune = Inventory.getItems("Mud rune").first();
        if (rune != null) {
            LazyMudRuneCrusher.status = "Clicking on mud rune.";
            if (rune.interact("Grind")) {
                Execution.delayUntil(() -> interfaceTextIsVisible("Grind"), 1500, 3000);
            }
            return true;
        }
        return false;
    }

    /**
     * Check if an interface with a specific text is visible.
     */
    public static boolean interfaceTextIsVisible(String text) {
        InterfaceComponent x = Interfaces.newQuery().textContains(text).results().first();
        return x != null && x.isValid() && x.isVisible();
    }

    /**
     * Presses Spacebar.
     */
    private boolean pressSpacebar() {
        LazyMudRuneCrusher.status = "Pressing spacebar.";
        if (Keyboard.typeKey(" ")) {
            LazyMudRuneCrusher.status = "Grinding runes";
            Execution.delayUntil(() -> !interfaceTextIsVisible("Grind"), 1500, 3000);
        }
        return false;
    }

    /**
     * Checks if the player is busy.
     */
    public static boolean isBusy() {
        return Players.getLocal().isMoving() || Players.getLocal().getAnimationId() != -1;
    }

    private boolean isCrushing() {

        return Players.getLocal().getAnimationId() == 22757;
    }
}
