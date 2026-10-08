package org.example.csc311_mod4_assigment2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Main JavaFX application for the Card Game - 24.
 * <p>
 * Loads the user interface from {@code card-game.fxml}, applies the
 * {@code styles.css} stylesheet, and shows the main window.
 */
public class CardGameApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load the layout; the FXML file names CardGameController as its controller
        FXMLLoader fxmlLoader = new FXMLLoader(CardGameApplication.class.getResource("card-game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Attach the CSS styling
        scene.getStylesheets().add(
                Objects.requireNonNull(CardGameApplication.class.getResource("styles.css")).toExternalForm());

        stage.setTitle("Card Game - 24");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
