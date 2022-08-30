package com.example.cs370;

import java.io.FileWriter;
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

/**
 * MainMenuController class
 * This class is to control the java fxml page2.fxml
 *
 */

public class MainMenuController {

    /**
     * Declaring javafx required declaration for elements inside of fxml file
     *
     */
    @FXML
    private Button cIn;
    @FXML
    private Button cOut;
    @FXML
    private Button sButton;
    @FXML
    private Button tOff;
    @FXML
    private Button pButton;
    @FXML
    private Button lOut;
    @FXML
    private Label clockstatus;  // Aleixs -- Cant Program the clock
    @FXML
    private Label welcomeText;
    @FXML
    private Button lunchI;
    @FXML
    private Button lunchB;



    // end of javafx declarations


    /**
     * Empty Constructor for MainMenuController
     *
     */
    public MainMenuController(){

    } // empty consturctor

    /**
     * Clockin Function
     * This function is when the button cIn is pressed
     * This function is to store the data when an employee clocks in in a dedicated csv file
     * Also this funciton will only allow one clock in input until a clock out value is found in the file
     * This function will store the data in a folder called /time/(eID)/timesheet
     * This method will create a carpet for store the data of clock in in a dedicated folder. the folder name is the eID of the employee
     *
     * 10/22/2021
     * CODE WAS MODIFIED TO ONLY ALLOW ONE CLOCK IN AT DAY
     *
     *
     * @param event
     * @throws IOException
     */


    /**
     ************** NOTE ******************
     *  THIS METHOD IN THE FUTURE WILL REQUIRE AN IMPLEMENTAITON OF A STATIC FLAG CALLED "ONCLOCK" TO ENSURE THE EMPLOPYEES
     *  ONLY USES THE FUNCITONALITIES THAT ARE CLASSIFIED TO BE ONLY USED ON THE CLOCK
     *
     *
     * @param event
     * @throws IOException
     */

    public void clockin(ActionEvent event) throws IOException {

        /**
         * 1 .This next lines are for set he fomrat for display the Hour clock in and the way is going to be stored
         *
         */
        SimpleDateFormat x = new SimpleDateFormat("dd/MM/yyyy,HH.mm"); // declaring a simpleDate format
        Date date = new Date();
        String s = x.format(date); // setting string s to x.format(date)

        // End 1 implementation

        /**
         *
         * 2. Next implementation of code is to create a directory to store each employee time in a dedicated folder
         *
         */

        File dir = new File("./time/" + EmployeeController.geteID()); // The format will create the path
        File csvFileName = new File("./time/"+ EmployeeController.geteID() +"/clockTime.csv"); // Creates the file of the csv name
            //If statement is to dont override if the folder is already created

                if (dir.exists() == false || csvFileName.exists() == false) {

                        dir.mkdir(); // create the directory
                        csvFileName.createNewFile(); // create the csv file

                } // If directory havent been created

                    else {

                    System.out.println("exist"); // debbugin code for the java console

                     } // end else

        // End Implementation 2

        /**
         *
         * 3 . Next implementation of code is to Store the data of clock in Time on the csv file each time clock in button is press
         *      This code needs to check if is alreay  clock in.
         *      If already clock in the program will display error on the screen and will not edit CSV file
         *
         *
         */

        //Create a method will read the csv File

        Scanner scan = new Scanner(new File("./time/" + EmployeeController.geteID() + "/clockTime.csv"));

            String lastLine = "" ; // creating a string Lasline to store it

            while(scan.hasNextLine()){ // while loop to store lastLine

                lastLine = scan.nextLine();

            } // end of while loop

            // If data is HH:MM, means you are clock in so dont allow another clock in write on the csv file

            String array[] = lastLine.split(",");

        /**
         *
         * IF STAMENT WAS MODIFIED TO ALLOW ONLY ONE CLOCK IN FOR THE DAY
         *
         *
         */

        if ((array.length == 5 || lastLine.isEmpty()) &&  array[0].equals(s.substring(0,s.indexOf(','))) == false ) {

                clockstatus.setText("Clock In \n" + s); // Setting clock status to display the actual time clock in

                //Want to write to the last line on the csv File

                FileWriter writer = new FileWriter("./time/" + EmployeeController.geteID() + "/clockTime.csv", true);

                StringBuilder sb = new StringBuilder();
                sb.append(s);
                sb.append(',');
                writer.write(sb.toString());
                writer.close();

            } // means we have a clock out value

            else if(array.length == 2 ){

            clockstatus.setText("Error On Clock \n Since: " + array[1]); // Setting clock status to display the actual time clock in

        } // dont have a clock out value

             /**
              * ELSE IF STAMENT FOR WHEN END USER ALREADY PUNCHED OUT AND IN FOR THE SAME DAY
              * ERROR MESSAGE
              *
              */
             else if (array[0].equals(s.substring(0,s.indexOf(','))) == true){


                 clockstatus.setText("Cant punch\ntwice"); // Setting clock status to display the actual time clock in


             }


        // End implementaiton 3

    } // end clockin

