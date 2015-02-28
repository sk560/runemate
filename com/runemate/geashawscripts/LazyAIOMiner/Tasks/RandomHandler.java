package com.runemate.geashawscripts.LazyAIOMiner.Tasks;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

/**
 * Created by Geashaw on 18-2-2015.
 */
public class RandomHandler extends Task {

    public static final String[] RANDOMS = {"Genie", "Mysterious Old Man", "Drunken Dwarf", "Frog", "Rick Turpentine", "Sergeant Damien", "Pillory Guard", "Capt' Arnav", "Flippa", "Evil Bob", "Giles", "Leo", "Dunce", "Sandwich lady"};

    private Npc random;

    @Override
    public boolean validate() {
        return (random = Npcs.newQuery().names(RANDOMS).targeting(Players.getLocal()).reachable().results().first()) != null && random.isVisible();
    }

    @Override
    public void execute() {
        if (random != null && random.interact("Dismiss")) {
            Execution.delayUntil(() -> !random.isVisible(), 1000, 1500);
        }
    }
}
