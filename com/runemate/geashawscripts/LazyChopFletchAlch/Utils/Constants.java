package com.runemate.geashawscripts.LazyChopFletchAlch.Utils;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.StopWatch;

/**
 * Created by Deka on 6-2-2015.
 */
public class Constants {

    public static final String logs = "Yew logs";
    public static final String shieldBow = "Yew shieldbow (u)";

    public final static Player player = Players.getLocal();
    public static final StopWatch runtime = new StopWatch();

    public static String status = "Loading...";
    public static String spell = "High Level Alchemy";
    public static String toolInterfaceText = "What do you want to use on the logs?";
    public static String fletchInterfaceText = "Yew shieldbow";

    public static int startX, startY = 0;
    public static int relativeX, relativeY;
    public static int paintWidth = 200;
    public static int paintHeight = 95;
    public static int userCoverWith = 150;
    public static int userCoverHeight = 19;

    public static boolean isMouseDown = false;

    public static int bowsAlched = 0;
    public static int bowsFletched = 0;
    public static int logsChopped = 0;
    public static int naturePrice, shieldbowPrice;
}