package GUI.Util;

import GUI.Model.ItemFX;
import Solver.Model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

/**
 * Created by james on 06.05.2017.
 */
public class Converter {

    public static List<Item> ItemsFXToItems(ObservableList<ItemFX> itemsFX) {
        return itemsFX.stream().map(obj -> new Item(obj.getName(), obj.getWeight(), obj.getValue())).collect(Collectors.toList());
    }

    public static ObservableList<ItemFX> ItemsToItemsFX(List<Item> items) {
        return items.stream().map(obj -> new ItemFX(obj.getName(), obj.getWeight(), obj.getValue())).collect(toCollection(FXCollections::observableArrayList));
    }
}
