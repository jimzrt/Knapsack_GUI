package Main.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by james on 06.05.2017.
 */
public class ItemFX {
    public int getWeight() {
        return weight.get();
    }

    public IntegerProperty weightProperty() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight.set(weight);
    }

    public int getValue() {
        return value.get();
    }

    public IntegerProperty valueProperty() {
        return value;
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    private final IntegerProperty weight;
    private final IntegerProperty value;


    public ItemFX(int weight, int value){
        this.weight = new SimpleIntegerProperty(weight);
        this.value = new SimpleIntegerProperty(value);

    }

    public String toString(){
        return "Gewicht: " + getWeight() + "kg, Wert: " + getValue() + "€";
    }
}
