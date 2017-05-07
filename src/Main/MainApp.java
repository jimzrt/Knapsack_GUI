package Main;

import Main.Controller.ItemListController;
import Main.Model.ItemFX;
import Main.Util.Converter;
import Solver.ASolver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class MainApp extends Application {

    public SimpleStringProperty capacity = new SimpleStringProperty();
    boolean computing = false;
    ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
        public Thread newThread(Runnable r) {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }
    });
    private Stage primaryStage;
    private Pane rootLayout;
    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<ItemFX> items = FXCollections.observableArrayList();
    private ObservableList<String> solvers;

    private ASolver currentSolver = null;

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
//        items.add(new ItemFX(3, 4));
//        items.add(new ItemFX(4, 5));
//        items.add(new ItemFX(5, 6));
//        items.add(new ItemFX(6, 7));
        capacity.set("15");



        solvers = getSolversByClassName();


        // ISolver solver = new DynamicSolver();
//        for(ISolver solver : solvers){
//            solver.setCapacity(15);
//            solver.setItems(itemsS);
//            solver.solve();
//            System.out.println("Max Value: " + solver.getMaxValueSum());
//        }


    }

    public static void main(String[] args) {
        launch(args);
    }

    public void solve(SimpleStringProperty terminalBuffer) {


        // System.out.println(terminalBuffer.get());
        if (currentSolver instanceof ASolver) {
            terminalBuffer.setValue("Löse " + getItems().size() + " Items und " + getCapacity().get() + "kg Kapazität mit " + currentSolver.getName() + "...\n");

            //TODO:Validation


            computing = true;

            executor.submit(() -> {
                currentSolver.setCapacity(Integer.valueOf(capacity.get()));
                currentSolver.setItems(Converter.ItemsFXToItems(items));
                currentSolver.solve();
                computing = false;
                Platform.runLater(() -> {
                    terminalBuffer.setValue("Maximaler Wert: " + currentSolver.getMaxValueSum());
                    terminalBuffer.setValue("Gewicht: " + currentSolver.getTotalWeight());
                    terminalBuffer.setValue("Ausgewählte Items: " + currentSolver.getItemSelection());
                    terminalBuffer.setValue("_______________________\n");


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

    public ObservableList<String> getSolvers() {
        return solvers;
    }

    ;

    public void setCurrentSolver(ASolver solver) {
        currentSolver = solver;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Rucksackproblem - Löser");
        primaryStage.setResizable(false);
        //setUserAgentStylesheet(STYLESHEET_MODENA);

        initRootLayout();

       // showPersonOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Main/View/sample.fxml"));
            rootLayout = (Pane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);


            String css = getClass().getResource("/Main/View/CSS/style.css").toExternalForm();
            scene.getStylesheets().add(css);

            primaryStage.setScene(scene);
            primaryStage.show();



            // Give the controller access to the main app.
            ItemListController controller = loader.getController();
            controller.setMainApp(this);


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

    private ObservableList<String> getSolversByClassName() {
       // List<String> solvers = new ArrayList<String>();
        String[] solverClassNames = new String[]{"DynamicSolver", "BruteForceSolver", "GreedySolver", "BranchAndBoundSolver"};

//        for(String solverName : solverClassNames) {
//
//            solvers.add(solverName);
//
//        }


//
//        }


        return FXCollections.observableArrayList(solverClassNames);
    }

    public void loadSolver(String className, SimpleStringProperty terminalBuffer) {
        Class<?> clazz = null;
        ASolver solver = null;
        try {
            clazz = Class.forName("Solver." + className);
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
            solver = (ASolver) ctor.newInstance();
            setCurrentSolver(solver);
            solver.setOutputBuffer(terminalBuffer);
            terminalBuffer.setValue("=== " + solver.getName() + " geladen. ===");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
