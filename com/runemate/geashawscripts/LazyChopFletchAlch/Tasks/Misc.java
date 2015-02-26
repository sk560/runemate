package com.runemate.geashawscripts.LazyChopFletchAlch.Tasks;

import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.Methods;

/**
 * Created by Geashaw on 7-2-2015.
 */
public class Misc extends Task {
    @Override
    public boolean validate() {
        return Camera.getPitch() > 0.33;
    }

    @Override
    public void execute() {
        Methods.debug("Executing the Misc Task.");
        Camera.passivelyTurnTo(0, 0.330, 0.360);
    }
}
