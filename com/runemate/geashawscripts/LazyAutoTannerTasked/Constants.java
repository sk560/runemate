package com.runemate.geashawscripts.LazyAutoTannerTasked;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.StopWatch;

/**
 * Created by Geashaw on 6-2-2015.
 */
public class Constants {
    public static String STATUS = "Loading...";
    public static String USERNAME = "Loading...";
    public static final String MAKE_LEATHER_ACTION = "Make Leather";
    public static final String DRAGON_HIDE = "Green dragonhide";
    public static final String TANNED_HIDE = "Green dragon leather";

    public static final StopWatch runtime = new StopWatch();
    public static StopWatch updateTimer = new StopWatch();
    public static final int updateIntervalMilliSeconds = 60000;

    public static long timeRanSoFar, lastRunTime;

    public static int startExp = -1, xpGained = 0, expGainedSoFar = 0, lastExpGained = 0;
    public static int hidesTanned = 0, hidesTannedSoFar = 0, lastHidesTanned = 0;
    public static int profitMade, profitMadeSoFar = 0, lastProfitMade = 0;
    public static int hidePrice, leatherPrice, bodyRunePrice, astralRunePrice, runeCostPerHide, profitPerHide;
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
