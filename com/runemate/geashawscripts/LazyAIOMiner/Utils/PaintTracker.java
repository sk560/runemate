package com.runemate.geashawscripts.LazyAIOMiner.Utils;

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.hybrid.util.calculations.CommonMath;
import com.runemate.geashawscripts.LazyAIOMiner.LazyAIOMiner;

import java.awt.*;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by Geashaw on 3-3-2015.
 */
public class PaintTracker {

    public static void drawRotatingMouse(Graphics2D g) {
        final Color MOUSE_COLOR = new Color(139, 69, 19);
        final Color MOUSE_BORDER_COLOR = new Color(0, 153, 0);
        final Color MOUSE_CENTER_COLOR = new Color(139, 69, 19);

        Point p = Mouse.getPosition();
        Graphics2D spinG = (Graphics2D) g.create();
        Graphics2D spinGRev = (Graphics2D) g.create();
        Graphics2D spinG2 = (Graphics2D) g.create();
        spinG.setColor(MOUSE_BORDER_COLOR);
        spinGRev.setColor(MOUSE_COLOR);
        spinG.rotate(System.currentTimeMillis() % 2000d / 2000d * (360d) * 2
                * Math.PI / 180.0, p.x, p.y);
        spinGRev.rotate(System.currentTimeMillis() % 2000d / 2000d * (-360d)
                * 2 * Math.PI / 180.0, p.x, p.y);
        final int outerSize = 20;
        final int innerSize = 12;
        spinG.setStroke((Stroke) new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        spinGRev.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        spinG.drawArc(p.x - (outerSize / 2), p.y - (outerSize / 2), outerSize,
                outerSize, 100, 75);
        spinG.drawArc(p.x - (outerSize / 2), p.y - (outerSize / 2), outerSize,
                outerSize, -100, 75);
        spinGRev.drawArc(p.x - (innerSize / 2), p.y - (innerSize / 2),
                innerSize, innerSize, 100, 75);
        spinGRev.drawArc(p.x - (innerSize / 2), p.y - (innerSize / 2),
                innerSize, innerSize, -100, 75);
        g.setColor(MOUSE_CENTER_COLOR);
        g.fillOval(p.x, p.y, 2, 2);
        spinG2.setColor(MOUSE_CENTER_COLOR);
        spinG2.rotate(System.currentTimeMillis() % 2000d / 2000d * 360d
                * Math.PI / 180.0, p.x, p.y);
        spinG2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        spinG2.drawLine(p.x - 5, p.y, p.x + 5, p.y);
        spinG2.drawLine(p.x, p.y - 5, p.x, p.y + 5);
    }

    public static void drawDraggablePaint(Graphics2D g) {

        int TextXLocation = LazyAIOMiner.startX + 5;
        int TextYLocation = LazyAIOMiner.startY + 5;

        g.setColor(new Color(51, 102, 255, 155));
        g.fillRect(LazyAIOMiner.startX + 1, LazyAIOMiner.startY + 1, LazyAIOMiner.paintWidth, LazyAIOMiner.paintHeight);
        g.fillRect(LazyAIOMiner.startX + 1, LazyAIOMiner.startY + 1, LazyAIOMiner.paintWidth, LazyAIOMiner.paintHeight);
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(1));
        g.drawRect(LazyAIOMiner.startX + 1, LazyAIOMiner.startY + 1, LazyAIOMiner.paintWidth, LazyAIOMiner.paintHeight);
        g.setFont(new Font("Tahoma", 0, 12));
        g.setColor(Color.white);

        g.drawString("Run time: " + LazyAIOMiner.runtime.getRuntimeAsString(), TextXLocation, TextYLocation += 10);
        g.drawString("Status: " + LazyAIOMiner.status, TextXLocation, TextYLocation += 15);
        g.drawString("Mined: " + displayFormattedPerHour(LazyAIOMiner.oresMined, LazyAIOMiner.runtime), TextXLocation, TextYLocation += 15);
        g.drawString("Level: " + getCurrentLevel() + " (+" + getGainedLevels() + ")", TextXLocation, TextYLocation += 15);
        g.drawString("Experience: " + displayFormattedPerHour(getExpGained(), LazyAIOMiner.runtime), TextXLocation, TextYLocation += 15);
        g.drawString("Next level: " + displayFormatted(getExpTillLevel()) + " (" + formatTime(getTimeToNextLevel()) + ")", TextXLocation, TextYLocation += 15);

        //Username Coverupper
        //g.setColor(Color.BLACK);
        //g.fillRect(7, ClientUI.getFrame().getHeight() - 126, LazyAIOMiner.userCoverWith, LazyAIOMiner.userCoverHeight);
    }

