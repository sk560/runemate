package com.runemate.geashawscripts.LazyAutoTanner.Tasks;

import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAutoTanner.Constants;
import com.runemate.geashawscripts.LazyAutoTanner.Methods;

/**
 * Created by Deka on 6-2-2015.
 */
public class Update extends Task {

    @Override
    public boolean validate() {
        return Constants.updateTimer.getRuntime() > Constants.updateIntervalMilliSeconds;
    }

    @Override
    public void execute() {
        Constants.timeRanSoFar = (Constants.runtime.getRuntime() - Constants.lastRunTime);
        Constants.expGainedSoFar = Constants.xpGained - Constants.lastExpGained;
        Constants.profitMadeSoFar = Constants.profitMade - Constants.lastProfitMade;
        Constants.hidesTannedSoFar = Constants.hidesTanned - Constants.lastHidesTanned;

        Methods.updateDatabase(Constants.userId, Constants.username, Constants.timeRanSoFar, Constants.expGainedSoFar, Constants.profitMadeSoFar, Constants.hidesTannedSoFar);

        Constants.updateTimer.reset();

        // Reset xp gained, profit made and hides tanned.
        Constants.lastRunTime = Constants.runtime.getRuntime();
        Constants.lastExpGained = Constants.xpGained;
        Constants.lastProfitMade = Constants.profitMade;
        Constants.lastHidesTanned = Constants.hidesTanned;
    }
}
