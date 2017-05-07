package Solver;

import Solver.Model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 06.05.2017.
 */
public class DynamicSolver extends ASolver {

    int[][] matrix;

    public DynamicSolver() {

    }

    public DynamicSolver(List<Item> items, int capacity) {
        super(items, capacity);
    }

    @Override
    public String getName() {
        return "Dynamic Solver";
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

        setTerminalText("Berechne Item-Selektion..");
        this.itemSelection = getItemSelection(this.items,this.matrix);
        this.isFinished = true;
        setTerminalText(" ");
    }

    @Override
    public int getProgress() {
        return 0;
    }





    public int[][] buildMatrix(List<Item> items, int weight){
        int rows = items.size()+1;
        int columns = weight+1;
        int[][] matrix = new int[10000][50000];




        for(int row=0;row < rows; row++){
            for(int col=0; col < columns; col++){

                //first column and row always 0
                if (col == 0 || row == 0){
                    matrix[row][col] = 0;
                    continue;
                }

                int bestBefore = matrix[row-1][col];

                if(col < items.get(row-1).getWeight()){
                    matrix[row][col]= bestBefore;

                } else {

                    int value = items.get(row-1).getValue();
                    int addition = matrix[row-1][col-items.get(row-1).getWeight()];

                    matrix[row][col]= Math.max(value+addition,bestBefore);
                }


            }
        }

        return matrix;

    }

    public List<Item> getItemSelection(List<Item> items, int[][] matrix){

        List<Item> itemSelection = new ArrayList<Item>();
        int row = matrix.length-1;
        int col = matrix[0].length-1;

        while (col > 0 && row > 0){

            if(row > 0 && matrix[row][col] <= matrix[row-1][col]){
                row--;
                continue;
            }
            itemSelection.add(items.get(row-1));
            col -= items.get(row-1).getWeight();
            row--;

        }

        return itemSelection;

    }

}
