package com.example.cs370;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Dont Edit this Code UNLESS NECESSARY
 *
 *
 * This code is for the javaFX initilialize
 *
 * This code should Not be touched
 *
 *  Alexis Mendoza
 *
 */

public class Employee extends Application {

    private static Stage s1; // Creating a stage


    /**
     *
     * Start Method this methoth starts stage
     * and set up the startup of java fx screen
     *
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        s1 = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Employee.class.getResource("page1.fxml"));
        stage.setResizable(false); // dont change size
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // setting size of the screen
        stage.setTitle("Employee Database"); // setting the title
        stage.setScene(scene);
        stage.show();
    } // end public


    // This function allows to change the sceen on the screen

    /**
     * newScene
     *
     * This method is called to inizialize a new screen when is required
     *
     *
     * @param x
     * @throws IOException
     */
    public void newScene(String x) throws IOException{

        Parent i = FXMLLoader.load(getClass().getResource(x));
        s1.getScene().setRoot(i);

    } // end newScene


    /**
     *Main
     * this method will call launch to start the program javafx
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
} // end main