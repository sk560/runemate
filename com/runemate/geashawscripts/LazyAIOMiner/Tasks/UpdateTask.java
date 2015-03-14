package com.runemate.geashawscripts.LazyAIOMiner.Tasks;

import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAIOMiner.LazyAIOMiner;
import com.runemate.geashawscripts.LazyAIOMiner.Utils.PaintTracker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Geashaw on 13-3-2015.
 */
public class UpdateTask extends Task {

    @Override
    public boolean validate() {
        return LazyAIOMiner.updateTimer.getRuntime() > LazyAIOMiner.updateIntervalMilliSeconds;
    }

    @Override
    public void execute() {
        LazyAIOMiner.timeRanSoFar = (LazyAIOMiner.runtime.getRuntime() - LazyAIOMiner.lastRunTime);
        LazyAIOMiner.expGainedSoFar = PaintTracker.getExpGained() - LazyAIOMiner.lastExpGained;
        LazyAIOMiner.oresMinedSoFar = LazyAIOMiner.oresMined - LazyAIOMiner.lastOresMined;

        updateDatabase(LazyAIOMiner.userId, LazyAIOMiner.username, LazyAIOMiner.timeRanSoFar, LazyAIOMiner.expGainedSoFar, LazyAIOMiner.oresMinedSoFar);

        LazyAIOMiner.updateTimer.reset();

        // Reset xp gained, profit made and hides tanned.
        LazyAIOMiner.lastRunTime = LazyAIOMiner.runtime.getRuntime();
        LazyAIOMiner.lastExpGained = PaintTracker.getExpGained();
        LazyAIOMiner.lastOresMined = LazyAIOMiner.oresMined;
    }

    private void updateDatabase(int userId, String userName, long time, int exp, int ores) {

        System.out.println("--- Updating signature for " + userName + " ---");
        System.out.println("1. Runtime: " + time);
        System.out.println("2. Experience: " + exp);
        System.out.println("3. Ores mined: " + ores);

        try {
            String website = "http://erikdekamps.nl";
            URL submit = new URL(website + "/update?uid="+userId+"&username="+userName+"&runtime="+(time/1000)+"&exp="+exp+"&ores="+ores);
            URLConnection connection = submit.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            final BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null) {
                if (rd.readLine().contains("success")) {
                    System.out.println("Successfully updated!");
                } else if (line.toLowerCase().contains("fuck off")) {
                    System.out.println("Something is fucked up, couldn't update!");
                }
                rd.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
