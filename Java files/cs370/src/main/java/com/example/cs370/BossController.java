package com.example.cs370;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.io.*;
import java.util.*;

/**
 * Class BossController
 * This class is to control the page Boss.fxml actions
 *
 */
public class BossController {

    /**
     * Empty Constructor
     */
    public BossController(){

    } // end boss controller

    /**
     * Declaring required FXML data required for
     * the items inside the FXML file
     */
    @FXML
    private Button activeE;
    @FXML
    private Button gPaystub;
    @FXML
    private Button wSchedule;
    @FXML
    private Button tOffR;
    @FXML
    private Button tSEditor;
    @FXML
    private Button lOut;
    @FXML
    private Button aRE;

    //end of the fxml declarations


    /**
     * Method logOut
     * This method will create a newScene to display the login page (page1.fxml)
     * This method is required to call flushid to remove the data stored in flushid
     * @throws IOException
     */
    public void logout() throws IOException{

        Employee scene = new Employee(); // creating a scene
        scene.newScene("page1.fxml"); //  calling newScene
        EmployeeController.flushID();    //  calling flushID

    } // end of logOut

    /**
     *This method is to open the scene add.fxml
     * @throws IOException
     */
    public void addEmpScreen(ActionEvent event) throws IOException {

        Employee canvas = new Employee(); // end canvas
        canvas.newScene("add.fxml"); // throw screen

    } // end addEmpScreen

    /**
     * bossPaystubScreen
     * This method will open the bossPaystub.fxml at the event of button press
     *
     * @param event
     * @throws IOException
     */
    public void bossPaystubScreen(ActionEvent event) throws IOException {


        Employee canvas = new Employee();
        canvas.newScene("bossPaystub.fxml");


    }

    /**
     * End Method
     */



    /**
     * timeOffScrene
     * This method will open the timeOff Screen at the event of a button press
     *
     * @param event
     * @throws IOException
     */

    public void timeOffScreen(ActionEvent event) throws IOException{

        Employee scene = new Employee();
        scene.newScene("timeOffBoss.fxml");


    }

    /**
     * End of method
     */



    public void scheduleScreen(ActionEvent event) throws IOException{

        Employee scene = new Employee();
        scene.newScene("bossSchedule.fxml");


    }


    public void scheduleGeneratorScreen(ActionEvent event) throws  IOException{

        Employee scene = new Employee();
        scene.newScene("randomSchedule.fxml");




    }

}
