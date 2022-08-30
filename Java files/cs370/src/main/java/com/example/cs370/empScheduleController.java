package com.example.cs370;

import java.io.FileNotFoundException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.io.IOException;
import java.io.File;

import javafx.fxml.Initializable;



public class empScheduleController implements Initializable {

    /**
     * FXML DECLARAITONS
     *
     *
     */

    @FXML
    private DatePicker datePicker;
    @FXML
    private Button searchButton;
    @FXML
    private Label   inSunday;
    @FXML
    private Label   inMonday;
    @FXML
    private Label   inTuesday;
    @FXML
    private Label   inWed;
    @FXML
    private Label   inThursday;
    @FXML
    private Label   inFriday;
    @FXML
    private Label   inSaturday;
    @FXML
    private Label   outSunday;
    @FXML
    private Label   outMonday;
    @FXML
    private Label   outTuesday;
    @FXML
    private Label   outWed;
    @FXML
    private Label   outThursday;
    @FXML
    private Label   outFriday;
    @FXML
    private Label   outSaturday;
    @FXML
    private Label   dateSunday;
    @FXML
    private Label   dateMonday;
    @FXML
    private Label   dateTuesday;
    @FXML
    private Label   dateWed;
    @FXML
    private Label   dateThursday;
    @FXML
    private Label   dateFriday;
    @FXML
    private Label   dateSaturday;
    @FXML
    private Label   errorLabel;
    @FXML
    private Button  backButton;


    /**
     * Empty Consturctor
     *
     *
     */
   public empScheduleController() {

    }


    /**
     * Method goBack
     * This method will allow to go to the previous screen
     *
     * @throws IOException
     */
    public void goBack()throws IOException{

       Employee scene = new Employee();
       scene.newScene("page2.fxml");


    }
    // end goBack


    /**
     *
     * Mehtod initialize
     * This method fills the data on the fxml screen when open
     *
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            startData();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Method startData
     *
     * This method is called on initialize
     * Method will fill the data at the start of the fxml File
     *
     * @throws IOException
     */
    public void startData() throws  IOException{

       String eID = EmployeeController.geteID();

       int week = 0;

       week = getTodayWeek();

       errorLabel.setText(""); // No error cleaning

        File csvFileName = new File("./scheduleData/" + eID + "/" + getTodayWeek() +"_" + getTodayYear() + ".csv"); // Creates the file of the csv name

        try {
            writeData(csvFileName);
        }
        catch (IOException e){

            errorLabel.setText("No schedule Found");

            dateSunday.setText("");
            inSunday.setText("");
            outSunday.setText("");
            dateMonday.setText("");
            inMonday.setText("");
            outMonday.setText("");
            dateTuesday.setText("");
            inTuesday.setText("");
            outTuesday.setText("");
            dateWed.setText("");
            inWed.setText("");
            outWed.setText("");
            dateThursday.setText("");
            inThursday.setText("");
            outThursday.setText("");
            dateFriday.setText("");
            inFriday.setText("");
            outFriday.setText("");
            dateSaturday.setText("");
            inSaturday.setText("");
            outSaturday.setText("");



        }
        return;

    }

    // end startData


    /**
     * Method writeData
     *
     * This method will change the data on the fxml file with a given csv File
     * The expected csv file is a schedule file in a format xx_xxxx.csv
     * example is 4_2022.csv
     *
     * @param csv
     * @throws IOException
     */
    public void writeData(File csv) throws IOException{


        Scanner scan =  new Scanner(csv);

        String sunday[] = scan.nextLine().split(",");
            dateSunday.setText(sunday[0]);
            inSunday.setText(sunday[1]);
            outSunday.setText(sunday[2]);
        String monday[] = scan.nextLine().split(",");
            dateMonday.setText(monday[0]);
            inMonday.setText(monday[1]);
            outMonday.setText(monday[2]);
        String tuesday[] = scan.nextLine().split(",");
            dateTuesday.setText(tuesday[0]);
            inTuesday.setText(tuesday[1]);
            outTuesday.setText(tuesday[2]);
        String wed[] = scan.nextLine().split(",");
            dateWed.setText(wed[0]);
            inWed.setText(wed[1]);
            outWed.setText(wed[2]);
        String thursday[] = scan.nextLine().split(",");
            dateThursday.setText(thursday[0]);
            inThursday.setText(thursday[1]);
            outThursday.setText(thursday[2]);
        String friday[] = scan.nextLine().split(",");
            dateFriday.setText(friday[0]);
            inFriday.setText(friday[1]);
            outFriday.setText(friday[2]);
        String saturday[] = scan.nextLine().split(",");
            dateSaturday.setText(saturday[0]);
            inSaturday.setText(saturday[1]);
            outSaturday.setText(saturday[2]);
    scan.close();

    return;

    }

    // end writeData


    /**
     * Method getTodayWeek
     * This method is called when startData
     *
     * This method is design to get the week number of the system is on the moment
     * this method is necessary to correct functionality of startData
     *
     * @return Current System Week Number
     */
    public int getTodayWeek(){

        int weekOfYear;

            LocalDate date = LocalDate.now();
            Locale locale = Locale.US;
            weekOfYear = date.get(WeekFields.of(locale).weekOfWeekBasedYear());
            errorLabel.setText(""); // no error



        return weekOfYear;

    }
    //end getTodayWeek

    /**
     * Method getTodayYear
     *
     * This method is design to get the year number of the system is on the moment
     * this method is necessary to correct functionality of startData
     *
     * @return System current year number
     */
    public int getTodayYear(){

       int weekOfYear;

        LocalDate date = LocalDate.now();
        Locale locale = Locale.US;
        weekOfYear = date.get(WeekFields.of(locale).weekBasedYear());
        errorLabel.setText(""); // no error

       return weekOfYear;


    }

    //end getTodayYear


    /**
     * Method lookWeek
     *
     * This method is design to work with the DatePicker
     * to look a schedule from a selected Date
     *
     * @param event
     * @throws IOException
     */
    public void lookWeek (ActionEvent event) throws IOException {

       String eID = EmployeeController.geteID();


       File csvFileName = new File("./scheduleData/" + eID + "/" + getWeek() + "_" + getYear() + ".csv"); // Creates the file of the csv name

        try {
            writeData(csvFileName);
        }
        catch (IOException e){


            errorLabel.setText("Week Dont Exist");

        }

    }

    //end lookWeek

    /**
     * Method getWeek
     *
     * This method will return the week number from the selected date on the datePicker
     *
     * @return week number from the selected Date
     */

    public int getWeek() {

        int weekOfYear;
        try {
            LocalDate date = datePicker.getValue();
            Locale locale = Locale.US;
            weekOfYear = date.get(WeekFields.of(locale).weekOfWeekBasedYear());
            errorLabel.setText(""); // no error

        } catch (NullPointerException e) {

            errorLabel.setText("Select Date");

            return 0;
        }

        return weekOfYear;

    }

    //end getWeek


    /**
     * Method getYear
     *
     * This method will return the year number from the selected date on the DatePicker
     *
     * @return year number from the DatePicker
     */
    public int getYear(){

        int year = 0;

        try {
            LocalDate date = datePicker.getValue();
            Locale locale = Locale.US;
            year = date.get(WeekFields.of(locale).weekBasedYear());
            errorLabel.setText(""); // no error

        }
        catch(NullPointerException e){

            errorLabel.setText("Select Date");

            return 0 ;
        }


        return year;
    }

    //end getYear


    }
