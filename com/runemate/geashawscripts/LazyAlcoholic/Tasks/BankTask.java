package com.runemate.geashawscripts.LazyAlcoholic.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.*;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAlcoholic.Utils.*;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class BankTask extends Task {

    @Override
    public boolean validate() {
        return gotAllJugs();
    }

    @Override
    public void execute() {
        if (Bank.isOpen()) {
            Methods.debug("Bank is open");
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

    /**
     * Check if full inventory of empty jugs.
     */
    private boolean gotAllJugs() {

        if (Inventory.containsAllOf("Jug")) {
            return true;
        }

        return false;
    }
}
