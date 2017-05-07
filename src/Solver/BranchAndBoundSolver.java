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


        Node best = makeKnapSack();

        itemSelection = best.taken;
        System.out.println(best.value);





    }

    @Override
    public int getProgress() {
        return 0;
    }

    public Node makeKnapSack()
    {
        Queue<Node> q = new PriorityQueue<>();
        List<Item> itemInKnapsack = items;
        int numItems=itemInKnapsack.size();
        int maxWeight = capacity;
        int maxProfit = 0;
        //creates root node
        Node v= new Node();
        Node u;
        //initilizes the max profit, bound, and maxprofit node according to values of node v
       v.computeBound();
        Node maxProfitNode = v;
        //adds root to the queue
        q.add(v);
        while(!(q.isEmpty()))
        {
            ArrayList<Item> parentList = new ArrayList<>();
            //creates a temp node that refers to the first item in the priority queue and then removes that item
            Node tempNode = q.peek();
            q.remove();
            //checks if the bound is greater than the current maximum profit
            if(tempNode.bound > maxProfit)
            {
                //adds the items of the parent node to the temp node
                parentList.addAll(tempNode.taken);
                //creates a new node that is the child of the temp node
                u = new Node(tempNode);
                u.weight += itemInKnapsack.get(u.level).getWeight();
                u.value += itemInKnapsack.get(u.level).getValue();

                //sets the bound
                u.computeBound();
                //determines if the childs weight is less than max and profit is greater than current max and adds it to the list of items
                //changes the maxProfit, and sets the child to be the node at which the best profit is determined at
                u.taken.add(itemInKnapsack.get(u.level));
                if(u.weight <= maxWeight && u.value > maxProfit)
                {

                    maxProfit = u.value;
                    maxProfitNode = u;
                }
                //if the bound at child is better than the max profit and it is not the last item the child is added to the queue
                if(u.bound > maxProfit && u.level < numItems-1)
                {
                    q.add(u);
                }
                //the temporary node level is incremented and bound is set to the new bound of the incramented temp node
                tempNode.level++;
                tempNode.computeBound();
                //if the temp node's bound is higher than the max profit and it is not the last item it is added to the queue
                if(tempNode.bound > maxProfit && u.level < numItems-1) {
                    q.add(tempNode);
                }
            }
        }
        //the node contianing the maxProfit is returned.  Also contains the optimal solution to the knapsack problem
        return maxProfitNode;
    }

    private class Node implements Comparable<Node> {

        int level;
        double bound;
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
            return (int) (other.bound - bound);
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
                //bound += (capacity - w) * item.getRatio();
            }


        }
    }
}
