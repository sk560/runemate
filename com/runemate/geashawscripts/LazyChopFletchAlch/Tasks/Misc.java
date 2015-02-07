package com.runemate.geashawscripts.LazyChopFletchAlch.Tasks;

import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.framework.task.Task;

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
        double random = Random.nextDouble(0.330, 0.360);
        Camera.setPitch(random);
    }
}
