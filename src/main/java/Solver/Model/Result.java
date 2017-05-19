package Solver.Model;

import java.util.List;


public class Result {

    private String solverName;
    private long computingTime;
    private List<Item> itemSelection;
    private int maxValue;
    private int capacity;
    private int weight;

    public Result(String solverName, long computingTime, List<Item> itemSelection, int maxValue, int weight, int capacity) {
        this.solverName = solverName;
        this.computingTime = computingTime;
        this.itemSelection = itemSelection;
        this.maxValue = maxValue;
        this.weight = weight;
        this.capacity = capacity;
    }

    public Result() {

    }

    public String getSolverName() {
        return solverName;
    }

    public void setSolverName(String solverName) {
        this.solverName = solverName;
    }

    public long getComputingTime() {
        return computingTime;
    }

    public void setComputingTime(long computingTime) {
        this.computingTime = computingTime;
    }

    public List<Item> getItemSelection() {
        return itemSelection;
    }

    public void setItemSelection(List<Item> itemSelection) {
        this.itemSelection = itemSelection;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String toString() {
        return solverName + " " + computingTime + " " + maxValue + " " + capacity;
    }
}
