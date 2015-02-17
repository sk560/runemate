package com.runemate.geashawscripts.LazyChaosDruids.Utilities;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author: Supreme Leader
 */
public class ExpTrackerContainer {

    private final ArrayList<ExpTracker> trackers;

    public ExpTrackerContainer () {
        trackers = new ArrayList<ExpTracker>();
    }

    public ExpTrackerContainer (ExpTracker... skills) {
        this();
        add(skills);
    }

    public void add(ExpTracker... skills) {
        for (ExpTracker skill: skills) {
            if (!trackers.contains(skill)) {
                trackers.add(skill);
            }
        }
    }

    public void remove(ExpTracker... skills) {
        for (ExpTracker skill: skills) {
            if (trackers.contains(skill)){
                trackers.remove(skill);
            }
        }
    }

    public void draw (Graphics2D g, int x, int y) {
        int pos = 0;
        for (ExpTracker tracker: trackers) {
            if (tracker.getExpPerHour() > 1000) {
                tracker.drawProgressBar(g, x, y + (pos* 20));
                pos++;
            }
        }
    }
}