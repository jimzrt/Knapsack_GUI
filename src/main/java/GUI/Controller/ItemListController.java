package GUI.Controller;

import GUI.MainApp;
import GUI.Model.ItemFX;
import GUI.Util.NameGenerator;
import GUI.Util.Validator;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class ItemListController {

    private static PseudoClass ACTIVE_PSEUDO_CLASS = PseudoClass.getPseudoClass("active");
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
    private ImageView decorationImageView;
    @FXML
    private Pane waitPane;
    @FXML
    private ListView<ItemFX> itemList;
    @FXML
    private ListView<String> solverList;
    @FXML
    private TextField nameField;
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


        Validator.createSimpleNumberValidator(capacityField);
        Validator.createSimpleNumberValidator(weightField);
        Validator.createSimpleNumberValidator(valueField);



        terminalBuffer.addListener((observable, oldValue, newValue) -> {
            if (newValue != "") {
                terminal.appendText(newValue + "\n");


                terminalBuffer.setValue("");
            }
            terminal.setScrollTop(Double.MAX_VALUE);


        });


        itemList.setOnKeyPressed((event) ->
                {


                    int selectedIndex = itemList.getSelectionModel().getSelectedIndex();
                    if (selectedIndex != -1) {
                        if (event.getCode().equals(KeyCode.DELETE)) {
                            handleDeleteItem();

                        }

                    }
                }
        );


        itemList.cellFactoryProperty().set((p) -> {
            ListCell cell = new ListCell<ItemFX>() {
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
                            mLLoader.setLocation(mainApp.getClass().getResource("/GUI/View/ItemFXCell.fxml"));

                            mLLoader.setController(this);

                            try {
                                pane = mLLoader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        imageViewIcon.setImage(bagImage);
                        imageViewIcon.setFitHeight(55);
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

            };

            cell.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
                System.out.println("yea");

            });

            return cell;
        });


        solverList.cellFactoryProperty().set(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {


                ListCell cell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            pseudoClassStateChanged(ACTIVE_PSEUDO_CLASS, mainApp.getCurrentSolver() != null && mainApp.getCurrentSolver().getClass().toString().endsWith(getText()));

                        } else {
                            setText("");
                        }
                    }

                };

                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
                    if (event.getClickCount() == 2) {
                        //  System.out.println("double clicked!");
                        ListCell c = (ListCell) event.getSource();
                        if (!c.getText().equals("")) {
                            handleLoadSolver();
                        }

                    }

                });

                return cell;

            }
        });

        nameField.promptTextProperty().set("optional");
        spinnerImage.setImage(new Image("/GUI/View/Images/spinner.gif"));
        spinnerImage.setFitHeight(120);
        spinnerImage.setFitWidth(120);
        spinnerImage.setPreserveRatio(true);

        decorationImageView.setImage(new Image("/GUI/View/Images/knap.png"));
        decorationImageView.setFitHeight(180);
        decorationImageView.setFitWidth(180);
        decorationImageView.setPreserveRatio(true);


        terminalBuffer.set("\n██████╗  █████╗ ███████╗                                              \n" +
                "██╔══██╗██╔══██╗██╔════╝                                              \n" +
                "██║  ██║███████║███████╗                                              \n" +
                "██║  ██║██╔══██║╚════██║                                              \n" +
                "██████╔╝██║  ██║███████║                                              \n" +
                "╚═════╝ ╚═╝  ╚═╝╚══════╝                                              \n" +
                "██████╗ ██╗   ██╗ ██████╗██╗  ██╗███████╗ █████╗  ██████╗██╗  ██╗     \n" +
                "██╔══██╗██║   ██║██╔════╝██║ ██╔╝██╔════╝██╔══██╗██╔════╝██║ ██╔╝     \n" +
                "██████╔╝██║   ██║██║     █████╔╝ ███████╗███████║██║     █████╔╝█████╗\n" +
                "██╔══██╗██║   ██║██║     ██╔═██╗ ╚════██║██╔══██║██║     ██╔═██╗╚════╝\n" +
                "██║  ██║╚██████╔╝╚██████╗██║  ██╗███████║██║  ██║╚██████╗██║  ██╗     \n" +
                "╚═╝  ╚═╝ ╚═════╝  ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝     \n" +
                "██████╗ ██████╗  ██████╗ ██████╗ ██╗     ███████╗███╗   ███╗          \n" +
                "██╔══██╗██╔══██╗██╔═══██╗██╔══██╗██║     ██╔════╝████╗ ████║          \n" +
                "██████╔╝██████╔╝██║   ██║██████╔╝██║     █████╗  ██╔████╔██║          \n" +
                "██╔═══╝ ██╔══██╗██║   ██║██╔══██╗██║     ██╔══╝  ██║╚██╔╝██║          \n" +
                "██║     ██║  ██║╚██████╔╝██████╔╝███████╗███████╗██║ ╚═╝ ██║          \n" +
                "╚═╝     ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝╚══════╝╚═╝     ╚═╝          ");

        terminalBuffer.set("\nBeschreibung:\n----------------");
        terminalBuffer.set("Das Rucksackproblem ist ein Optimierungsproblem der Kombinatorik. Aus einer Menge von Objekten, die jeweils ein Gewicht und einen Nutzwert haben, soll eine Teilmenge ausgewählt werden, deren Gesamtgewicht eine vorgegebene Gewichtsschranke nicht überschreitet. Unter dieser Bedingung soll der Nutzwert der ausgewählten Objekte maximiert werden.");
        terminalBuffer.set("\n\nBenutzung:\n----------------");
        terminalBuffer.set("In der linken Spalte kann man das Maximalgewicht definieren und einzeln Gegenstände hinzufügen.");
        terminalBuffer.set("Alternativ kann man über die Menüleiste Zufallsgegenstände generieren und hinzufügen, oder eine Textdatei importieren, in der das Maximalgewicht und Gegenstände definiert sind.");
        terminalBuffer.set("Zudem hat man die Möglichkeit die aktuelle Liste von Gegenständen und das Maxmialgewicht zu exportieren.\n");

        terminalBuffer.set("In der rechten Spalte kann man den Solver auswählen, der für die Berechnung und Gegenstands-Selektion zuständig ist.");
        terminalBuffer.set("Fur jeden Solven kann man eine Beschreibung ausgeben lassen, die ihn näher erläutert, daher nur auf die Problemgröße (Maximalgewicht und Anzahl der Gegenstände) bezogen:\n");
        terminalBuffer.set("GreedySolver: Eignet sich für Probleme jeglicher Größe wegen polynomialer\n\tLaufzeit und führt zu einem guten Ergbnis, das aber von der optimalen\n\tLösung abweichen kann.");
        terminalBuffer.set("BruteForceSolver: Eignet sich für kleinere Probleme wegen exponentiellem\n\tLaufzeitverhalten und führt zu einem korrekten Ergebnis.");
        terminalBuffer.set("BranchAndBoundSolver: Eignet sich für kleinere bis mittelgroße Probleme, weil\n\tes im Worst-case auch z uexponentiellem Laufzeitverhalten kommt\n\tund führt zu einem korrekten Ergebnis.");
        terminalBuffer.set("DynamicSolver: Eignet sich für große Probleme wegen pseudo-polynomialer\n\tLaufzeit und führt zu einem korrekten Ergbnis.");
        terminalBuffer.set("DynamicSolverOpt: Eine Speicher-optimierte Version des DynamicSolver, der mit\n\tnoch größeren Problemen umgehen kann, jedoch keine Gegenstände\n\tauswählt, sondern nur den maxmial möglichen Wert berechnet.\n\n");


    }


    @FXML
    private void handleAddItem() {
        if (weightField.getText().matches("[0-9]+") && valueField.getText().matches("[0-9]+")) {
            String name = nameField.getText().trim();
            if (name.length() > 35) {
                name = name.substring(0, 35);
            } else if (name.equals("")) {
                name = NameGenerator.getRandomName();
            }
            int weight = Integer.valueOf(weightField.getText());
            int value = Integer.valueOf(valueField.getText());
            ItemFX newItem = new ItemFX(name, weight, value);
            mainApp.getItems().add(newItem);
            //FXCollections.sort(mainApp.getItems(),Comparator.comparingInt(ItemFX::getWeight));
            weightField.setText("");
            valueField.setText("");
        } else {
            terminalBuffer.set("Nur ganze Zahlen für Gewicht und Wert!");
        }

    }

    @FXML
    private void handleNewInstance() {

        mainApp.createNewInstance();

    }

    @FXML
    private void handleClose() {

        mainApp.exit();

    }


    @FXML
    private void handleDeleteItem() {
        int selectedIndex = itemList.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            itemList.getItems().remove(selectedIndex);
        } else {
            terminalBuffer.set("Kein Gegenstand ausgewählt!");
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

                terminalBuffer.set("Gespeichert!");

            } catch (IOException e) {

                terminalBuffer.set("Error:");
                terminalBuffer.set(e.getMessage());

            }


        }

    }

    @FXML
    private void handleLoadSolver() {
        String className = solverList.getSelectionModel().getSelectedItem();

        if (className != null) {
            if (mainApp.getCurrentSolver() == null || !mainApp.getCurrentSolver().getClass().toString().endsWith(className)) {
                mainApp.loadSolver(className, terminalBuffer);
                solverList.refresh();
            }
        } else {
            terminalBuffer.set("Kein Solver ausgewählt!");
        }


    }

    @FXML
    public void handleSolve() {

        if (mainApp.getItems().isEmpty()) {
            terminalBuffer.set("Keine Gegenstände!");
            return;
        }

        spinnerImage.visibleProperty().set(true);
        decorationImageView.visibleProperty().set(false);
        // waitPane.visibleProperty().set(true);
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
                decorationImageView.visibleProperty().set(true);
                //   waitPane.visibleProperty().set(false);

            });

        });


    }

    @FXML
    public void handleRandomDialog() {

        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(mainApp.getClass().getResource("/GUI/View/RandomItemsDialog.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Gegenstände generieren...");
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
                if (first.matches("[0-9]+ [0-9]+")) {
                    mainApp.getCapacity().set(first.split(" ")[0]);
                    for (int i = 0; i < Integer.valueOf(first.split(" ")[1]); i++) {
                        String itemString = br.readLine();
                        if (itemString.matches("[0-9]+ [0-9]+")) {
                            String name = itemString.split(" ")[0];
                            int weight = Integer.valueOf(itemString.split(" ")[1]);
                            int value = Integer.valueOf(itemString.split(" ")[2]);
                            mainApp.getItems().add(new ItemFX(name, weight, value));
                        } else {
                            terminalBuffer.set("Format-Error: " + itemString);
                            mainApp.getItems().clear();
                            return;
                        }

                    }

                    terminalBuffer.set(first.split(" ")[1] + " Gegenstände geladen - Maximalgewicht " + first.split(" ")[0] + "kg");
                } else {
                    terminalBuffer.set("Format-Error: " + first);
                }


            } catch (IOException e) {

                e.printStackTrace();

            }


        }


    }

    @FXML
    public void handleGetDescription() {


        if (mainApp.getCurrentSolver() != null) {
            terminalBuffer.set(mainApp.getCurrentSolver().getDescription());
        } else {
            terminalBuffer.set("Kein Solver ausgewählt!");
        }


    }

    @FXML
    public void handleClearItems() {
        mainApp.getItems().clear();
        terminalBuffer.set("Gegenstände geleert!");
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        itemList.setItems(mainApp.getItems());

        solverList.setItems(FXCollections.observableArrayList(MainApp.SOLVER_CLASS_NAMES));

        capacityField.textProperty().bindBidirectional(mainApp.getCapacity());

        terminal.setScrollTop(Double.MIN_VALUE);
        weightField.requestFocus();



    }


}
