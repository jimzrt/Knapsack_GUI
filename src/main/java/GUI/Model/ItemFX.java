package GUI.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by james on 06.05.2017.
 */
public class ItemFX {
    private final StringProperty name;
    private final IntegerProperty weight;
    private final IntegerProperty value;

    public ItemFX(String name, int weight, int value) {
        this.name = new SimpleStringProperty(name);
        this.weight = new SimpleIntegerProperty(weight);
        this.value = new SimpleIntegerProperty(value);

    }

    public int getWeight() {
        return weight.get();
    }

    public void setWeight(int weight) {
        this.weight.set(weight);
    }

    public IntegerProperty weightProperty() {
        return weight;
    }

    public int getValue() {
        return value.get();
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    public IntegerProperty valueProperty() {
        return value;
    }

    public String toString() {
        return getName() + ", Gewicht: " + getWeight() + "kg, Wert: " + getValue() + "€";
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);

    }
}
