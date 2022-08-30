package com.example.cs370;

import java.io.FileWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
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



public class bossScheduleController implements Initializable{

    /**
     * FXML Declarations
     *
     */

    @FXML
    private ChoiceBox<String> empPicker = new ChoiceBox<>();
    @FXML
    private Button  searchButton;
    @FXML
    private DatePicker  datePicker;
    @FXML
    private TextField   saturdayStart;
    @FXML
    private TextField   saturdayEnd;
    @FXML
    private TextField   sundayStart;
    @FXML
    private TextField   sundayEnd;
    @FXML
    private TextField   mondayStart;
    @FXML
    private TextField   mondayEnd;
    @FXML
    private TextField   tuesdayStart;
    @FXML
    private TextField   tuesdayEnd;
    @FXML
    private TextField   wedStart;
    @FXML
    private TextField   wedEnd;
    @FXML
    private TextField   thursdayStart;
    @FXML
    private TextField   thursdayEnd;
    @FXML
    private TextField   fridayStart;
    @FXML
    private TextField   fridayEnd;
    @FXML
    private Label   saturdayDate;
    @FXML
    private Label   sundayDate;
    @FXML
    private Label   mondayDate;
    @FXML
    private Label   tuesdayDate;
    @FXML
    private Label   wedDate;
    @FXML
    private Label   thursdayDate;
    @FXML
    private Label   fridayDate;
    @FXML
    private Button  updateButton;
    @FXML
    private Button backButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Button generateButton;




    /**
     * Empty
     * bossScheduleController Constructor
     *
     */

    public bossScheduleController(){




    } // end Constructor


    /**
     *  Mehtod initialize
     * This method fills the data on the fxml screen
     *
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            startChoiceBox();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //end initialize

    /**
     * Method startChoiceBox
     *This method starts the ChoiceBox with data to be able to select an Employee
     *
     *
     * @throws IOException
     */
    public void startChoiceBox() throws  IOException{


        Scanner scany = new Scanner (new File (".\\src\\main\\resources\\com\\example\\cs370\\users.csv"));
        String storeTempString ;
        String storeInArray[];

     while (scany.hasNext()){


         storeTempString= scany.nextLine();

         storeInArray = storeTempString.split(",");

         if (storeInArray[3].equals("1")) {
             empPicker.getItems().add(storeInArray[6] + " " + storeInArray[5] +" " +  storeInArray[2]);

             generateDirectory(storeInArray[2]);

         }
     }
        empPicker.setValue("Select Employee");

        scany.close();


    }

    //End startChoiceBox

    /**
     * Mehtod getWeek
     * This method will get the week from the datePicker on the javafx
     *
     * @return week number
     */

    public int getWeek(){

        int weekOfYear;
        try {
            LocalDate date = datePicker.getValue();
            Locale locale = Locale.US;
            weekOfYear = date.get(WeekFields.of(locale).weekOfWeekBasedYear());
            errorLabel.setText(""); // no error 

        }
        catch(NullPointerException e){

            errorLabel.setText("Missing Week");

            return 0 ;
        }

        return weekOfYear;

    }

    //end getWeek


    /**
     * Method getYear
     * This method will get the year from the DatePicekr
     *
     * @return year
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

            errorLabel.setText("Missing Week");

            return 0 ;
        }


        return year;
    }

    //end getYear


    /**
     * Method datesOfWeek
     * This method will return a array of dates to create the format of the csv file what will store the data
     *
     * @param week
     * @return array of string with the dates of the week in format MM/dd
     */

