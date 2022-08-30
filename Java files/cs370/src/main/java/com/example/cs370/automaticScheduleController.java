package com.example.cs370;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

public class automaticScheduleController implements Initializable {


    /**
     * FXML DECLARATIONS
     *
     *
     */

    @FXML
private DatePicker  datePicker;
@FXML
private Label   errorLabel;
@FXML
private TextField empDay;
@FXML
private TextField   empHour;
@FXML
private TextField   openTime;
@FXML
private TextField   closingTime;
@FXML
private Button  existButton;
@FXML
private Button  generateWeek;
@FXML
private Button  backButton;

//end FXML declarations

    /**
     *  Empty Constructor
     */
    public automaticScheduleController(){

    }

    //end Empty Constructor

    /**
     * Method generateRandomSchedule
     *
     * This method is the main Mehtod to generate a random Schedule from the user requierements
     * Requirements are:
     * Employees required per day
     * How many hours per Employee
     * Opening Time
     * Closing Time
     *
     * This method will create a randomSchedule for All the current Employees
     *
     * @throws IOException
     */
    public void generateRandomSchedule() throws IOException {

    int employeesForDay;
    int timePerDay;
    int oTime = 0;
    int cTime = 0;

        /**
         * try and catch statements are to make sure we get integer values and not letters and not run the code
         *
         *
         */

    try {
         employeesForDay = Integer.valueOf(empDay.getText());
    }
    catch (Exception e){

        errorLabel.setText("Please Input a number");
        return;
    }
    try {
         timePerDay = Integer.valueOf(empHour.getText());
    }
    catch (Exception e) {

        errorLabel.setText("Please input a number");
        return;
    }

    try {
         oTime = Integer.valueOf(openTime.getText());
    }
    catch (Exception e){

        errorLabel.setText("Please Input a Number");
        return;
    }

    try {
         cTime= Integer.valueOf(closingTime.getText());
    }
    catch (Exception e) {


        errorLabel.setText("Please Input a Number ");

    }


    //end try and catch

    //at this point we know the code is good so we clean the error label.
    errorLabel.setText("");


    String temp[];
    int count = 0;
    LinkedList eID = new LinkedList();
    File users = new File(".\\src\\main\\resources\\com\\example\\cs370\\users.csv");
    Scanner scan = new Scanner(users);

        /**
         *
         * Following while loop is to store all the eID from Employees not Employeers
         * and Stored into a LinkedList
         *
         */
    while(scan.hasNext()){

        temp = scan.nextLine().split(",");

        if (temp[3].equals("1")){

            generateDirectory(temp[2]);

            eID.add(temp[2]);
            //count++;

        }

    }
    //End While
    
    //At this point we have all the eID employees  in a LinkedList()


    //Random eID picker and Schedule Maker

    //Declaring required parameters for the correct functionality of the code
    int counter = 0;
    int days = 0;

    LinkedList pass = new LinkedList();
    LinkedList temporal;

    boolean flag = false;
    boolean isEnd = false;

    int resetTime = 0;



    //Following if Statement is to make sure the code runs correcty if a Boss inputs a greater number of employees
    if (employeesForDay > eID.size()){

        employeesForDay = eID.size() ;

    }

    // end if

        /**
         *
         * The Following while loop is the code what generates the random Schedule from eID
         * This following algorithm will create a schedule with the requierements
         *
         *
         **/
    while (days < 7) {

        temporal = new LinkedList(eID);

        while (count < employeesForDay) {

            int randomNumber = (int) (Math.random() * eID.size());

            File csvFileName = new File("./scheduleData/" + eID.get(randomNumber) + "/" + getWeek() + "_" + getYear() + ".csv"); // Creates the file of the csv name

            pass.add(eID.get(randomNumber));

            StringBuilder sb = new StringBuilder();

            String[] dates = datesOfWeek(getWeek());

            if (csvFileName.exists() == true) {

            } else {

                csvFileName.createNewFile();

            }


            //************************************** HERE IS  GOING TO BE THE CODE TO DO THE SCHEDULE GENERATION

            //MATH
            if (flag == false) {
                resetTime = oTime;
                flag = true;
            }

            int x;

            x = oTime;

            x = x + timePerDay;

            if (x > cTime ){

                if(days < 6) {
                    sb.append(dates[days] + ",");
                    sb.append(oTime + ":00," + cTime + ":00\n");
                    isEnd = true;
                }

                else {

                    sb.append(dates[days] + ",");
                    sb.append(oTime +":00," + cTime+":00");

                }

                oTime = resetTime;

            }

            else {

                if(days < 6) {
                    sb.append(dates[days] + ",");
                    sb.append(oTime + ":00," + x + ":00\n");
                    isEnd = true;
                }

                else {

                    sb.append(dates[days] + ",");
                    sb.append(oTime +":00," + x+":00");

                }

                oTime = x;

            }


            if (isDayOff(dates[days], eID.get(randomNumber).toString()) == true){

                sb.delete(0,sb.length());

                if (isEnd == true ) {
                    sb.append(dates[days] + "," + "OFF,OFF\n");
                    isEnd = false;
                }
                else {

                    sb.append(dates[days] + "," + "OFF,OFF");

                }


            }

            //End Math
            eID.remove(randomNumber);

            FileWriter writer = new FileWriter(csvFileName , true);

            writer.write(sb.toString());
            writer.close();

            count++;

        }

        oTime = resetTime;
        flag = false;

        eID.removeAll(temporal);
        eID.addAll(temporal);
        temporal.removeAll(pass);

        pass.clear();

        while (temporal.isEmpty() != true) {

            File csvFileName = new File("./scheduleData/" + temporal.pop() + "/" + getWeek() + "_" + getYear() + ".csv"); // Creates the file of the csv name

            if (csvFileName.exists() == true) {

            }
            else {

                csvFileName.createNewFile();

            }

            StringBuilder sb = new StringBuilder();

            String[] dates = datesOfWeek(getWeek());

            if (days < 6) {
                sb.append(dates[days] + ",");
                sb.append("OFF,OFF\n");
            }

            else {

                sb.append(dates[days] + ",");
                sb.append("OFF,OFF");

            }

            FileWriter writer = new FileWriter(csvFileName , true);
            writer.write(sb.toString());
            writer.close();

        }

            count = 0;
            days++;

        }



    }

    //end generateRandomSchedules

    /**
     * Method datesOfWeek
     *
     * This method with a given Week number will return days of that specific week number in a string array
     * starting from Sunday to Saturday
     *  the output will look 11/24 (month/day)
     * @param week
     * @return
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

    //end datesOfWeek

    /**
     *
     * Method checkIfWeekExist
     * This methid will set up the errorLabel with text to notify the user if the week is going to generate already
     * exist or no
     *
     */
    public void checkIfWeekExist(){

    String eID = "9";
    File csvFileName = new File("./scheduleData/" + eID + "/" + getWeek() +"_" + getYear() + ".csv"); // Creates the file of the csv name

    if (csvFileName.exists() == true){

        errorLabel.setText("An Schedule Already Exist");

    }
    else {

        errorLabel.setText("Schedule dont Exist");

    }

}

    //end checkIfWeekExist



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

    /**
     *
     *Method generateDirectory
     *This method will create a directory with an specific eID input if it dosent exist
     * @param eID
     *
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
     * Empty initialize Method
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    //end initialize

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
     * goBack Method
     * This method will return to the previous fxml screen
     *
     * @throws IOException
     */

    public void goBack()throws IOException{

        Employee scene = new Employee();
        scene.newScene("boss.fxml");

    }

    //end goBack

}

