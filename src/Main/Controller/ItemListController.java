package Main.Controller;

import Main.MainApp;
import Main.Model.ItemFX;
import Solver.ISolver;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by james on 06.05.2017.
 */
public class ItemListController {

    @FXML
    private ListView<ItemFX> itemList;
    @FXML
    private ListView<String> solverList;
    @FXML
    private TextField weightField;
    @FXML
    private TextField valueField;

    @FXML
    private TextField capacityField;

    @FXML
    private TextArea terminal;

    // Reference to the main application.
    private MainApp mainApp;

    @FXML
    private void initialize() {

        this.capacityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("[0-9]+")){
                mainApp.getCapacity().set(Integer.valueOf(newValue));
            }
        });

    }

    @FXML
    private void handleAddItem() {
        if(weightField.getText().matches("[0-9]+") && valueField.getText().matches("[0-9]+")){
            int weight = Integer.valueOf(weightField.getText());
            int value = Integer.valueOf(valueField.getText());
            ItemFX newItem = new ItemFX(weight,value);
            mainApp.getItems().add(newItem);

            weightField.setText("");
            valueField.setText("");
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }

    }


    @FXML
    private void handleDeleteItem() {
        int selectedIndex = itemList.getSelectionModel().getSelectedIndex();
        if(selectedIndex != -1){
            itemList.getItems().remove(selectedIndex);
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleLoadSolver(){
        String className = solverList.getSelectionModel().getSelectedItem();
        if(className != null){
            Class<?> clazz = null;
            ISolver solver = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Constructor<?> ctor = null;
            try {
                ctor = clazz.getConstructor();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                solver = (ISolver) ctor.newInstance();
                mainApp.setCurrentSolver(solver);
                terminal.appendText("=== " + solver.getName() + " geladen. ===\n");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    public void handleSolve(){
        terminal.appendText("Löse...\n");
        mainApp.solve();
         ObservableList<ItemFX> items = mainApp.getSelectedItems();
         for(ItemFX item : items){
             terminal.appendText(item + "\n");
         }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        itemList.setItems(mainApp.getItems());

        solverList.setItems(mainApp.getSolvers());

    }


}
