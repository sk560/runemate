package com.runemate.geashawscripts.LazyChopFletchAlch.Utils;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.StopWatch;

/**
 * Created by Deka on 6-2-2015.
 */
public class Constants {
    public final static Player player = Players.getLocal();
    public static Path walkToTree;

    public static final Area TREE_AREA = new Area.Rectangular(new Coordinate(2996, 3312, 0), new Coordinate(3021, 3326, 0));
    public static final StopWatch runtime = new StopWatch();

    public static String status = "Loading...";
    public static String toolInterfaceText = "What do you want to use on the logs?";
    public static String fletchInterfaceText = "Yew shieldbow";

    public static int startX, startY = 0;
    public static int relativeX, relativeY;
    public static int paintWidth = 200;
    public static int paintHeight = 95;
    public static int userCoverWith = 100;
    public static int userCoverHeight = 19;

    public static boolean isMouseDown = false;


}