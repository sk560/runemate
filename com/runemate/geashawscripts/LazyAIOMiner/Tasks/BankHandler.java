package com.runemate.geashawscripts.LazyAIOMiner.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
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
        return Inventory.isFull() || Inventory.isEmpty();
    }

    @Override
    public void execute() {
//Methods.debug("Executing bank handler");

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
                } else {
                    walkToMiningSpot();
                }
            }
        } else if (Methods.atMiningSpot()) {
            if (Inventory.isFull()) {
                walkToBank();
            }
        } else {
            if (Inventory.isFull()) {
                walkToBank();
            } else {
                walkToMiningSpot();
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

    private boolean walkToBank() {

        Coordinate location = LazyAIOMiner.bankArea.getCenter();
        WebPath bankPath = Traversal.getDefaultWeb().getPathBuilder().buildTo(location);

        if (bankPath != null) {
            LazyAIOMiner.status = "Walking to bank";
            return bankPath.step();
        }

        return false;
    }

    private boolean walkToMiningSpot() {

        Coordinate location = LazyAIOMiner.mineArea.getCenter();
        WebPath minePath = Traversal.getDefaultWeb().getPathBuilder().buildTo(location);

        if (minePath != null) {
            LazyAIOMiner.status = "Walking to mining spot";
            return minePath.step();
        }

        return false;
    }

}