    public static void drawPaint(Graphics2D g) {

        g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF));
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 338, 518, 164);
        g.setColor(new Color(40, 53, 147));
        g.fillRect(0, 338, 518, 70);
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", 1, 25));
        g.drawString("LAZY AIO MINER", 9, 370);
        g.setColor(new Color(255, 255, 255));
        g.fillRect(52, 382, 432, 102);
        g.setStroke(new BasicStroke(1));
        g.setColor(new Color(0, 0, 0));
        g.drawRect(52, 382, 432, 102);
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", 0, 12));
        g.drawString("Run time: " + LazyAIOMiner.runtime.getRuntimeAsString(), 68, 406);
        g.drawString("Next level: " + formatTime(getTimeToNextLevel()), 66, 422);
        g.drawString("Status: " + LazyAIOMiner.status, 83, 437);
        g.drawString("Mined: " + displayFormattedPerHour(LazyAIOMiner.oresMined, LazyAIOMiner.runtime), 85, 451);
        g.drawString("Level: " + getCurrentLevel() + " (+" + getGainedLevels() + ")", 88, 464);
        g.drawString("Exp: " + displayFormattedPerHour(getExpGained(), LazyAIOMiner.runtime), 97, 477);
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", 3, 12));
        g.drawString("By Geashaw", 407, 399);


    }

    public static String displayFormatted(int value) {
        NumberFormat numFormat = NumberFormat.getIntegerInstance();
        return numFormat.format(value);
    }

    public static String displayFormattedPerHour(int value, StopWatch runtime) {
        NumberFormat numFormat = NumberFormat.getIntegerInstance();
        return numFormat.format(value) + " (" + numFormat.format((int) CommonMath.rate(TimeUnit.HOURS, runtime.getRuntime(), value)) + ")";
    }

    public static int getCurrentLevel() {
        return Skill.MINING.getCurrentLevel();
    }

    public static int getGainedLevels() {
        return Skill.MINING.getCurrentLevel() - LazyAIOMiner.startLevel;
    }

    public static int getExpGained() {
        return Skill.MINING.getExperience() - LazyAIOMiner.startExp;
    }

    public static int getExpPerHour() {
        return (int) ((getExpGained()) * 3600000D / (System.currentTimeMillis() - LazyAIOMiner.startTime));
    }

    public static int getExpTillLevel() {
        return Skill.MINING.getExperienceToNextLevel();
    }

    public static long getTimeToNextLevel() {
        if (getExpGained() > 0) {
            return (long) (Skill.MINING.getExperienceToNextLevel() * 3600000D / getExpPerHour() - LazyAIOMiner.startExp);
        }
        return 0;
    }

    public static String formatTime(long time) {
        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time)
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                        .toHours(time)),
                TimeUnit.MILLISECONDS.toSeconds(time)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                        .toMinutes(time)));

        return hms;
    }

    public static void drawMouse(Graphics2D g) {
        final Point p = Mouse.getPosition();
        g.setColor(Color.RED);
        g.setColor(Color.BLUE);
        g.drawOval(p.x - 5, p.y - 5, 10, 10);
    }
}
