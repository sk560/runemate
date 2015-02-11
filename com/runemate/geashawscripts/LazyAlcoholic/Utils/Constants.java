package com.runemate.geashawscripts.LazyAlcoholic.Utils;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.StopWatch;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class Constants {

    public static String status = "Loading...";
    public static String wine = "Jug of wine";
    public static String drinkAction = "Jug of wine";

    public static int jugPrice, winePrice, wineDrunk, profitPerJug, profitMade;

    public static final StopWatch runtime = new StopWatch();

    public static int startX, startY = 0;
    public static int relativeX, relativeY;
    public static int paintWidth = 200;
    public static int paintHeight = 95;
    public static int userCoverWith = 100;
    public static int userCoverHeight = 19;
    public static boolean isMouseDown = false;

    public final static Player player = Players.getLocal();

}
