package Solver;

import Solver.Model.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.reverseOrder;


public class GreedySolver extends KnapsackSolver {
    @Override
    public String getName() {
        return "Greedy Solver";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void solve() {
        itemSelection.clear();
        List<Item> greedyItems = new ArrayList<>(items);
        greedyItems.sort(Comparator.comparing(Item::getRatio, reverseOrder()));

        double capUsed = 0;

        for (Item item : greedyItems) {
            if (capUsed + item.getWeight() > capacity) continue;

            capUsed += item.getWeight();
            itemSelection.add(item);

        }

    }

    @Override
    public int getProgress() {
        return 0;
    }
}
