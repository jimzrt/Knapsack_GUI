package Solver;

import Solver.Model.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import static java.util.Comparator.reverseOrder;

/**
 * Created by james on 06.05.2017.
 */
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
        root.computeBound();

        PriorityQueue<Node> q = new PriorityQueue<Node>();
        q.offer(root);

        while (true) {
            System.out.println("size " + q.size());
            if (q.isEmpty()) {
                break;
            }
            Node node = q.poll();
            System.out.println(node);

            if (node.bound > best.value && node.h <= items.size() - 1) {


                Node with = new Node(node);
                Item item = items.get(node.h);
                with.weight += item.getWeight();

                if (with.weight <= capacity) {
                    with.taken.add(items.get(node.h));
                    with.value += item.getValue();
                    with.computeBound();

                    if (with.value > best.value) {
                        best = with;
                    }
                    if (with.bound > best.value) {
                        q.offer(with);
                    }
                } else {
                    Node without = new Node(node);
                    without.computeBound();

                    if (without.bound > best.value) {
                        q.offer(without);
                    }
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

        public int h;
        public double bound;
        public double value;
        public double weight;
        List<Item> taken;

        public Node() {
            taken = new ArrayList<Item>();
        }

        public Node(Node parent) {
            h = parent.h + 1;
            taken = new ArrayList<Item>(parent.taken);
            bound = parent.bound;
            value = parent.value;
            weight = parent.weight;
        }

        // Sort by bound
        public int compareTo(Node other) {
            return (int) (other.bound - bound);
        }

        public void computeBound() {
            int i = h;
            double w = weight;
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
