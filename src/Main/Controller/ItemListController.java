package Main.Controller;

import Main.MainApp;
import Main.Model.ItemFX;
import com.sun.javafx.scene.control.skin.LabeledText;
import com.sun.javafx.scene.control.skin.ListViewSkin;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class ItemListController {

    SimpleStringProperty terminalBuffer = new SimpleStringProperty();
    private ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
        public Thread newThread(Runnable r) {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }
    });
    @FXML
    private ImageView spinnerImage;
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

    private static PseudoClass ACTIVE_PSEUDO_CLASS = PseudoClass.getPseudoClass("active");


    @FXML
    private void initialize() {


        this.capacityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("[0-9]+")){
                mainApp.getCapacity().set(newValue);
            }
        });

        terminalBuffer.addListener((observable, oldValue, newValue) -> {
            if (newValue != "") {
                terminal.appendText(newValue + "\n");
            } else {
                terminalBuffer.setValue("");
            }

        });




        solverList.cellFactoryProperty().set(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {


                ListCell cell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null) {
                            setText(item);
                            pseudoClassStateChanged(ACTIVE_PSEUDO_CLASS, mainApp.getCurrentSolver() != null && mainApp.getCurrentSolver().getClass().toString().endsWith(getText()));

                        } else {
                            setText("");
                        }
                    }

                };

                cell.addEventFilter(MouseEvent.MOUSE_CLICKED,(event) -> {
                        if (event.getClickCount() == 2) {
                          //  System.out.println("double clicked!");
                            ListCell c = (ListCell) event.getSource();
                            if(!c.getText().equals("")){
                                handleLoadSolver();
                            }

                        }

                });

                return cell;

            }
        });

        spinnerImage.setImage(new Image("/Main/View/Images/spinner.gif"));

    }


    @FXML
    private void handleAddItem() {
        if(weightField.getText().matches("[0-9]+") && valueField.getText().matches("[0-9]+")){
            int weight = Integer.valueOf(weightField.getText());
            int value = Integer.valueOf(valueField.getText());
            ItemFX newItem = new ItemFX(weight,value);
            mainApp.getItems().add(newItem);
            //FXCollections.sort(mainApp.getItems(),Comparator.comparingInt(ItemFX::getWeight));
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
    private void handleSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Knapsack Problem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text", "*.txt")
        );
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

                //first line capacity and Length
                bw.write(mainApp.getCapacity().get() + " " + mainApp.getItems().size());
                bw.newLine();
                for (ItemFX item : mainApp.getItems()) {
                    bw.write(item.getWeight() + " " + item.getValue());
                    bw.newLine();
                }

            } catch (IOException e) {

                e.printStackTrace();

            }


        }

    }

    @FXML
    private void handleLoadSolver(){
        String className = solverList.getSelectionModel().getSelectedItem();

        if (className != null) {
            mainApp.loadSolver(className, terminalBuffer);
            solverList.refresh();
        }


    }

    @FXML
    public void handleSolve() {

        spinnerImage.visibleProperty().set(true);
        mainApp.solve(terminalBuffer);

        executor.submit(() -> {

            while (mainApp.isComputing()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> {
                spinnerImage.visibleProperty().set(false);
            });

        });


    }

    @FXML
    public void handleRandomDialog() {

        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(mainApp.getClass().getResource("/Main/View/RandomItemsDialog.fxml"));
            Pane page = (Pane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Zufall");
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            RandomItemsDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setItemList(mainApp.getItems());

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            // FXCollections.sort(mainApp.getItems(),Comparator.comparingInt(ItemFX::getWeight));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void handleFileImport() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Knapsack Problem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text", "*.txt")
        );
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.getItems().clear();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                //first line capacity and Length
                String first = br.readLine();
                mainApp.getCapacity().set(first.split(" ")[0]);
                for (int i = 0; i < Integer.valueOf(first.split(" ")[1]); i++) {
                    String itemString = br.readLine();
                    int weight = Integer.valueOf(itemString.split(" ")[0]);
                    int value = Integer.valueOf(itemString.split(" ")[1]);
                    mainApp.getItems().add(new ItemFX(weight, value));
                }


            } catch (IOException e) {

                e.printStackTrace();

            }


        }


    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        itemList.setItems(mainApp.getItems());

        solverList.setItems(mainApp.getSolvers());

        capacityField.textProperty().bindBidirectional(mainApp.getCapacity());

    }


}
