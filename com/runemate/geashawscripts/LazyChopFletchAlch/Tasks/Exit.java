package com.runemate.geashawscripts.LazyChopFletchAlch.Tasks;

import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyChopFletchAlch.Utils.Methods;

/**
 * Created by Geashaw on 7-2-2015.
 */
public class Exit extends Task {

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {
        Methods.debug("Executing the Exit Task.");
    }
}