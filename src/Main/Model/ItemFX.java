package Main.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by james on 06.05.2017.
 */
public class ItemFX {
    private final IntegerProperty weight;
    private final IntegerProperty value;

    public ItemFX(int weight, int value) {
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

    public String toString(){
        return "Gewicht: " + getWeight() + "kg, \t Wert: " + getValue() + "€";
    }
}
