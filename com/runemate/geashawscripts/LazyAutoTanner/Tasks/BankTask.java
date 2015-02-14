package com.runemate.geashawscripts.LazyAutoTanner.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAutoTanner.Constants;

/**
 * Created by Geashaw on 6-2-2015.
 */
public class BankTask extends Task {

    @Override
    public boolean validate() {
        return gotAllLeather();
    }

    @Override
    public void execute() {
        if (Bank.isOpen()) {
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
                Constants.status = "Performing bank preset";
                Execution.delayUntil(() -> !Bank.isOpen(), 0, 1000);
                return true;
            }
        }

        return false;
    }

    /**
     * Check if the inventory contains at least 5 tanned hides.
     */
    private boolean gotAllLeather() {
        return (Inventory.contains(Constants.leather) && Inventory.getQuantity(Constants.leather) >= 23);
    }

    /**
     * Open the bank.
     */
    private boolean openBank() {
        Constants.status = "Opening the bank.";
        if (!Bank.isOpen()) {
            if (Bank.open()) {
                Execution.delayUntil(() -> Bank.isOpen(), 500);
            }

            return true;
        }
        return false;
    }
}