package GUI.Controller;

import GUI.Model.ItemFX;
import GUI.Util.Converter;
import Solver.Model.Result;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by tophoven on 18.05.2017.
 */
public class ResultViewController {
    @FXML
    private ListView<ItemFX> itemList;
    @FXML
    private Label resultLabel;
    private Result result;
    private Stage stage;

    @FXML
    private void initialize() {

        itemList.cellFactoryProperty().set((p) -> new ListCell<ItemFX>() {
            @FXML
            private Label nameLabel;

            @FXML
            private Label weightLabel;

            @FXML
            private Label valueLabel;

            @FXML
            private ImageView imageViewIcon;
            private Image bagImage = new Image("/GUI/View/Images/bag.png");


            private Pane pane;

            private FXMLLoader mLLoader;

            @Override
            protected void updateItem(ItemFX item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    if (mLLoader == null) {

                        mLLoader = new FXMLLoader();
                        mLLoader.setLocation(getClass().getResource("/GUI/View/ItemFXCell.fxml"));

                        mLLoader.setController(this);

                        try {
                            pane = mLLoader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    imageViewIcon.setImage(bagImage);
                    imageViewIcon.setFitHeight(45);
                    imageViewIcon.setFitWidth(60);
                    imageViewIcon.setPreserveRatio(true);
                    nameLabel.setText(item.getName());
                    weightLabel.setText(item.getWeight() + "kg");
                    valueLabel.setText("" + item.getValue() + "€");


                    setText(null);
                    setGraphic(pane);
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }

        });


    }


    public void setResult(Result result) {
        this.result = result;
        this.itemList.setItems(Converter.ItemsToItemsFX(result.getItemSelection()));
        this.resultLabel.setText(result.getItemSelection().size() + " Gegenstände bei einem Maximalgewicht von "
                + result.getCapacity() + "kg mit " + result.getSolverName() + " ausgewählt.\n\n"
                + "Berechnungszeit: " + result.getComputingTime() + "ms\n\n"
                + "Maximaler Wert: " + result.getMaxValue() + "€\n\n"
                + "Gewicht: " + result.getWeight() + "kg");
    }

    @FXML
    public void close(ActionEvent event) {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
