package com.example.cs370;
/**
 * import statements
 *
 *
 *
 */
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
 * Class EmployeeController
 *
 *
 */
public class EmployeeController{


    /**
     * String eID is to store the value of eID when is log in each username to be
     * able to share it between methods
     *
     */
    private static String eID;


    /**
     * Empty Constructor
     *
     *
     */
   public EmployeeController(){


   } // end public

    /**
     *
     * Declaring FXML required parameters for
     * Items inside the fxml
     *
     */

    @FXML
    private Button loginbutton;
    @FXML
    private PasswordField passfield;
    @FXML
    private TextField userfield;
    @FXML
    private Label logtext;


    /**
     *  Public method to credentials
     *  this mehtod will call the private method login for security
     *
     * @param event
     * @throws IOException
     */
    public void credentials(ActionEvent event) throws IOException {

        login(); // calling login

    }


    /**
     * Private method login
     * This mehtod will check the username and password and the level of security access have the username
     *
     *   implementaiton of security check still in progress
     *
     *   this method reads from the csv file and check username and password and level of access
     *
     *   now you can log in with multiple usernames to the program
     *
     *   CSV PATH FIXED
     *
     * @throws IOException
     */
    private void login() throws IOException{


        /**
         * Declaring variales necessary to run the next sceene
         *
         * Scene2 is for display the correct data when usernade and password and level of access match
         * STRING x is to store each string found in users.csv
         *int count is to count how many lines are in the csv
         */
        Employee scene2 = new Employee(); // creating a new scnene

            String[] x = new String[100]; // declaring a 100 string array
            int count = 0; // declaring a count int

        /**
         *Try and Catch method
         *
         *This method will read users csv file to store the complete string in a array of x[]
         *
         *
         */
        // Try stamenent for the scanner
        try {
            File file = new File(".\\src\\main\\resources\\com\\example\\cs370\\users.csv"); // path of users.csv
            Scanner scanner = new Scanner(file); // creating a scanner for the file
            while (scanner.hasNextLine()) {
                x[count] = scanner.nextLine(); // storing the line on the array
                count ++;// count ++
            } // end while
            scanner.close(); // closing the scanner
        } //end  try
        catch (FileNotFoundException e) {

            e.printStackTrace();
        } // end catch


        //END TRY AND CATCH



        /**
         * This for loop is design to read the array and check the username and password of the array
         * if it mach userfield and passfield
         *
         */
        for (int y = 0 ; y < count ; y++){


            //Debbugin comments
            //   System.out.println(userfield.getText().toString());
            //  System.out.println(x[y].substring(0, x[y].indexOf(',')-1));
           // System.out.println(passfield.getText().toString());
           // System.out.println(x[y].substring(x[y].indexOf(',')));


            /**
             *
             * Right now the if statement is hard coded it needs to be improved to ve able to take more
             * information of a line of the array
             *
             *        THIS METHOD NOW CHECKS FOR LEVEL OF ACCESS
             *        RULES SETS FOR THE CSV FILE IS THE DATA STORED IN PLACE NUMBER 4 EACH NEW LINE
             *
             * linedata is to store the substrings from x
             * String copy is to store the string store on x[y]
             * String buff is to store data for a required place a buffer is requiered
             * int counter for the while statement
             */

            String linedata [] = new String[100]; // Declaring linedata
            String copy = x[y]; // declaring string copy
            String buff = ""; // declaring a String buff
            int counter = 0; // declaring int counter

            while(counter < 4) {

                if(copy.length() > 1 ){

                    linedata[counter] = copy.substring(0, copy.indexOf(',')); // substring of index of 0 to , to store the data until find ,
                    buff = copy.substring(copy.indexOf(',') + 1); // copy the rest of the substring in buff
                    copy = (buff); // copy is now buff.. extra step to make sure the data is not changed
                    counter++; // counter  add one

            }// end if
                 else {

                    /**
                     * In this part is the last data on the line of the csv file
                     *
                     */

                    linedata[counter] = buff;// buff
                    counter++; // counter add one to get outside of the loop at this point

                }// end else

            }//end while loop


            /**
             * IF STATEMENTS
             * NEXT IF STATEMENTS ARE FOR CHECKING THE DATA WHAT THE USER INPUT TO LOG IN
             * THIS IF STATEMENT CHECKS LINEDATA ARRAY LOCATIONS OF 0 , 1 , 3
             * 0 IS USERNAME
             * 1 IS PASSWORD
             * 2 IS eID (Employee ID)
             * 3 IS LEVEL OF ACCESS
             *
             *
             * IF STATEMENT IS TRUE IF DATA FROM END USER MATCHES THE DATA STORED IN THE CSV FILE
             *
             */

            /**
             * IF STATEMENT CHECKS THE USERNAME PASSWORD AND THE LEVEL OF ACCESS
             * THIS WILL CHECK THE LEVEL OF ACCES OF **1**
             * IF LEVEL OF ACCESS IS ONE IS AN EMPLOYEE
             */

            if( linedata[0].equals(userfield.getText().toString()) && linedata[1].equals(passfield.getText().toString()) && linedata[3].equals("1")){ // if stamtenet

                    scene2.newScene("page2.fxml"); // throw screen
                    eID =  linedata[2]; //declaring Employee ID

            } // end if

            /**
             * IF STATEMENT CHECKS THE USERNAME PASSWORD AND THE LEVEL OF ACCESS
             * THIS WILL CHECK  THE LEVEL OF ACCESS OF **2**
             * IF LEVEL OF ACCESS IS TWO IS AN EMPLOYER (ADMIN USER)
             */

            if( linedata[0].equals(userfield.getText().toString()) && linedata[1].equals(passfield.getText().toString()) && linedata[3].equals("2")){ // if stamtenet

                eID = linedata[2]; // setting eID to data in linedata[2]
                scene2.newScene("boss.fxml"); // throw screen For boss

            } // end if

            /**
             * ELSE STATEMENT
             * AT THIS POINT THE USERNAME OR PASSWORD IS INCORRECT
             *THE PROGRAM WILL CHANGE THE LOGTEXT AS WRONG TO ADVISE END USER THE DATA IS INCORRECT
             */
            else {

                    logtext.setText("Wrong"); // change text

                } // end else

            }//end for loop

    } // end login

    /**
     * STATIC METHOD STRING geteID
     * This method will return to a required class the eID found in the csv file
     *
     * @return eID
     */
    public static String geteID(){

        return eID;

    } // end geteID


    /**
     * This method deletes the ID log in
     * This method will be run everytime the button logs out is activated
     */

    public static void flushID(){

          eID = "" ;

    } // end geteID



} // end class
