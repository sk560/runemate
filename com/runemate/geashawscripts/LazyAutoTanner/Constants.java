package com.runemate.geashawscripts.LazyAutoTanner;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.StopWatch;

/**
 * Created by Geashaw on 6-2-2015.
 */
public class Constants {

    // this class is created right away before they choose a hide, so the name you set here before is decided before it changes to their choice if you get me.
    // So how would I initiate that the correct way?
    // since you are using this class to store constants and not to instantiate it anyway you would just do it like this

    public static String status = "Loading...";
    public static String username = "Loading..."; // Lol wut?
    public static final String makeLeatherAction = "Make Leather";

    public static String hide = "";
    public static String leather = "";

    public static int hideId;
    public static int leatherId;

    public static final StopWatch runtime = new StopWatch();
    public static StopWatch updateTimer = new StopWatch();
    public static final int updateIntervalMilliSeconds = 60000;

    public static long timeRanSoFar, lastRunTime;

    public static int startExp = -1, xpGained = 0, expGainedSoFar = 0, lastExpGained = 0;
    public static int hidesTanned = 0, hidesTannedSoFar = 0, lastHidesTanned = 0;
    public static int profitMade = 0, profitMadeSoFar = 0, lastProfitMade = 0;
    public static int hidePrice = 0, leatherPrice = 0, runeCostPerHide = 0, bodyRunePrice = 0, astralRunePrice = 0, profitPerHide = 0;
    public static int userId;

    public static int startX, startY = 0;
    public static int relativeX, relativeY;
    public static int paintWidth = 200;
    public static int paintHeight = 95;
    public static int userCoverWith = 100;
    public static int userCoverHeight = 19;

    public final static Player player = Players.getLocal();

    public static boolean isMouseDown = false;
}