    public String[] datesOfWeek(int week){

        String data [] = new String[7];
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR , getYear());
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        data[0] = format.format(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        data[1] = format.format(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        data[2] = format.format(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        data[3] = format.format(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        data[4] = format.format(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        data[5]= format.format(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        data[6] = format.format(cal.getTime());

        return data;

    }

    /**
     * Method getData
     * This method will fill up the spaces of the blank spaces with the specific week selected from the date picker and
     * the correct Employee
     *
     * @param event
     * @throws IOException
     */
    public void getData(ActionEvent event) throws IOException{


        if (getYear() == 0 || getWeek() == 0 || empPicker.getValue().equals("Select Employee")){

            errorLabel.setText("Input Data");


        }

        else {

         String storeInArray []  = empPicker.getValue().split(" ");
            errorLabel.setText("");
            File csvFileName = new File("./scheduleData/" + storeInArray[2] + "/" + getWeek() +"_" + getYear() + ".csv"); // Creates the file of the csv name
            if(csvFileName.exists() == false) {

                errorLabel.setText("Week Dont exist");

                return;

            }
            errorLabel.setText(""); // no error 
            Scanner scan = new Scanner(csvFileName);

            String sunday[] = scan.nextLine().split(",");
            sundayDate.setText(sunday[0]);
            sundayStart.setText(sunday[1]);
            sundayEnd.setText(sunday[2]);

            String monday[] = scan.nextLine().split(",");
            mondayDate.setText(monday[0]);
            mondayStart.setText(monday[1]);
            mondayEnd.setText(monday[2]);

            String tuesday[] = scan.nextLine().split(",");
            tuesdayDate.setText(tuesday[0]);
            tuesdayStart.setText(tuesday[1]);
            tuesdayEnd.setText(tuesday[2]);

            String wed[] = scan.nextLine().split(",");
            wedDate.setText(wed[0]);
            wedStart.setText(wed[1]);
            wedEnd.setText(wed[2]);

            String thursday[] = scan.nextLine().split(",");
            thursdayDate.setText(thursday[0]);
            thursdayStart.setText(thursday[1]);
            thursdayEnd.setText(thursday[2]);

            String friday[] = scan.nextLine().split(",");
            fridayDate.setText(friday[0]);
            fridayStart.setText(friday[1]);
            fridayEnd.setText(friday[2]);

            String saturday[] = scan.nextLine().split(",");
            saturdayDate.setText(saturday[0]);
            saturdayStart.setText(saturday[1]);
            saturdayEnd.setText(saturday[2]);


            scan.close();

        }

    }

    //End getData


    /**
     * Method generateDirectory
     * This method will generate the directory of all the employees at the first time the code is generated
     * @param eID
     */
    public void generateDirectory(String eID){

        File dir = new File("./scheduleData/" + eID + "/" ); // The format will create the path

        /**
         * If Statement to check if directories exist
         */
        if (dir.exists() == false ){
            dir.mkdir();
        } // end if

    } // end generateDirectory


    /**
     * Mehtod generateWeek
     * This method will create the week of each employee when a week schedule is generated
     * Will fill up the data of csv file in the next format
     * DATE,00,00
     * This metod initializes the csv files with no set time and checks for days off
     *
     * @throws IOException
     */
    public void generateWeek() throws IOException{


        Scanner scany = new Scanner (new File (".\\src\\main\\resources\\com\\example\\cs370\\users.csv"));
        String storeTempString ;
        String storeInArray[];

        if (getWeek() != 0 && getYear() != 0) {

            while (scany.hasNext()) {


                storeTempString = scany.nextLine();

                storeInArray = storeTempString.split(",");

                if (storeInArray[3].equals("1")) {

                    File csvFileName = new File("./scheduleData/" + storeInArray[2] + "/" + getWeek() +"_" + getYear() + ".csv"); // Creates the file of the csv name

                    if (csvFileName.exists() != true) {
                        csvFileName.createNewFile();
                        errorLabel.setText(""); // no error 

                        FileWriter writer = new FileWriter(csvFileName);
                        StringBuilder sb = new StringBuilder();


                        String[] storeDays = datesOfWeek(getWeek());

                        if (isDayOff(storeDays[0] , storeInArray[2] ) == false) {

                            sb.append(storeDays[0] + "," + "00" + "," + "00");
                            sb.append("\n");

                        }
                        else {

                            sb.append(storeDays[0] + "," + "OFF" + "," + "OFF");
                            sb.append("\n");

                        }

                        if (isDayOff(storeDays[1] , storeInArray[2] ) == false) {
                            sb.append(storeDays[1] + "," + "00" + "," + "00");
                            sb.append("\n");
                        }
                        else {

                            sb.append(storeDays[1] + "," + "OFF" + "," + "OFF");
                            sb.append("\n");

                        }


                        if (isDayOff(storeDays[2] , storeInArray[2] ) == false) {
                            sb.append(storeDays[2] + "," + "00" + "," + "00");
                            sb.append("\n");
                        }
                        else {

                            sb.append(storeDays[2] + "," + "OFF" + "," + "OFF");
                            sb.append("\n");

                        }

                        if (isDayOff(storeDays[3] , storeInArray[2] ) == false ) {
                            sb.append(storeDays[3] + "," + "00" + "," + "00");
                            sb.append("\n");
                        }
                        else {

                            sb.append(storeDays[3] + "," + "OFF" + "," + "OFF");
                            sb.append("\n");


                        }
                        if (isDayOff(storeDays[4] , storeInArray[2] ) == false) {
                            sb.append(storeDays[4] + "," + "00" + "," + "00");
                            sb.append("\n");
                        }
                        else {

                            sb.append(storeDays[4] + "," + "OFF" + "," + "OFF");
                            sb.append("\n");


                        }
                        if (isDayOff(storeDays[5] , storeInArray[2] ) == false) {
                            sb.append(storeDays[5] + "," + "00" + "," + "00");
                            sb.append("\n");
                        }
                        else {

                            sb.append(storeDays[5] + "," + "OFF" + "," + "OFF");
                            sb.append("\n");

                        }

                        if (isDayOff(storeDays[6] , storeInArray[2] ) == false) {
                            sb.append(storeDays[6] + "," + "00" + "," + "00");
                        }

                        else {

                            sb.append(storeDays[6] + "," + "OFF" + "," + "OFF");

                        }
                        writer.write(sb.toString());
                        writer.flush();
                        writer.close();

                        writer.close();

                    }

                    else {

                        errorLabel.setText("Week On Record");
                        return;

                    }

                }
            }

        }

        else if (getWeek() == 0){

            errorLabel.setText("Input Week");
            return;

        }

    }

    //End generate week


    /**
     * Method isDayOff
     * This method is to check if a specific date is a day of of the week and it returns true if is a aprrove day of
     * or false if is Not Approve, Pending, Is not requested
     *
     * @param date
     * @param eID
     * @return
     * @throws IOException
     */

    public boolean isDayOff(String date , String eID) throws  IOException{

        File csvFileName = new File("./timeOffRequest/"+"/requestRecord.csv"); // Creates the file of the csv name
        Scanner scanner = new Scanner(csvFileName); //Declaring a Scanner
        String data[];//Declaring a String Array

        SimpleDateFormat form = new SimpleDateFormat("MM/dd");

        String convert ;
        String temp[] ;

        /*
            While loop
            This While loop is designed to look for Letter "Y"
            If Found the Line will be added into the StringBuilder to create the expected Output
         */

        while(scanner.hasNextLine() != false){

            data = scanner.nextLine().split(",");   //Sptiing the csv into array

            convert = data[0];
            temp = convert.split("-");
            convert = temp[1] +"/"+ temp[2];



            if (data[5].equals("Y") && data[1].equals(eID) && convert.equals(date)  ){

               return true;

            } // end if


        } // end while

        scanner.close(); // Closing the scanner

        return false;

    }

    //end isDayOff

    /**
     * Method updateSchedule
     * This method will write the changes on the time of a specific week
     * If is a day Off it will not allow the user edit the day off
     *
     * @throws IOException
     */
    public void updateSchedule () throws IOException{

        String storeInArray[] = empPicker.getValue().split(" ");

        SimpleDateFormat form = new SimpleDateFormat("HH:mm");

        File csvFileName = new File("./scheduleData/" + storeInArray[2] + "/" + getWeek() +"_" + getYear() + ".csv"); // Creates the file of the csv name
        Scanner scan = new Scanner(csvFileName);
        StringBuilder sb = new StringBuilder();

        String data = scan.nextLine();
        String line[] = data.split(",");

        if (isDayOff(line[0], storeInArray[2]) == false){

            System.out.println("i reached ");
            sb.append(line[0] + ",");
            sb.append(sundayStart.getText()+",");
            sb.append(sundayEnd.getText()+"\n");


        }
        else {

            sb.append(line[0]+","+line[1]+","+line[2]+ "\n");

        }


        String line2[] = scan.nextLine().split(",");

        if (isDayOff(line2[0], storeInArray[2]) == false){

            sb.append(line2[0] + ",");
            sb.append(mondayStart.getText()+",");
            sb.append(mondayEnd.getText()+"\n");


        }
        else {

            sb.append(line2[0]+","+line2[1]+","+line2[2]+ "\n");

        }

        String line3[] = scan.nextLine().split(",");

        if (isDayOff(line3[0], storeInArray[2]) == false){

            sb.append(line3[0] + ",");
            sb.append(tuesdayStart.getText()+",");
            sb.append(tuesdayEnd.getText()+"\n");


        }
        else {

            sb.append(line3[0]+","+line3[1]+","+line3[2]+ "\n");

        }
        String line4[] = scan.nextLine().split(",");

        if (isDayOff(line4[0], storeInArray[2]) == false){

            sb.append(line4[0] + ",");
            sb.append(wedStart.getText()+",");
            sb.append(wedEnd.getText()+"\n");


        }
        else {

            sb.append(line4[0]+","+line4[1]+","+line4[2]+ "\n");

        }
        String line5[] = scan.nextLine().split(",");

        if (isDayOff(line5[0], storeInArray[2]) == false){

            sb.append(line5[0] + ",");
            sb.append(thursdayStart.getText()+",");
            sb.append(thursdayEnd.getText()+"\n");


        }
        else {

            sb.append(line5[0]+","+line5[1]+","+line5[2]+ "\n");

        }
        String line6[] = scan.nextLine().split(",");

        if (isDayOff(line6[0], storeInArray[2]) == false){

            sb.append(line6[0] + ",");
            sb.append(fridayStart.getText()+",");
            sb.append(fridayEnd.getText()+"\n");


        }
        else {

            sb.append(line6[0]+","+line6[1]+","+line6[2]+ "\n");

        }
        String line7[] = scan.nextLine().split(",");

        if (isDayOff(line7[0], storeInArray[2]) == false){

            sb.append(line7[0] + ",");
            sb.append(saturdayStart.getText()+",");
            sb.append(saturdayEnd.getText());


        }
        else {

            sb.append(line7[0]+","+line7[1]+","+line7[2]);

        }

        scan.close();

        System.out.println(sundayStart.getText());

        FileWriter writer = new FileWriter(csvFileName);
        writer.write(sb.toString());
        writer.close();

        return;


    }

    /**
     * goBack Method
     * This method will return to the previous fxml screen
     *
     * @throws IOException
     */

    public void goBack() throws IOException{

        Employee scene = new Employee();
        scene.newScene("boss.fxml");

    }

    //end updateSchedule


}
