package Solver;

import Solver.Model.Item;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public abstract class ASolver {

    List<Item> items;
    int capacity;

    List<Item> itemSelection = new ArrayList<>();

    boolean isFinished = false;

    private SimpleStringProperty outputBuffer = new SimpleStringProperty();

    public ASolver() {
    }


    public ASolver(List<Item> items, int capacity) {
        this.items = items;
        this.capacity = capacity;
    }

    public void setOutputBuffer(SimpleStringProperty outputBuffer) {
        this.outputBuffer = outputBuffer;
    }

    public abstract String getName();

    public abstract String getDescription();

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    public abstract void solve();

    public abstract int getProgress();

    public boolean isFinished() {
        return isFinished;
    }


    public List<Item> getItemSelection() {
        itemSelection.sort(Comparator.comparing(Item::getWeight));
        return itemSelection;
    }


    public int getTotalWeight() {
        return getTotalWeight(itemSelection);
    }


    public int getTotalWeight(List<Item> items) {
        return items.stream().mapToInt(Item::getWeight).sum();
    }


    public int getMaxValueSum() {
        return getMaxValueSum(itemSelection);
    }


    public int getMaxValueSum(List<Item> items) {
        return items.stream().mapToInt(Item::getValue).sum();
    }


    public void setTerminalText(String text) {
        Platform.runLater(() -> {
            outputBuffer.set(text);
        });

    }
}