    /**
     * clockOut funciton
     * This function is when cOut button is pressed
     * This function will store the time when an employee press the clock out buttton
     *This functuion will allow only one clock out and if only the employee is clock in
     *This function will display an error message when is necessary
     * @param event
     * @throws IOException
     */

    public void clockout (ActionEvent event) throws IOException{
        /**
         * 1 .This next lines are for set he fomrat for display the Hour clock in and the way is going to be stored*
         */
        SimpleDateFormat x = new SimpleDateFormat("HH.mm"); // declaring a simpleDate format
        Date date = new Date();
        String s = x.format(date); // setting string s to x.format(date)

        // End 1 implementation

        /**
         *
         * 2. Next implementation of code is to check if directory is already created
         * This means already has made their first Clock in
         *
         */

        File dir = new File("./time/" + EmployeeController.geteID()); // The format will create the path
        File csvFileName = new File("./time/"+ EmployeeController.geteID() +"/clockTime.csv"); // Creates the file of the csv name
        //If statement is to dont override if the folder is already created

        if (dir.exists() == false || csvFileName.exists() == false) {

            clockstatus.setText("1st clock in \n no recorded"); //

        } // If directory havent been created

        //// End 2 Code Implementation

        /**
         * 3. Code Implementaiton
         * Else
         * At This point we know the files exist so we can procceed to write a clock out
         *
         */

        else {

            Scanner scan = new Scanner(new File("./time/" + EmployeeController.geteID() + "/clockTime.csv"));
            String lastLine = "" ;

            while(scan.hasNextLine()){

                lastLine = scan.nextLine();

            }

            // If data is HH:MM, means you are clock in so dont allow another clock in write on the csv file
            String array[] = lastLine.split(",");

            if (array.length == 5 || lastLine.isEmpty()) {

                clockstatus.setText("You are not \n Clock in"); // Setting clock status to display the actual time clock in

                //Want to write to the last line on the csv File

            } // means we have a clock out value

            else if(array.length == 4 ){

                FileWriter writer = new FileWriter("./time/" + EmployeeController.geteID() + "/clockTime.csv", true);
                StringBuilder sb = new StringBuilder();
                sb.append(s);
                sb.append('\n');
                writer.write(sb.toString());
                writer.close();

                clockstatus.setText("Clocked out at:\n" + s); // Setting clock status to display the actual time clock in

            } // dont have a clock out value

        } // end else 3 Implementaiton

        // End Implementation 3

    }

    /***
     * LunchIn Function
     *
     * This function will check and allow the employee to record their Lunch In time on the csv file
     *
     *
     *
     *
     */

    public void lunchIn(ActionEvent event) throws IOException{


        /**
         * 1 .This next lines are for set he fomrat for display the Hour clock in and the way is going to be stored
         *
         */
        SimpleDateFormat x = new SimpleDateFormat("HH.mm"); // declaring a simpleDate format
        Date date = new Date();
        String s = x.format(date); // setting string s to x.format(date)

        // End 1 implementation

        /**
         *
         * 2. Next implementation of code is to create a directory to store each employee time in a dedicated folder
         *
         */

        File dir = new File("./time/" + EmployeeController.geteID()); // The format will create the path
        File csvFileName = new File("./time/"+ EmployeeController.geteID() +"/clockTime.csv"); // Creates the file of the csv name
        //If statement is to dont override if the folder is already created

        if (dir.exists() == false || csvFileName.exists() == false) {

                clockstatus.setText("Error");

        } // If directory havent been created

        else {          //ELSE STAMENT UWOLD MAKE THE CODE RUN IF TH CODE EXIST


            // End Implementation 2

            /**
             *
             * 3 . Next implementation of code is to Store the data of clock in Time on the csv file each time clock in button is press
             *      This code needs to check if is alreay  clock in.
             *      If already clock in the program will display error on the screen and will not edit CSV file
             *
             *
             */

            //Create a method will read the csv File

            Scanner scan = new Scanner(new File("./time/" + EmployeeController.geteID() + "/clockTime.csv"));

            String lastLine = ""; // creating a string Lasline to store it

            while (scan.hasNextLine()) { // while loop to store lastLine

                lastLine = scan.nextLine();

            } // end of while loop

            // If data is HH:MM, means you are clock in so dont allow another clock in write on the csv file

            String array[] = lastLine.split(",");

            /**
             *
             * IF STAMENT WAS MODIFIED TO ALLOW ONLY ONE CLOCK IN FOR THE DAY
             *
             *
             */

            //At this point should have [0] = date,  [1] = clock in time , [2] shouldnt exist


            if (array.length == 2) {

                clockstatus.setText("Lunch In \n" + s); // Setting clock status to display the actual time clock in

                //Want to write to the last line on the csv File

                FileWriter writer = new FileWriter("./time/" + EmployeeController.geteID() + "/clockTime.csv", true);

                StringBuilder sb = new StringBuilder();
                sb.append(s);
                sb.append(',');
                writer.write(sb.toString());
                writer.close();

            } // means we have a clock out value

            else if (array.length == 3) {

                clockstatus.setText("Error On Lunch since \n Since: " + array[2]); // Setting clock status to display the actual time clock in

            } // dont have a clock out value


            else {


                clockstatus.setText("Error");


            }


        } // END ELSE



    }


