package GUI;

import GUI.Controller.MainGuiController;
import GUI.Controller.ResultViewController;
import GUI.Model.ItemFX;
import GUI.Util.Converter;
import Solver.KnapsackSolver;
import Solver.Model.Item;
import Solver.Model.Result;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApp extends Application {

    public static String[] SOLVER_CLASS_NAMES = new String[]{"GreedySolver", "BruteForceSolver", "BranchAndBoundSolver", "DynamicSolver", "DynamicSolverOpt"};


    public SimpleStringProperty capacity = new SimpleStringProperty("15");
    boolean computing = false;
    ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
        Thread t = Executors.defaultThreadFactory().newThread(r);
        t.setDaemon(true);
        return t;
    });
    private Stage primaryStage;
    private Pane rootLayout;

    private ObservableList<ItemFX> items = FXCollections.observableArrayList();
    private ObservableList<String> solvers;

    private KnapsackSolver currentSolver = null;



    public static void main(String[] args) {
        launch(args);
    }

    public void solve(SimpleStringProperty terminalBuffer) {


        // System.out.println(terminalBuffer.get());
        if (currentSolver != null) {
            terminalBuffer.setValue("_______________________\n");

            terminalBuffer.setValue("Berechne " + getItems().size() + " Gegenstände und " + getCapacity().get() + "kg Maximalgewicht mit " + currentSolver.getName() + "...\n");


            computing = true;

            executor.submit(() -> {

                long time = System.currentTimeMillis();
                currentSolver.setCapacity(Integer.valueOf(capacity.get()));
                currentSolver.setItems(Converter.ItemsFXToItems(items));
                currentSolver.solve();
                Result result = currentSolver.getResult();
                result.setComputingTime(System.currentTimeMillis() - time);
                computing = false;
                Platform.runLater(() -> {

                    terminalBuffer.setValue("Rechenzeit: " + result.getComputingTime() + "ms");
                    terminalBuffer.setValue("Maximaler Wert: " + result.getMaxValue() + "€");
                    terminalBuffer.setValue("Gewicht: " + result.getWeight() + "kg\n");
                    terminalBuffer.setValue(result.getItemSelection().size() + " ausgewählte Gegenstände: ");

                    if (result.getItemSelection().size() > 0) {


                        showResultWindow(result);

                        int index = 1;
                        StringBuilder itemListString = new StringBuilder();
                        itemListString.append(String.format("+%78s+\n", " ").replaceAll(" ", "-"));
                        itemListString.append(String.format("| %-10s| %-40s| %-10s| %-10s |\n", "Nummer", "Name", "Gewicht", "Wert"));
                        itemListString.append(String.format("+ %-10s+ %-40s+ %-10s+ %-10s +\n", " ", " ", " ", " ").replaceAll(" ", "-"));

                        for (Item item : result.getItemSelection()) {

                            itemListString.append(String.format("| %-10s| %-40s| %-10s| %-10s |\n", index, item.getName(), item.getWeight() + "kg", item.getValue() + "€"));
                            itemListString.append(String.format("+ %-10s+ %-40s+ %-10s+ %-10s +\n", " ", " ", " ", " ").replaceAll(" ", "-"));
                            //itemListString.append(String.format("+%68s+\n", " ").replaceAll(" ", "-"));
                            index++;
                        }
                        terminalBuffer.set(itemListString.toString());
                        //terminalBuffer.setValue("_______________________\n");

                    }
                });

            });


        } else {
            terminalBuffer.setValue("Solver auswählen und laden!\n");

        }

    }

    public ObservableList<ItemFX> getSelectedItems() {
        return Converter.ItemsToItemsFX(currentSolver.getItemSelection());
    }

    public ObservableList<ItemFX> getItems() {
        return items;
    }


    public KnapsackSolver getCurrentSolver() {
        return currentSolver;
    }

    public void setCurrentSolver(KnapsackSolver solver) {
        currentSolver = solver;
    }

    @Override
    public void start(Stage primaryStage) {

        Font.loadFont(getClass().getResource("/GUI/View/Fonts/Roboto-Regular.ttf").toExternalForm(), 12);
        Font.loadFont(getClass().getResource("/GUI/View/Fonts/UbuntuMono-R.ttf").toExternalForm(), 12);


        this.primaryStage = primaryStage;
        primaryStage.setTitle("Rucksackproblem - Löser");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Gui/View/Images/icon.png")));


        primaryStage.setResizable(false);


        initRootLayout();

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/View/MainGui.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);


            String css = getClass().getResource("/GUI/View/CSS/style.css").toExternalForm();
            scene.getStylesheets().add(css);

            primaryStage.setScene(scene);
            primaryStage.show();


            // Give the controller access to the main app.
            MainGuiController controller = loader.getController();
            controller.setMainApp(this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showResultWindow(Result result) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/GUI/View/ResultView.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Resultat");
            dialogStage.setResizable(false);
            dialogStage.initOwner(this.getPrimaryStage());
            Scene scene = new Scene(page);
            String css = getClass().getResource("/GUI/View/CSS/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ResultViewController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setResult(result);

            // Show the dialog and wait until the user closes it
            dialogStage.show();

            // FXCollections.sort(mainApp.getItems(),Comparator.comparingInt(ItemFX::getWeight));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public SimpleStringProperty getCapacity() {
        return this.capacity;
    }

    public boolean isComputing() {
        return computing;
    }


    public void loadSolver(String className, SimpleStringProperty terminalBuffer) {
        Class<?> clazz = null;
        KnapsackSolver solver;
        try {
            clazz = Class.forName("Solver." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Constructor<?> ctor = null;
        try {
            if (clazz != null) {
                ctor = clazz.getConstructor();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            if (ctor != null) {
                solver = (KnapsackSolver) ctor.newInstance();

                setCurrentSolver(solver);
                solver.setOutputBuffer(terminalBuffer);
                terminalBuffer.setValue("=== " + solver.getName() + " geladen. ===");
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public void createNewInstance() {
        Platform.runLater(() ->
                new MainApp().start(new Stage())

        );
    }

    public void exit() {
        Platform.exit();
        System.exit(0);
    }
}
