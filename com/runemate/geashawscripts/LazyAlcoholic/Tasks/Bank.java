package com.runemate.geashawscripts.LazyAlcoholic.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAlcoholic.Utils.*;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class Bank extends Task {

    @Override
    public boolean validate() {
        return Inventory.getQuantity(Constants.wine) == 0;
    }

    @Override
    public void execute() {
        if (!com.runemate.game.api.hybrid.local.hud.interfaces.Bank.isOpen()) {
            openBank();
        } else {
            performBankPreset();
        }
    }
    /**
     * Perform quick banking with a bank preset.
     */
    private boolean performBankPreset() {
        InterfaceComponent component = Interfaces.getAt(762, 41);

        if (component != null && component.isVisible()) {
            if (component.click()) {
                com.runemate.geashawscripts.LazyAutoTanner.Constants.status = "Performing bank preset";
                Execution.delayUntil(() -> !com.runemate.game.api.hybrid.local.hud.interfaces.Bank.isOpen(), 0, 1000);
                return true;
            }
        }

        return false;
    }
    /**
     * Open the bank.
     */
    private boolean openBank() {
        Constants.status = "Opening the bank.";
        if (!com.runemate.game.api.hybrid.local.hud.interfaces.Bank.isOpen()) {
            if (com.runemate.game.api.hybrid.local.hud.interfaces.Bank.open()) {
                Execution.delayUntil(() -> com.runemate.game.api.hybrid.local.hud.interfaces.Bank.isOpen(), 500);
            }

            return true;
        }
        return false;
    }
}
