package Solver;

import Solver.Model.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.reverseOrder;

/**
 * Created by james on 06.05.2017.
 */
public class GreedySolver extends ASolver {
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
        List<Item> greedyItems = new ArrayList<Item>(items);
        greedyItems.sort(Comparator.comparing(Item::getRatio, reverseOrder()));

        double capUsed = 0;

        for (int i = 0; i < greedyItems.size(); i++) {
            Item item = greedyItems.get(i);
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
