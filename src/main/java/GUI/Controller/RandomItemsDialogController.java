package GUI.Controller;

import GUI.Model.ItemFX;
import GUI.Util.NameGenerator;
import GUI.Util.Validator;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Random;

//TODO: max < min, integer-limit

public class RandomItemsDialogController {

    @FXML
    private TextField anzahlField;
    @FXML
    private TextField weightMinField;
    @FXML
    private TextField weightMaxField;
    @FXML
    private TextField valueMinField;
    @FXML
    private TextField valueMaxField;
    @FXML
    private Slider anzahlSlider;

    private Stage dialogStage;
    private ObservableList<ItemFX> itemList;
    private SimpleIntegerProperty anzahl = new SimpleIntegerProperty(10);






    @FXML
    private void initialize() {

        //initial values
        anzahlField.setText("" + anzahl.get());
        weightMinField.setText("5");
        Validator.createSimpleNumberValidator(weightMinField);
        weightMaxField.setText("50");
        Validator.createSimpleNumberValidator(weightMaxField);
        valueMinField.setText("5");
        Validator.createSimpleNumberValidator(valueMinField);
        valueMaxField.setText("50");
        Validator.createSimpleNumberValidator(valueMaxField);


        anzahl.addListener((observable, oldValue, newValue) -> {
            anzahlField.setText(newValue.toString());
        });


        anzahlField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("[0-9]+")) {
                anzahl.set(Integer.valueOf(newValue));
            } else {
                anzahlField.textProperty().set(oldValue);
            }
        });

        anzahlSlider.valueProperty().bindBidirectional(anzahl);


    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setItemList(ObservableList<ItemFX> itemList) {
        this.itemList = itemList;

    }


    @FXML
    private void handleAdd() {

        Random random = new Random();
        for (int i = 0; i < Integer.valueOf(anzahlField.getText()); i++) {
            String name = NameGenerator.getRandomName();

            int minWeight = Integer.valueOf(weightMinField.getText());
            int maxWeight = Integer.valueOf(weightMaxField.getText());

            int minValue = Integer.valueOf(valueMinField.getText());
            int maxValue = Integer.valueOf(valueMaxField.getText());

            this.itemList.add(new ItemFX(name, random.nextInt(maxWeight - minWeight + 1) + minWeight, random.nextInt(maxValue - minValue + 1) + minValue));

        }

        dialogStage.close();

    }


}
