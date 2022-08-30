package com.example.cs370;
import java.io.FileWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import  java.io.*;
import javafx.fxml.Initializable;

public class timeOffEmployeeController implements Initializable {


    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField reasonText;
    @FXML
    private Button  submitButton;
    @FXML
    private TextArea historyText;
    @FXML
    private Button back;


    /**
     * Method initialize
     * This method allow the program to run code at the start of the fxml file
     *
     * @param location
     * @param resources
     */

    public void initialize(URL location, ResourceBundle resources) {
        try {

            existFile();
            recordHistory(EmployeeController.geteID());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * End initialize
     */


    /**
     * Constructor
     *
     * @throws IOException
     */
    public timeOffEmployeeController()  throws IOException  {

        existFile();

    }
    /**
     * End Constructor
     */


    /**
     * Method existFile
     *
     * This method will check the directory if exist,
     * if true program dont do nothing
     * if false program builds the file and directory
     *
     * @throws IOException
     */
    public void existFile() throws IOException {

        File dir = new File("./timeOffRequest/" ); // The format will create the path
        File csvFileName = new File("./timeOffRequest/"+"/requestRecord.csv"); // Creates the file of the csv name

        if (dir.exists() == false || csvFileName.exists() == false){

            dir.mkdir();
            csvFileName.createNewFile();

        }

    }


    /**
     * Method submitDay
     * This method will Submit the Requested DAY OFF into the csv File in the expected input
     *
     * @throws IOException
     */
    public void submitDay() throws IOException {

        FileWriter writer = new FileWriter("./timeOffRequest/" + "requestRecord.csv", true);
        //Declaring FileWriter
        StringBuilder builder = new StringBuilder();//declaring a stringBuilder
        Scanner scany = new Scanner (new File (".\\src\\main\\resources\\com\\example\\cs370\\users.csv"));
        //Declaring a Scanner
        String storeInfo = ""; //Decalring a String
        String important[]; // Declaring a String
        String last = "";   //Declaring a String
        String first = "";  //Declaring a Stirng

        /*
            While Loop Designed to get the username of the employee Currently Loged In
         */
        while(scany.hasNext()) {

            storeInfo = scany.nextLine();
            important = storeInfo.split(",");

                    if (important[2].equals(EmployeeController.geteID()) ){

                        last = important[5];
                        first = important[6];
                        break;

                    }// end if

        }// end While

        scany.close();

        LocalDate storeDate = datePicker.getValue();    //Getting the date
        String reason = reasonText.getText();       //Getting the String Reason

        /**
         * Next String Builder will build a string with the next Format
         * Date,EID,First Name,Last Name,Reason, P OR Y OR N ,
         * P = Pending
         * Y = Approved
         * N = Decline
         *
         * Because we are on Employee Side This will set the last value always as P
         */

        builder.append(storeDate.toString());
        builder.append(",");
        builder.append(EmployeeController.geteID());
        builder.append(",");
        builder.append(first);
        builder.append(",");
        builder.append(last);
        builder.append(",");
        builder.append(reason);
        builder.append(",");
        builder.append("P\n");
        writer.write(builder.toString());
        writer.close();


        recordHistory(EmployeeController.geteID()); //recordHistory is called to update the textArea

        writer.close(); // Closing File Writer

    }
    /**
     *  End submitDay
     */

    /**
     * Method recordHistory
     *
     * This method is Designed to run when the fxml page is open
     * This method will print all the record of days approved deny or pending
     * @param eIDLocal
     * @throws IOException
     */
    public void recordHistory(String eIDLocal) throws IOException {
        File csvFileName = new File("./timeOffRequest/"+"/requestRecord.csv");
        // Creates the file of the csv name
        Scanner scanner = new Scanner(csvFileName); // Declaring a Scanner
        StringBuilder record = new StringBuilder();//Declaring a StringBuilder
        String data[];//Declaring String Array

        /*
         *The next While loop is designed to collect only the request from the current eID log in
         */
        while(scanner.hasNextLine() != false){

            data = scanner.nextLine().split(",");

                if (data[1].equals(eIDLocal)){

                    record.append("Date: " + data[0] + " Reason: " + data[4] + " Status: ");

                    if(data[5].equals("P")){

                        record.append("Pending\n");

                    }
                    else if (data[5].equals("N")){

                        record.append("Declined\n");

                    }
                    else if (data[5].equals("Y")){


                        record.append("Approved\n");

                    }

            }// end iif

        } // end while

        historyText.setText(record.toString()); //Printing expected output

    }
    /**
     * End recordHistory
     */


    /**
     * Mehtod backAction
     * This method is designed to return to the previous fxml Page
     *
     * @param Event
     * @throws IOException
     */
    public void backAction (ActionEvent Event) throws IOException{

        Employee scene = new Employee();

        scene.newScene("page2.fxml");

    }
    /**
     * End backAction
     */
}
