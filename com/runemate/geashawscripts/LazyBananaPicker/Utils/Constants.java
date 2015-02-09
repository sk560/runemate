package com.runemate.geashawscripts.LazyBananaPicker.Utils;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.StopWatch;

import java.util.regex.Pattern;

/**
 * Created by Geashaw on 6-2-2015.
 */
public class Constants {

    public static final StopWatch runtime = new StopWatch();

    public static int startX, startY = 0;
    public static int relativeX, relativeY;
    public static int paintWidth = 215;
    public static int paintHeight = 95;
    public static int userCoverWith = 100;
    public static int userCoverHeight = 19;
    public static boolean isMouseDown = false;
    public final static Player player = Players.getLocal();

    public static String status = "Starting up...";
    public static final String banana = "Banana", basket = "Basket", filledBasket = "Bananas (5)", glory = "Amulet of glory";

    public static int bananaCount = 0, bananaBasketPrice = 0, profitMade = 0, startExp = 1;

    public static final Area KARAMJA_AREA = new Area.Rectangular(new Coordinate(2905, 3154), new Coordinate(2935, 3180, 0));
    public static final Area EDGEVILLE_AREA = new Area.Rectangular(new Coordinate(3082, 3487, 0), new Coordinate(3097, 3499, 0));
    public static Path walkToTree;

    public static final Pattern pattern = Pattern.compile("Amulet of glory \\(([2-4])\\)");
}
