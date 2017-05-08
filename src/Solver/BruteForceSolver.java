package Solver;

import Solver.Model.Item;

import java.util.ArrayList;
import java.util.List;


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

        itemSelection.clear();
        RecursiveKnapsack(items.size() - 1, capacity, itemSelection);



    }

    int RecursiveKnapsack(int i, int w, List<Item> taken) {
        if (i < 0 || w == 0)
            return 0;

        if (items.get(i).getWeight() > w) {
            return RecursiveKnapsack(i - 1, w, taken);
        } else {

            final int preTookSize = taken.size();
            int with = RecursiveKnapsack(i - 1, w - items.get(i).getWeight(), taken) + items.get(i).getValue();

            final int preLeftSize = taken.size();
            int without = RecursiveKnapsack(i - 1, w, taken);

            if (with > without) {
                if (taken.size() > preLeftSize)
                    taken.subList(preLeftSize, taken.size()).clear();
                taken.add(items.get(i));
                return with;
            } else {
                if (preLeftSize > preTookSize)
                    taken.subList(preTookSize, preLeftSize).clear();
                return without;
            }


        }
    }


    @Override
    public int getProgress() {
        return 0;
    }

    private List<List<Item>> sublists(List<Item> items) {

        List<List<Item>> sublists = new ArrayList<>();
        if (items.isEmpty()) {
            sublists.add(new ArrayList<>());
            return sublists;
        }

        Item first = items.get(0);
        List<List<Item>> subsublists = sublists(items.subList(1, items.size()));
        for (List<Item> subset : subsublists) {
            sublists.add(subset);
            List<Item> augmented = new ArrayList<>(subset);
            augmented.add(0, first);
            sublists.add(augmented);
        }
        return sublists;
    }

}
