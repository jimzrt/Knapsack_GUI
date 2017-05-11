package GUI.Util;

import javafx.application.Platform;
import javafx.scene.control.TextField;

/**
 * Created by james on 11.05.2017.
 */
public class Validator {

    public static void createSimpleNumberValidator(TextField field) {

        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]+")) {
                Platform.runLater(() -> {
                    if (oldValue.equals("")) {
                        field.textProperty().set("0");
                    } else {
                        field.textProperty().set(oldValue);
                    }
                });
            }
        });

    }
}
