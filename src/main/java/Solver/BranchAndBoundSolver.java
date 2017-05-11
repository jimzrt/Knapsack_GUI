package Solver;

import Solver.Model.Item;

import java.util.*;

import static java.util.Comparator.reverseOrder;


public class BranchAndBoundSolver extends ASolver {

    @Override
    public String getName() {
        return "Branch and Bound Solver";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void solve() {
        itemSelection.clear();

        items.sort(Comparator.comparing(Item::getRatio, reverseOrder()));


        Node best = new Node();
        Node root = new Node();
        // root.level = -1;
        root.computeBound();

        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);

        while (!q.isEmpty()) {

            Node node = q.poll();

            // If there is nothing on next level
            if (node.level == items.size())
                continue;


            if (node.bound > best.value) {


                Node with = new Node(node);
                Item item = items.get(node.level);
                with.weight += item.getWeight();

                if (with.weight <= capacity) {
                    with.taken.add(items.get(node.level));
                    with.value += item.getValue();
                    with.computeBound();

                    if (with.value > best.value) {
                        best = with;
                    }
                    if (with.bound > best.value) {
                        q.offer(with);
                    }
                }

                Node without = new Node(node);
                without.computeBound();

                if (without.bound > best.value) {
                    q.offer(without);
                }


            }
        }

        System.out.println("done");
        itemSelection = best.taken;


    }

    @Override
    public int getProgress() {
        return 0;
    }

    private class Node implements Comparable<Node> {

        int level;
        int bound;
        int value;
        int weight;
        List<Item> taken;

        public Node() {
            taken = new ArrayList<>();
        }

        public Node(Node parent) {
            level = parent.level + 1;
            taken = new ArrayList<>(parent.taken);
            bound = parent.bound;
            value = parent.value;
            weight = parent.weight;
        }

        // Sort by bound
        public int compareTo(Node other) {
            return (other.bound - bound);
        }

        void computeBound() {
            int i = level;
            int w = weight;
            bound = value;
            Item item;
            while (i < items.size()) {
                item = items.get(i);
                i++;
                if (w + item.getWeight() > capacity) continue;
                w += item.getWeight();
                bound += item.getValue();
                bound += (capacity - w) * item.getRatio();
            }


        }
    }
}
