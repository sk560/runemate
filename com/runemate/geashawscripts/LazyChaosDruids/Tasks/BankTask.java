package com.runemate.geashawscripts.LazyChaosDruids.Tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChaosDruids.LazyChaosDruids;
import com.runemate.geashawscripts.LazyChaosDruids.Methods.Methods;

/**
 * Created by Geashaw on 18-2-2015.
 */
public class BankTask extends Task {

    @Override
    public boolean validate() {
        return !Methods.atDruids() && !Methods.canFight() && !Methods.gotFood() && !Methods.canHeal();
    }

    @Override
    public void execute() {
        if (!Methods.gotAllSupplies()) {
            if (Methods.atFaladorBank()) {
                if (Bank.isOpen()) {
                    if (Methods.gotLoot()) {
                        clickDepositAllButton();
                    } else if (!Methods.gotAllSupplies()) {
                        withdrawSupplies();
                    } else {
                        Bank.close();
                    }
                } else {
                    Bank.open();
                }
            } else {
                Methods.debug("Not at bank");
                walkToBank();
            }
        } else {
            if (Bank.isOpen()) {
                Bank.close();
            }
        }
    }

    /**
     * Walk to Falador west bank method.
     */
    private boolean walkToBank() {
        final Area FALADOR_BANK = new Area.Rectangular(new Coordinate(2943, 3367, 0), new Coordinate(2947, 3371, 0));
        return BresenhamPath.buildTo(FALADOR_BANK.getCenter()).step(true);
    }

    /**
     * Method to click the deposit all button.
     */
    private boolean clickDepositAllButton() {
        InterfaceComponent button = Interfaces.getAt(12, 25);

        if (button != null) {
            if (button.isValid() && button.isVisible()) {
                if (button.click()) {
                    Execution.delayUntil(() -> Inventory.isEmpty(), 1000, 1500);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to withdraw supplies.
     */
    private boolean withdrawSupplies() {
        if (!Methods.gotTeleportRunes()) {
            if (Bank.getQuantity("Law rune") > 1 && Bank.getQuantity("Air rune") > 3 && Bank.getQuantity("Water rune") > 1) {
                if (Bank.withdraw("Law rune", 1) && Bank.withdraw("Water rune", 1) && Bank.withdraw("Air rune", 3)) {
                    Execution.delayUntil(() -> Methods.gotTeleportRunes(), 1000, 1500);
                }
            }
        } else if (!Methods.gotAllFood()) {
            if (!Methods.gotAllFood()) {
                if (Bank.getQuantity(LazyChaosDruids.foodName) > LazyChaosDruids.foodAmount) {
                    if (Bank.withdraw(LazyChaosDruids.foodName, LazyChaosDruids.foodAmount)) {
                        Execution.delayUntil(() -> Methods.gotFood(), 1000, 1500);
                    }
                }
            }
        } else {
            return true;
        }
        return false;
    }


}
