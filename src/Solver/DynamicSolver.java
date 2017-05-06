package Solver;

import Solver.Model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 06.05.2017.
 */
public class DynamicSolver implements ISolver{

    boolean isFinished = false;
    int[][] matrix;
    List<Item> items;
    List<Item> itemSelection;
    int capacity;

    @Override
    public String getName() {
        return "Dynamic Solver";
    }

    @Override
    public String getDescription() {
        return "yay";
    }

    @Override
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void solve() {
        this.matrix = buildMatrix(this.items,this.capacity);
        this.itemSelection = getItemSelection(this.items,this.matrix);
        this.isFinished = true;
    }

    @Override
    public int getProgress() {
        return 0;
    }

    @Override
    public boolean isFinished() {
        return this.isFinished;
    }

    @Override
    public List<Item> getSelection() {
        return this.itemSelection;
    }

    @Override
    public int getTotalWeight() {

            return this.itemSelection.stream().mapToInt(Item::getWeight).sum();

    }

    @Override
    public int getMaxValueSum() {
        return this.matrix[ this.matrix.length-1][ this.matrix[0].length-1];
    }


    public int[][] buildMatrix(List<Item> items, int weight){
        int rows = items.size()+1;
        int columns = weight+1;
        int[][] matrix = new int[rows][columns];




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

    private int getMaxValueSum(int[][] matrix){
       return matrix[matrix.length-1][matrix[0].length-1];
    }
}
