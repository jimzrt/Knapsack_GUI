package Main;

import Main.Controller.ItemListController;
import Main.Model.ItemFX;
import Main.Util.Converter;
import Solver.DynamicSolver;
import Solver.ISolver;
import Solver.Model.Item;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    private Stage primaryStage;
    private Pane rootLayout;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<ItemFX> items = FXCollections.observableArrayList();
    public SimpleIntegerProperty capacity = new SimpleIntegerProperty();
    private ObservableList<String> solvers;

    private ISolver currentSolver = null;

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        items.add(new ItemFX(3, 4));
        items.add(new ItemFX(4, 5));
        items.add(new ItemFX(5, 6));
        items.add(new ItemFX(6, 7));



        solvers = getSolversByClassName();


        // ISolver solver = new DynamicSolver();
//        for(ISolver solver : solvers){
//            solver.setCapacity(15);
//            solver.setItems(itemsS);
//            solver.solve();
//            System.out.println("Max Value: " + solver.getMaxValueSum());
//        }


    }

    public void solve(){
        currentSolver.setCapacity(this.capacity.get());
        currentSolver.setItems(Converter.ItemsFXToItems(items));
        currentSolver.solve();
        System.out.println("Max Value: " + currentSolver.getMaxValueSum());
    }

    public ObservableList<ItemFX> getSelectedItems() {
        return Converter.ItemsToItemsFX(currentSolver.getSelection());
    }

    public ObservableList<ItemFX> getItems() {
        return items;
    }

    public ObservableList<String> getSolvers(){return solvers;}

    public void setCurrentSolver(ISolver solver){currentSolver = solver;};


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

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
            loader.setLocation(MainApp.class.getResource("view/sample.fxml"));
            rootLayout = (Pane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);


            String css = this.getClass().getResource("/Main/View/CSS/style.css").toExternalForm();
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



    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public SimpleIntegerProperty getCapacity() {
        return this.capacity;
    }

    private ObservableList<String> getSolversByClassName() {
       // List<String> solvers = new ArrayList<String>();
        String[] solverClassNames = new String[]{"Solver.DynamicSolver"};

//        for(String solverName : solverClassNames) {
//
//            solvers.add(solverName);
//
//        }


//
//        }


        return FXCollections.observableArrayList(solverClassNames);
    }
}
