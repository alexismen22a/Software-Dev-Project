package com.example.cs370;

import java.io.FileWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import  java.io.*;
import javafx.fxml.Initializable;


public class ePaystubController implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {
        try {
            paystubBuilder();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   private float workedHours ;
   private float compensation;
   private float hourlyWage;

    @FXML
    private Button back;
    @FXML
    public Label nameInfo = new Label();
    @FXML
    public Label   detailInfo = new Label();
    @FXML
    public Label   netPay = new Label();
    @FXML
    private Label   netText;
    @FXML
    private Label paystubLogo;

    /**
     * Empty Constructor
     *
     *
     */
    public ePaystubController() throws IOException {



        employeeHourCalc();

       paystubBuilder();
    }


    /***
     *Employee HOUR CALCULATION FUNCTION
     * THIS METHOD IS DESIGN TO READ THE CLOCKTIME.CSV DATA AND STORE THE CALCULATION OF
     * IN A PRIVATE VARIABLE NAMED WORKEDHOURS type FLOAT
     *
     *
     */

    public  void employeeHourCalc() throws IOException{

        Scanner scan = new Scanner(new File("./time/" + EmployeeController.geteID() + "/clockTime.csv"));

        String line = "" ; // creating a string Lasline to store it

        float clockinTime = 0;
        float clockOutTime = 0;
        float lunchInTime =0;
        float lunchBackTime =0;



        while(scan.hasNextLine()){ // while loop to store lastLine

            line = scan.nextLine();

            String array[] = line.split(",");

            clockinTime = Float.valueOf(array[1]).floatValue();
            clockOutTime = Float.valueOf(array[4]).floatValue();
             lunchInTime = Float.valueOf(array[2]).floatValue();
             lunchBackTime =Float.valueOf(array[3]).floatValue();

            workedHours = workedHours + ((clockOutTime - clockinTime) - (lunchBackTime - lunchInTime));

            clockinTime = 0;
            clockOutTime = 0;

        } // end of while loop

        Scanner scany = new Scanner (new File (".\\src\\main\\resources\\com\\example\\cs370\\users.csv"));

        int x = 0;
        String buffer = "";

        while( x != Integer.valueOf(EmployeeController.geteID()).intValue()){

           buffer =  scany.nextLine();
            x++;

        }

        String otherArray[] = buffer.split(",");


        // If data is HH:MM, means you are clock in so dont allow another clock in write on the csv file

        System.out.println(workedHours);

        compensation = workedHours * (Float.valueOf(otherArray[4]).floatValue());

        hourlyWage = Float.valueOf(otherArray[4]).floatValue();

        System.out.println(compensation);
        return ;



    }

    /**
     *Paystub Builder
     *
     *
     *
     */

    public void paystubBuilder() throws IOException{


        Scanner scany = new Scanner (new File (".\\src\\main\\resources\\com\\example\\cs370\\users.csv"));

        int x = 0;
        String buffer = "";

        while( x != Integer.valueOf(EmployeeController.geteID()).intValue()){

            buffer =  scany.nextLine();
            x++;

        }

        String array[] = buffer.split(",");

        DecimalFormat df = new DecimalFormat(".##");


        netPay.setText("$" +  df.format(compensation));
        nameInfo.setText(array[5] + " " + array [6]);
        detailInfo.setText("Worked Hours:        " + df.format(workedHours) + "\nHourly Compensation: $" + df.format(hourlyWage));



    }



    /**
     * This Funciton is to return to the prevois screen
     *
     * @param event
     * @throws IOException
     */

    public void backAction(ActionEvent event) throws IOException{
        Employee canvas = new Employee(); // creating a new canvas
        canvas.newScene("page2.fxml"); // Displaying fxml2.fxml

    } // end backAction





}
