package com.runemate.geashawscripts.LazyAIOMiner.Tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.basic.ViewportPath;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.queries.results.LocatableEntityQueryResults;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.Filter;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.geashawscripts.LazyAIOMiner.LazyAIOMiner;
import com.runemate.geashawscripts.LazyAIOMiner.Utils.Methods;

/**
 * Created by Geashaw on 11-2-2015.
 */
public class MineHandler extends Task {

    @Override
    public boolean validate() {
        if (LazyAIOMiner.powermine) {
            return !Methods.atBank() && Methods.atMine() && !gotOre() && !Inventory.isFull();
        } else {
            return !Methods.atBank() && Methods.atMine() && !Inventory.isFull();
        }
    }

    @Override
    public void execute() {
        if (LazyAIOMiner.powermine) {
            if (LazyAIOMiner.noMove) {
                if (!atSpot()) {
                    walkToSpot();
                } else {
                    if (!Methods.isBusy()) {
                        mineOres();
                    } else {
                        hoverInventoryItem();
                    }
                }
            } else {
                if (canClickRock()) {
                    if (!Methods.isBusy()) {
                        mineOres();
                    }
                } else {
                    walkToRocks();
                }
            }
        } else {
            if (!Methods.isBusy()) {
                if (canClickRock()) {
                    mineOres();
                } else {
                    walkToRocks();
                }
            } else {
                if (canClickRock()) {
                    hoverNextRock();
                } else {
                    walkToRocks();
                }
            }
        }
    }

    private boolean mineOres() {
        LocatableEntityQueryResults<GameObject> rocks = GameObjects.getLoaded(new Filter<GameObject>() {
            @Override
            public boolean accepts(GameObject gameObject) {
                return LazyAIOMiner.oreObjectIds.contains(gameObject.getId()) && LazyAIOMiner.mineArea.contains(gameObject) && !rockIsInUse(gameObject);
            }
        });

        if (rocks != null) {
            GameObject rock = rocks.sortByDistance().nearestTo(Players.getLocal());

            if (rock != null) {
                if (LazyAIOMiner.noMove == true) {
                    if (rock.distanceTo(Players.getLocal()) <= 2) {
                        if (rock.interact("Mine", rock.getDefinition().getName())) {
                            LazyAIOMiner.status = "Mining " + LazyAIOMiner.oreName;
                            if (!LazyAIOMiner.powermine) {
                                hoverNextRock();
                            }
                            Execution.delayUntil(() -> !Methods.isBusy(), 2000, 2500);
                            return true;
                        }
                    }
                } else {
                    if (rock.interact("Mine", rock.getDefinition().getName())) {
                        LazyAIOMiner.status = "Mining " + LazyAIOMiner.oreName;
                        if (!LazyAIOMiner.powermine) {
                            hoverNextRock();
                        }
                        Execution.delayUntil(() -> !Methods.isBusy(), 2000, 2500);
                        return true;
                    }
                }
            }

        }

        return false;
    }

    private boolean hoverInventoryItem() {
        LazyAIOMiner.status = "Hovering inventory";
        return Inventory.getSlotBounds().get(0).hover();
    }

    private boolean hoverNextRock() {
        LocatableEntityQueryResults<GameObject> rocks = GameObjects.getLoaded(new Filter<GameObject>() {
            @Override
            public boolean accepts(GameObject gameObject) {
                return LazyAIOMiner.oreObjectIds.contains(gameObject.getId()) && LazyAIOMiner.mineArea.contains(gameObject) && !Players.getLocal().isFacing(gameObject);
            }
        });

        GameObject rock = rocks.sortByDistance().nearestTo(Players.getLocal());
        LazyAIOMiner.status = "Hovering next rock";

        if (LazyAIOMiner.noMove == true) {
            return rock != null && !Players.getLocal().isFacing(rock) && rock.distanceTo(Players.getLocal()) <= 1 && rock.hover();
        } else {
            return rock != null && !Players.getLocal().isFacing(rock) && rock.hover();
        }
    }

    private boolean gotOre() {
        return Inventory.getQuantity(LazyAIOMiner.oreName) >= 1;
    }

    private boolean canClickRock() {
        LocatableEntityQueryResults<GameObject> rocks = GameObjects.getLoaded(new Filter<GameObject>() {
            @Override
            public boolean accepts(GameObject gameObject) {
                return LazyAIOMiner.oreObjectIds.contains(gameObject.getId()) && LazyAIOMiner.mineArea.contains(gameObject);
            }
        });
        GameObject rock = rocks.sortByDistance().nearestTo(Players.getLocal());

        return rock != null && rock.isVisible();
    }

    private boolean atSpot() {
        return LazyAIOMiner.spot.equals(Players.getLocal().getPosition());
    }

    private boolean walkToSpot() {
        final WebPath path = Traversal.getDefaultWeb().getPathBuilder().buildTo(LazyAIOMiner.spot);
        ViewportPath view;
        LazyAIOMiner.status = "Walking back to spot";
        return path != null && (view = ViewportPath.convert(path)) != null && view.step(true);
    }

    private boolean walkToRocks() {
        LocatableEntityQueryResults<GameObject> rocks = GameObjects.getLoaded(new Filter<GameObject>() {
            @Override
            public boolean accepts(GameObject gameObject) {
                return LazyAIOMiner.oreObjectIds.contains(gameObject.getId()) && LazyAIOMiner.mineArea.contains(gameObject) && !Players.getLocal().isFacing(gameObject);
            }
        });
        GameObject rock = rocks.sortByDistance().nearestTo(Players.getLocal());
        final WebPath path = Traversal.getDefaultWeb().getPathBuilder().buildTo(rock);

        if (Methods.atMine()) {
            return path != null && path.step();
        }

        return false;
    }

    private boolean rockIsInUse(GameObject Rock){
        for (Player P : Players.getLoaded()){
            if (!P.equals(Players.getLocal()) && P.getAnimationId() == 624 && Players.getLocal().isFacing(Rock.getPosition())){
                return true;
            }
        }
        return false;
    }

}