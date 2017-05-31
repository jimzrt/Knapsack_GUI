package Solver;

import Solver.Model.Item;

import java.util.ArrayList;
import java.util.List;


public class DynamicSolverOpt extends KnapsackSolver {

    private int[][] matrix;


    @Override
    public String getName() {
        return "Dynamic Solver Optimized";
    }

    @Override
    public String getDescription() {
        return "yay";
    }


    @Override
    public void solve() {
        setTerminalText("Erstelle Matrix...");
        try {
            this.matrix = buildMatrix(this.items, this.capacity);

        } catch (Throwable ex) {
            System.err.println("Uncaught exception - " + ex.getMessage());
            ex.printStackTrace(System.err);
        }

        int maxValue = Math.max(matrix[0][capacity], matrix[1][capacity]);

        setTerminalText("Max Value: " + maxValue);
        setTerminalText("Berechne Item-Selektion..");
        this.itemSelection = getItemSelection(this.items, this.matrix);
        this.isFinished = true;
        setTerminalText(" ");
    }


    @Override
    public int getProgress() {
        return 0;
    }


    private int[][] buildMatrix(List<Item> items, int weight) {
        int rows = items.size() + 1;
        int columns = weight + 1;
        int[][] matrix = new int[2][columns];


        int current = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {

                //first column and row always 0
                if (col == 0 || row == 0) {
                    matrix[current][col] = 0;
                    continue;
                }

                int bestBefore = matrix[1 - current][col];

                if (col < items.get(row - 1).getWeight()) {
                    matrix[current][col] = bestBefore;

                } else {

                    int value = items.get(row - 1).getValue();
                    int addition = matrix[1 - current][col - items.get(row - 1).getWeight()];

                    matrix[current][col] = Math.max(value + addition, bestBefore);

                }


            }
            current = 1 - current;
        }


        return matrix;

    }

    private List<Item> getItemSelection(List<Item> items, int[][] matrix) {

        List<Item> itemSelection = new ArrayList<>();
        int row = matrix.length - 1;
        int col = matrix[0].length - 1;

        while (col > 0 && row > 0) {

            if (row > 0 && matrix[row][col] <= matrix[row - 1][col]) {
                row--;
                continue;
            }
            itemSelection.add(items.get(row - 1));
            col -= items.get(row - 1).getWeight();
            row--;

        }

        return itemSelection;

    }

}
