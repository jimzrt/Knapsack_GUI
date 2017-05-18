package GUI.Controller;

import GUI.Model.ItemFX;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

/**
 * Created by tophoven on 18.05.2017.
 */
public class ResultViewController {
    @FXML
    private ListView<ItemFX> itemList;

    public void setItemList(ObservableList<ItemFX> itemList) {
        this.itemList.setItems(itemList);
    }
}
