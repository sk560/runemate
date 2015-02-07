package com.runemate.geashawscripts.LazyAutoTanner.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAutoTanner.Constants;

/**
 * Created by Geashaw on 6-2-2015.
 */
public class Bank extends Task {

    @Override
    public boolean validate() {
        return gotAllLeather();
    }

    @Override
    public void execute() {
        if (com.runemate.game.api.hybrid.local.hud.interfaces.Bank.isOpen()) {
            performBankPreset();
        } else {
            openBank();
        }
    }

    /**
     * Perform quick banking with a bank preset.
     */
    private boolean performBankPreset() {
        InterfaceComponent component = Interfaces.getAt(762, 41);

        if (component != null && component.isVisible()) {
            if (component.click()) {
                Constants.STATUS = "Performing bank preset";
                Execution.delayUntil(() -> !com.runemate.game.api.hybrid.local.hud.interfaces.Bank.isOpen(), 0, 1000);
                return true;
            }
        }

        return false;
    }

    /**
     * Check if the inventory contains at least 5 tanned hides.
     */
    private boolean gotAllLeather() {
        return (Inventory.contains(Constants.TANNED_HIDE) && Inventory.getQuantity(Constants.TANNED_HIDE) >= 23);
    }

    /**
     * Open the bank.
     */
    private boolean openBank() {
        Constants.STATUS = "Opening the bank.";
        if (!com.runemate.game.api.hybrid.local.hud.interfaces.Bank.isOpen()) {
            if (com.runemate.game.api.hybrid.local.hud.interfaces.Bank.open()) {
                Execution.delayUntil(() -> com.runemate.game.api.hybrid.local.hud.interfaces.Bank.isOpen(), 500);
            }

            return true;
        }
        return false;
    }
}