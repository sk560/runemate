package com.runemate.geashawscripts.LazyBananaPicker.Tasks;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyBananaPicker.Constants;
import com.runemate.geashawscripts.LazyBananaPicker.Methods;

/**
 * Created by Geashaw on 9-2-2015.
 */
public class Bank extends Task {

    @Override
    public boolean validate() {
        Npc banker = Npcs.newQuery().names("Banker").results().nearest();
        return Methods.gotFilledBaskets() && Methods.atEdgeville() && banker != null;
    }

    @Override
    public void execute() {
        Npc banker = Npcs.newQuery().names("Banker").results().nearest();

        if (banker.isVisible()) {
            if (com.runemate.game.api.hybrid.local.hud.interfaces.Bank.isOpen()) {
                if (!Equipment.newQuery().names(Constants.pattern).results().isEmpty()) {
                    performBankPreset();
                }
                else {
                    equipGlory();
                }
            } else {
                openBank();
            }
        } else {
            Camera.setPitch(0.234);
            Camera.turnTo(banker);
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
                Methods.debug("Performing bank preset");
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
        Methods.debug("Opening the bank");
        if (com.runemate.game.api.hybrid.local.hud.interfaces.Bank.open()) {
            if (!com.runemate.game.api.hybrid.local.hud.interfaces.Bank.isOpen()) {
                Execution.delayUntil(() -> com.runemate.game.api.hybrid.local.hud.interfaces.Bank.isOpen(), 500);
                return true;
            }
        }
        return false;
    }

    /**
     * Equip a glory.
     * Credits to Aidden.
     */
    private void equipGlory() {
        final SpriteItem glory = com.runemate.game.api.hybrid.local.hud.interfaces.Bank.newQuery().names(Constants.pattern).results().first();
        if (glory != null) {
            Constants.status = "Equiping glory";

            final int count = glory.getQuantity();
                if (glory.interact("Wear")) {
                    Execution.delayUntil(() -> glory.getQuantity() != count, 1000);
                }
        }
    }
}
