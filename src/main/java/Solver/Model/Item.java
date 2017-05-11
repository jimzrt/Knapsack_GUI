package Solver.Model;


public class Item {

    private String name;
    private int weight;
    private int value;


    public Item(String name, int weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getRatio() {
        return value / (double) weight;
    }


    public String toString() {
        return "[" + name + "\t Gewicht: " + weight + "kg \t Wert: " + value + "€]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}