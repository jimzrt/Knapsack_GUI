package Solver;

import Solver.Model.Item;

import java.util.List;

/**
 * Created by james on 06.05.2017.
 */
public interface ISolver {

    public String getName();
    public String getDescription();

    public void setItems(List<Item> items);
    public void setCapacity(int capacity);
    public void solve();
    public int getProgress();
    public boolean isFinished();
    public List<Item> getSelection();
    public int getTotalWeight();
    public int getMaxValueSum();
}
