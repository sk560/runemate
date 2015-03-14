package com.runemate.geashawscripts.LazyAIOMiner.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAIOMiner.LazyAIOMiner;
import com.runemate.geashawscripts.LazyAIOMiner.Utils.Methods;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class BankHandler extends Task {

    @Override
    public boolean validate() {
        return Methods.atBank() && !Inventory.isEmpty();
    }

    @Override
    public void execute() {
        if (Methods.atBank()) {
            if (Inventory.isFull()) {
                if (!Bank.isOpen()) {
                    Bank.open();
                } else {
                    clickDepositAllButton();
                }
            } else {
                if (Bank.isOpen()) {
                    Bank.close();
                }
            }
        }
    }

    private boolean clickDepositAllButton() {
        InterfaceComponent button = Interfaces.getAt(12, 25);

        if (button != null) {
            if (button.isValid() && button.isVisible()) {
                LazyAIOMiner.status = "Depositing items";
                if (button.click()) {
                    Execution.delayUntil(() -> Inventory.isEmpty(), 1000, 1500);
                    return true;
                }
            }
        }
        return false;
    }

}
