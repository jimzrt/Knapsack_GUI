package GUI.Controller;

import GUI.Model.ItemFX;
import javafx.fxml.FXML;

import java.util.List;

/**
 * Created by tophoven on 18.05.2017.
 */
public class ResultViewController {
    @FXML
    private List<ItemFX> itemList;

    public void setItemList(List<ItemFX> itemList) {
        this.itemList = itemList;
    }
}
