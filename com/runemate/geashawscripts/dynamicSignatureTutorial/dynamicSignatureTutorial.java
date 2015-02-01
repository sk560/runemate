package com.runemate.geashawscripts.dynamicSignatureTutorial;

//Imports are all the classes that we are going to use methods from

import com.runemate.game.api.hybrid.RuneScape;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.script.framework.LoopingScript;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class dynamicSignatureTutorial extends LoopingScript  {

    private String userName;

    private final StopWatch runtime = new StopWatch();
    private StopWatch updateTimer = new StopWatch();
    private final int updateInterval = 15000;

    private long timeRanSoFar, lastRunTime;

    private int xpGained = 0, expGainedSoFar = 0, lastExpGained,
                hidesTanned = 0, hidesTannedSoFar = 0, lastHidesTanned = 0,
                profitMade = 0, profitMadeSoFar = 0, lastProfitMade = 0,
                userId;

    @Override
    public void onStart(String... args) {
        // Getting the forum data.
        userId = com.runemate.game.api.hybrid.Environment.getForumId();
        userName = com.runemate.game.api.hybrid.Environment.getForumName();
        // Starting both timers.
        runtime.start();
        updateTimer.start();
        // Standard loop delay little slower than normal.
        setLoopDelay(100, 200);
    }

    @Override
    public void onPause() {
        runtime.stop();
    }

    @Override
    public void onResume() {
        runtime.start();
    }

    @Override
    public void onLoop() {

        // Check if the user is logged in.
        if (RuneScape.isLoggedIn()) {

            // Update the database.
            if (updateTimer.getRuntime() > updateInterval) {

                timeRanSoFar = (runtime.getRuntime() - lastRunTime);
                expGainedSoFar = xpGained - lastExpGained;
                profitMadeSoFar = profitMade - lastProfitMade;
                hidesTannedSoFar = hidesTanned - lastHidesTanned;

                updateDatabase(userId, userName, timeRanSoFar, expGainedSoFar, profitMadeSoFar, hidesTannedSoFar);

                updateTimer.reset();

                // Reset xp gained, profit made and hides tanned.
                lastRunTime = runtime.getRuntime();
                lastExpGained = xpGained;
                lastProfitMade = profitMade;
                lastHidesTanned = hidesTanned;
            }
        }
    }

    private void updateDatabase(int userId, String userName, long time, int exp, int profit, int hidesTanned) {

        System.out.println("--- Inserting data into the database ---");
        System.out.println("1. Runtime: " + time);
        System.out.println("2. Experience: " + exp);
        System.out.println("3. Profit made: " + profit);
        System.out.println("4. Hides tanned: " + hidesTanned);

        try {
            String website = "http://example.com";
            URL submit = new URL(website + "/update?uid="+userId+"&username="+userName+"&runtime="+(time/1000)+"&exp="+exp+"&profit="+profit+"&hides="+hidesTanned);
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