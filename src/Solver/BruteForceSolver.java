package Solver;

import Solver.Model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 06.05.2017.
 */
public class BruteForceSolver extends ASolver {

    public BruteForceSolver() {

    }


    public BruteForceSolver(List<Item> items, int capacity) {
        super(items, capacity);
    }

    @Override
    public String getName() {
        return "Brute Force Solver";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void solve() {
        int bestValue = 0;
        List<Item> selection = new ArrayList<Item>();
        for (List<Item> subset : sublists(items)) {
            int weight = getTotalWeight(subset);
            if (weight <= capacity) {
                int value = getMaxValueSum(subset);
                if (value > bestValue) {
                    bestValue = value;
                    selection = subset;
                }
            }
        }
        this.itemSelection = selection;

    }

    @Override
    public int getProgress() {
        return 0;
    }

    private List<List<Item>> sublists(List<Item> items) {

        List<List<Item>> sublists = new ArrayList<List<Item>>();
        if (items.isEmpty()) {
            sublists.add(new ArrayList<Item>());
            return sublists;
        }

        Item first = items.get(0);
        List<List<Item>> subsublists = sublists(items.subList(1, items.size()));
        for (List<Item> subset : subsublists) {
            sublists.add(subset);
            List<Item> augmented = new ArrayList<Item>(subset);
            augmented.add(0, first);
            sublists.add(augmented);
        }
        return sublists;
    }

}
