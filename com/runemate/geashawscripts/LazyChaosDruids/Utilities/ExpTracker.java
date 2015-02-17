package com.runemate.geashawscripts.LazyChaosDruids.Utilities;

import com.runemate.game.api.hybrid.local.Skill;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author: Supreme Leader
 */
public class ExpTracker {

    private final Skill skill;
    private final long startTime;
    private final int startExp;
    private final Color borderColor, backgroundColor, foregroundColor, textColor;

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.0");
    private static final Font FONT = new Font("Arial", Font.PLAIN, 11);

    public ExpTracker (final Skill skill, final Color borderColor, final Color backgroundColor, final Color foregroundColor, final Color textColor) {
        this.skill = skill;
        this.startTime = System.currentTimeMillis();
        this.startExp = skill.getExperience();
        this.borderColor = borderColor;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.textColor = textColor;
    }

    public int getExpGained () {
        return skill.getExperience() - startExp;
    }

    public int getExpPerHour () {
        return perHour(getExpGained());
    }

    public long getTimeToNextLevel () {
        return (long) (skill.getExperienceToNextLevel() * 3600000D / getExpPerHour());
    }

    private int getBarWidth () {
        double tnl = (100 - skill.getPercentTowardsNextLevel()) * 0.01;
        return (int) (tnl * 250);
    }

    private String getFormattedExpPerHour () {
        double expPerHour = (double) getExpPerHour() / 1000;
        return DECIMAL_FORMAT.format(expPerHour) + "K";
    }

    private String getProgressBarInfo () {
        String info = skill.name() + ": " + formatNumber(getExpGained()) + " (" + getFormattedExpPerHour() + " P/H) - " + formatTime(getTimeToNextLevel());
        return info;
    }

    public void drawProgressBar (Graphics2D g, int x, int y) {
        g.setStroke(new BasicStroke(2));
        g.setColor(backgroundColor);
        g.fillRect(x, y, 250, 20);
        g.setColor(foregroundColor);
        g.fillRect(x, y, getBarWidth() ,20);
        g.setColor(borderColor);
        g.drawRect(x, y, 250, 20);
        g.setColor(textColor);
        g.setFont(FONT);
        FontMetrics fontMetrics = g.getFontMetrics(FONT);
        String info = getProgressBarInfo();
        int textX = 125 - (fontMetrics.stringWidth(info)/2);
        g.drawString(info, x + textX, y + 15);
    }

    private int perHour(int val) {
        return (int) ((val) * 3600000D / (System.currentTimeMillis() - startTime));
    }

    private String formatTime(long time) {
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

    private String formatNumber(int i) {
        return NumberFormat.getIntegerInstance().format(i);
    }

}