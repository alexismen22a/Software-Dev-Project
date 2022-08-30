package com.example.cs370;

import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.io.File;

import javafx.fxml.Initializable;


public class bossPaystubController implements  Initializable {

    @FXML
    private ListView<String> listV;
    @FXML
    public Label nameInfo = new Label();
    @FXML
    public Label   detailInfo = new Label();
    @FXML
    public Label   totalPay = new Label();
    @FXML
            public Button back;

    //Declaring a String For use in initialize
    String selected;

    /**
     * Mehtod initialize
     * This method fills the data on the fxml screen when open
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        try {
            listBuilder(); //  call list builder

            listV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String>  some , String s, String t1) {

                    selected = listV.getSelectionModel().getSelectedItem();

                    try {
                        generatePaystub(selected);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //end initializable


    /**
     * Empty Constructor
     *
     *
     */
    public bossPaystubController(){


    }

    /**
     * ListBuilder Method
     *
     * This method will build the list with all the employee names on the List on the fxml File
     *
     * @throws IOException
     */
    public void listBuilder() throws IOException{

        Scanner scany = new Scanner (new File (".\\src\\main\\resources\\com\\example\\cs370\\users.csv"));

        int x = 0;
        String buffer = "";

        while( scany.hasNextLine()){

            buffer =  scany.nextLine();

            String otherArray[] = buffer.split(",");

            if (Integer.valueOf(otherArray[3]) != 2) {
                String actualValues = "" + otherArray[6] + "," + otherArray[5] + "," + otherArray[2];

                listV.getItems().add(actualValues);


            }

        }

    }
    // end listBuilder

    /**
     * Method generatePaystub
     *
     * This method will generate a paystub with a eID extracted from a input String
     *
     *
     * @param info
     * @throws IOException
     */
    public void generatePaystub(String info) throws  IOException{

        String storeInfo[]  = info.split(",");


        Scanner success;

        File  look = new File("./time/" + storeInfo[2] + "/clockTime.csv");

        try {
             success = new Scanner(look);
        }
        catch (IOException E){

            nameInfo.setText("CLOCK RECORD NOT FOUND");
            detailInfo.setText("");
            totalPay.setText("");

            return ;

        }
        Scanner scan = success;

        String line = "" ; // creating a string Lasline to store it

        float clockinTime = 0;
        float clockOutTime = 0;
        float lunchInTime =0;
        float lunchBackTime =0;
        float workedHours = 0;


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
            lunchBackTime = 0 ;
            lunchInTime = 0;

        } // end of while loop

        Scanner scany = new Scanner (new File (".\\src\\main\\resources\\com\\example\\cs370\\users.csv"));

        int x = 0;
        String buffer = "";

        while( x != Integer.valueOf(storeInfo[2])){

            buffer =  scany.nextLine();
            x++;

        }

        String userTemp[] = buffer.split(",");

        float wage = Float.valueOf(userTemp[4]);

        float netPay = wage * workedHours;

        DecimalFormat df = new DecimalFormat(".##");

        nameInfo.setText("" + userTemp[6] +" " + userTemp[5]);

        detailInfo.setText("Worked Hours:        " + df.format(workedHours) + "\nHourly Compensation: $" + df.format(wage));
        totalPay.setText("" + netPay);

    }

    /**
     * Method backActoion
     *
     * This method is to return to the previous screen
     *
     *
     * @param event
     * @throws IOException
     */
    public void backAction(ActionEvent event) throws IOException{
        Employee canvas = new Employee(); // creating a new canvas
        canvas.newScene("boss.fxml"); // Displaying fxml2.fxml

    } // end backAction



}