    /**
     * LunchBack
     *
     * This function will check and allow the employeee to record their lunch back on the csv File
     *
     *
     *
     */


    public void lunchBack(ActionEvent event) throws IOException     {



        /**
         * 1 .This next lines are for set he fomrat for display the Hour clock in and the way is going to be stored
         *
         */
        SimpleDateFormat x = new SimpleDateFormat("HH.mm"); // declaring a simpleDate format
        Date date = new Date();
        String s = x.format(date); // setting string s to x.format(date)

        // End 1 implementation

        /**
         *
         * 2. Next implementation of code is to create a directory to store each employee time in a dedicated folder
         *
         */

        File dir = new File("./time/" + EmployeeController.geteID()); // The format will create the path
        File csvFileName = new File("./time/"+ EmployeeController.geteID() +"/clockTime.csv"); // Creates the file of the csv name
        //If statement is to dont override if the folder is already created

        if (dir.exists() == false || csvFileName.exists() == false) {

            clockstatus.setText("Error");

        } // If directory havent been created

        else {          //ELSE STAMENT UWOLD MAKE THE CODE RUN IF TH CODE EXIST


            // End Implementation 2

            /**
             *
             * 3 . Next implementation of code is to Store the data of clock in Time on the csv file each time clock in button is press
             *      This code needs to check if is alreay  clock in.
             *      If already clock in the program will display error on the screen and will not edit CSV file
             *
             *
             */

            //Create a method will read the csv File

            Scanner scan = new Scanner(new File("./time/" + EmployeeController.geteID() + "/clockTime.csv"));

            String lastLine = ""; // creating a string Lasline to store it

            while (scan.hasNextLine()) { // while loop to store lastLine

                lastLine = scan.nextLine();

            } // end of while loop

            // If data is HH:MM, means you are clock in so dont allow another clock in write on the csv file

            String array[] = lastLine.split(",");

            /**
             *
             * IF STAMENT WAS MODIFIED TO ALLOW ONLY ONE CLOCK IN FOR THE DAY
             *
             *
             */

            //At this point should have [0] = date,  [1] = clock in time , [2] shouldnt exist


            if (array.length == 3) {

                clockstatus.setText("Lunch back \n" + s); // Setting clock status to display the actual time clock in

                //Want to write to the last line on the csv File

                FileWriter writer = new FileWriter("./time/" + EmployeeController.geteID() + "/clockTime.csv", true);

                StringBuilder sb = new StringBuilder();
                sb.append(s);
                sb.append(',');
                writer.write(sb.toString());
                writer.close();

            } // means we have a clock out value

            else if (array.length == 4) {

                clockstatus.setText("Error Back from\n Lunch since \n Since: " + array[3]); // Setting clock status to display the actual time clock in

            } // dont have a clock out value

            else {


                clockstatus.setText("Error");


            }

            /**
             * ELSE IF STAMENT FOR WHEN END USER ALREADY PUNCHED OUT AND IN FOR THE SAME DAY
             * ERROR MESSAGE
             *
             */


        } // END ELSE



    }







    /**
     * Method paystubScreen
     * This Mehtod will open the ePaystub.fxml File from Action Event button
     *
     *
     * Next code is only to Open the ePaystub.fxml File
     *
     */

    public void paystubScreen() throws IOException{

        Employee scenegg = new Employee(); // Creating a new Scene

        scenegg.newScene("ePaystub.fxml"); // Calling main screen


    } // end paystubScreen

    /**
     *  End Method
     */

    /**
     * Method timeOffScreen
     * This method will open the timeOffEmployee.fxml file from Action Event button
     *
     * @param event
     * @throws IOException
     */

    public void timeOffScreen(ActionEvent event) throws  IOException{

        Employee scene = new Employee();

        scene.newScene("timeOffEmployee.fxml");



    } // end Method

    /**
     * End Method
     */



    public void scheduleScreen(ActionEvent event) throws IOException{

        Employee scene = new Employee();

        scene.newScene("empSchedule.fxml");





    }








    //************************************************************* End Implementaiton Clock In And Out **********************************************//

    /**
     * This method is for the button Logout
     *
     * This method will call flushID from employeeControl to reset eID
     *
     * @throws IOException
     */
    public void logout() throws IOException{

        Employee scene = new Employee(); // Creating a new Scene

        scene.newScene("page1.fxml"); // Calling main screen

        EmployeeController.flushID(); // calling flush id

    } // end logout

    /**
     * End Method
     */

    //***************************************************************** End Implementation logOut ******************************//

    //FXML CODE

} // end MainMenuController Class
