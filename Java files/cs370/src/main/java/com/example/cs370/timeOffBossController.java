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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import  java.io.*;
import javafx.fxml.Initializable;

public class timeOffBossController implements Initializable {

    @FXML
    private TextArea historyText;
    @FXML
    private Label   nameEmployee;
    @FXML
    private Label   reasonRequest;
    @FXML
    private Button  approveB;
    @FXML
    private Button denyB;
    @FXML
    private Button  backB;
    @FXML
    private Button  aDays;
    @FXML
    private Button  dDays;
    @FXML
    private Button  pDays;


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
            allRecordHistory();
            nextRequest();

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
     */
    public timeOffBossController(){


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

        /**
         * If Statement to check if directories exist
         */
        if (dir.exists() == false || csvFileName.exists() == false){

            dir.mkdir();
            csvFileName.createNewFile();

        } // end if




    }
    /**
     * End Method
     */


    /**
     * Method nextRequest
     * This Method Will change the Main Labels of Name And Reason to Display
     * The next person to Make a dessision of Approve or Deny the day
     *
     * @throws IOException
     */
    public void nextRequest() throws IOException{

        File csvFileName = new File("./timeOffRequest/"+"/requestRecord.csv");  //path to the csv File

        Scanner scanner = new Scanner(csvFileName); // Declaring a Scanner

        String data[]; // Declaring a String

        //While the text file has next Line
        while (scanner.hasNextLine() != false){

            data = scanner.nextLine().split(",");   // Split the data into An array to found the Specific part of
                                                        // the CSV file we need
            //If statement to find the letter P that is the keyword to Pending Request
            if (data[5].equals("P")){

                nameEmployee.setText(" " + getName(data[1]));   //Setting the data
                reasonRequest.setText(" " + data[4]);           //Setting the data

                return; // returning

            } // end if



        } // end while


        //If the code reaches here means we dont have anymore pending request
        //We display A text to notify the user
        nameEmployee.setText("No More Pending days");
        reasonRequest.setText("");

        scanner.close(); // CLOSE THE SCANNER



        return; // return statement for clearity

    }

    /**
     * End Method nextRequest
     */


    /**
     * Method ApproveDay
     *
     * This Method is designed to work when the button Aprrove is Actioned
     * This method will change the data on the array where the status off the request for a letter Y
     * meaning the Manager Approved the day
     *
     * @throws IOException
     */
    public void approveDay(ActionEvent event) throws IOException{

        //If statement to checkForP
        if (checkForP() == false){

            nameEmployee.setText("No More Pending days");

            return;

        } // end if

        File csvFileName = new File("./timeOffRequest/"+"/requestRecord.csv");  //Declaring a File

        Scanner scanner = new Scanner(csvFileName); // Declaring a Scanner

        StringBuilder sb = new StringBuilder(); //Declaring a StringBuilder

        String data[];  //Declarign a String Array

        boolean flag = false;   //Declaring a flag

        /*
        While loop
        The Next While loop will Scan all the Lines of the File
        We use a String Builder to rebuild the File to Write the change maded into the code
        The flag is use to only change the file was the next Pending on the request csv file
         */
        while (scanner.hasNextLine() != false){

            data = scanner.nextLine().split(",");

            if (data[5].equals("P") && flag == false){

                sb.append(data[0]+",");
                sb.append(data[1]+",");
                sb.append(data[2]+",");
                sb.append(data[3]+",");
                sb.append(data[4]+",");
                data[5] = "Y\n";
                sb.append(data[5]);
                flag = true;



            }
            else {

                sb.append(data[0]+",");
                sb.append(data[1]+",");
                sb.append(data[2]+",");
                sb.append(data[3]+",");
                sb.append(data[4]+",");
                sb.append(data[5]+"\n");


            }





        }

        /*
        Closing the scanner to dont create conflict with the Writing of the code
        creating a writer to write the uptaded data on the code.
        Closing the writer to keep the file close when not in use
         */
        scanner.close();
        FileWriter writer = new FileWriter(csvFileName);
        writer.write(sb.toString());
        writer.close();

        /*
        Calling methods
        Required to the correct output of the program
         */
        allRecordHistory();
        nextRequest();

    }

    /**
     * End Method
     */

    /**
     * Method denyDay
     *
     * This Method is designed to work when the button deny is Actioned
     * This method will change the data on the array where the status off the request for a letter N
     * meaning the Manager Deny the day
     *
     * @throws IOException
     */
    public void denyDay() throws IOException{

        //Calling method checkForP
        /*
            IF statement is designed to dont allow the execution of code if we dont have more Pending Days "P"
         */
        if (checkForP() == false){

            nameEmployee.setText("No More Pending days");
            return;

        } // end IF


        File csvFileName = new File("./timeOffRequest/"+"/requestRecord.csv"); // Declaring a File Name

        Scanner scanner = new Scanner(csvFileName); // Declaring a scanner

        StringBuilder sb = new StringBuilder(); //Declaring a sb

        String data[]; // Declaring a String Array
        boolean flag = false; // declaring a flag

        /*
        While loop
        The Next While loop will Scan all the Lines of the File
        We use a String Builder to rebuild the File to Write the change maded into the code
        The flag is use to only change the file was the next Pending on the request csv file
         */

        while (scanner.hasNextLine() != false){

            data = scanner.nextLine().split(",");

            if (data[5].equals("P") && flag == false){

                sb.append(data[0]+",");
                sb.append(data[1]+",");
                sb.append(data[2]+",");
                sb.append(data[3]+",");
                sb.append(data[4]+",");
                data[5] = "N\n";
                sb.append(data[5]);
                flag = true;



            }
            else {

                sb.append(data[0]+",");
                sb.append(data[1]+",");
                sb.append(data[2]+",");
                sb.append(data[3]+",");
                sb.append(data[4]+",");
                sb.append(data[5]+"\n");


            }


        }

        /*
        Closing the scanner to dont create conflict with the Writing of the code
        creating a writer to write the uptaded data on the code.
        Closing the writer to keep the file close when not in use
         */

        scanner.close();
        FileWriter writer = new FileWriter(csvFileName);
        writer.write(sb.toString());
        writer.close();

        /*
        Calling methods
        Required to the correct output of the program
         */
        allRecordHistory();
        nextRequest();


    }

    /**
     * End denyDay
     */


    /**
     * Method checkForP
     * This Method checks the csv File for letter P in the expected place to
     * make programs run at the desired status
     *
     * @return true if P is found
     * @return false if P is not Found
     * @throws IOException
     */
    public boolean checkForP() throws IOException{


        File csvFileName = new File("./timeOffRequest/"+"/requestRecord.csv"); // Declaring a File
        Scanner scanner = new Scanner(csvFileName); //Declaring a Scanner
        String data[]; // Declaring a data

        /*
        While Loop
        The next while loop is designed to check read all the lines
        If statement is for check for the letter p
        in the spected location and return true
         */
        while (scanner.hasNextLine() != false){

            data = scanner.nextLine().split(",");

                if (data[5].equals("P") ){

                    return true;

                 }


        }

        scanner.close(); //Closing the scanner

        /*
        At this point P is not found and return false
         */
        return false;


    }

    /**
     *End checkForP
     */


    /**
     * Method allRecordHistoy
     * This method is designed Specific for Admin LOA
     * This method will change the textArea with the expected output
     * This method is designed to output all the request of time off of all Employees
     * This method is call When the code starts, Button Aprove and Decline is Actioned
     *
     * @throws IOException
     */
    public void allRecordHistory() throws IOException {

        File csvFileName = new File("./timeOffRequest/"+"/requestRecord.csv");  //Declaring File

        Scanner scanner = new Scanner(csvFileName); //Declaring a Scanner
        StringBuilder record = new StringBuilder(); //Declaring a record

        String data[];  //Declaring a String Array
        String eIDLocal;    //Declaring a String


            /*
            While loop
            This While loop Is designed to read the csv File and display the data on the TextArea on the Java FX GUI
            Depending of the letter found on expected place will display Approved , Declined or Pending
             */
            while (scanner.hasNextLine() != false) {

                data = scanner.nextLine().split(",");

                eIDLocal = data[1];


                record.append("Date: " + data[0] + ", Name: ");

                record.append(getName(eIDLocal));


                record.append(", Reason: " + data[4] + ", Status: ");

                if (data[5].equals("P")) {

                    record.append("Pending\n");

                }// end else if
                else if (data[5].equals("N")) {

                    record.append("Declined\n");

                } //end else if
                else if (data[5].equals("Y")) {


                    record.append("Approved\n");

                } // end else if

            } // end while

            historyText.setText(record.toString()); //We display the spected output into the csv file

        scanner.close(); // closing the scanner

    } // end allRecordHistory

    /**
     *  End allRecordHistory
     */


    /**
     * Method approvedDays
     *
     * This Method is designed to work only when the Button is Actioned
     * This method will change the TextArea to Display the days are Only Approved
     * This Method Will NOT DISPLAY PENDING OR DENY TIME OFFS
     * @param event
     * @throws IOException
     */
    public void approvedDays(ActionEvent event) throws IOException{

        File csvFileName = new File("./timeOffRequest/"+"/requestRecord.csv"); // Creates the file of the csv name
        Scanner scanner = new Scanner(csvFileName); //Declaring a Scanner
        StringBuilder record = new StringBuilder(); //Declaring a StringBuilder
        String data[];//Declaring a String Array

        /*
            While loop
            This While loop is designed to look for Letter "Y"
            If Found the Line will be added into the StringBuilder to create the expected Output
         */
        while(scanner.hasNextLine() != false){

            data = scanner.nextLine().split(",");   //Sptiing the csv into array

            if (data[5].equals("Y")){

                record.append("Date: " + data[0] +", Name: " + getName(data[1]) + ", Reason: " + data[4] + ", Status: ");
                record.append("Approved\n");

            } // end if


        } // end while

        historyText.setText(record.toString()); // Displaying the expected Output

        scanner.close(); // Closing the scanner

    } // end approvedDays
    /**
     *  End approvedDays
     */


    /**
     *Method declinedDays
     *
     * This Method is designed to work only when the Button is Actioned
     * This method will change the TextArea to Display the days are Only Deny
     * This Method Will NOT DISPLAY PENDING OR APPROVED TIME OFFS
     * @throws IOException
     *
     */
    public void declinedDays() throws IOException{

        File csvFileName = new File("./timeOffRequest/"+"/requestRecord.csv"); // Creates the file of the csv name
        Scanner scanner = new Scanner(csvFileName);//Declaring a scanner
        StringBuilder record = new StringBuilder();//Declaring a StringBuilder
        String data[];//Declaring an Array

        /*
            While Loop
            The next While Loop is designed to loop for the letter "N"
            IF FOUND THE STRING WILL BE ADDED INTO A STRING BUILDER TO CREATE THE EXPECTED OUTPUT
         */
        while(scanner.hasNextLine() != false){

            data = scanner.nextLine().split(",");

            if (data[5].equals("N")){

                record.append("Date: " + data[0] +", Name: " + getName(data[1]) + ", Reason: " + data[4] + ", Status: ");
                record.append("Declined\n");

            }// end if

        }//end While

        historyText.setText(record.toString()); //Displaying expected output

        scanner.close();//Closing the scanner

    }
    /**
     *  End declinedDays
     */


    /**
     *
     * Method pendingDays
     * This Method is designed to work only when the Button is Actioned
     * This method will change the TextArea to Display the days are Only Pending
     * This Method Will NOT DISPLAY DENY OR APPROVED TIME OFFS
     * @throws IOException
     *
     */
    public void pendingDays() throws IOException{

        File csvFileName = new File("./timeOffRequest/"+"/requestRecord.csv"); // Creates the file of the csv name
        Scanner scanner = new Scanner(csvFileName);//Declaring a scanner
        StringBuilder record = new StringBuilder();//Declaring a StringBuilder
        String data[];//Declaring a StringArray

        /*
        While loop
        The next While loop is designed to look for the letter "P"
        If letter P is found will be added into the String Builder to create the expected output
         */
        while(scanner.hasNextLine() != false){

            data = scanner.nextLine().split(",");

            if (data[5].equals("P")){

                record.append("Date: " + data[0] +", Name: " + getName(data[1]) + ", Reason: " + data[4] + ", Status: ");
                record.append("Pending\n");

            }//end if

        } // end while

        historyText.setText(record.toString());//Displaying expected output

        scanner.close();    //Closing Scanner

    }

    /**
     *  End pendingDays
     */

    /**
     *
     * Method getName
     * This method will return the Name with the last Name included of a specific person with their eID
     * This method requires a String will have the eID
     *
     * @param eIDLocal
     * @return
     * @throws IOException
     */
    public String getName(String eIDLocal)throws IOException {

        Scanner scan = new Scanner(new File(".\\src\\main\\resources\\com\\example\\cs370\\users.csv"));
        //Declaring a Sccanner
        String data[];//Declaring String Array
        String Name = "";//Declaring string

        /*
        While Loop
        The next While loop will look for the eID on the users.csv to match it and return the
        name corresponding with that eID
         */
        while (scan.hasNextLine() != false) {

            data = scan.nextLine().split(",");

                if (data[2].equals(eIDLocal)) {

                    return data[6] + " " + data[5];

                 } // end if

        } // end while

        scan.close(); // closing the scanner
        return ""; // return statement required

    } // end getName
    /**
     *  End getName
     */


    /**
     * Method backAction
     * This method is designed to go to the back screen
     *
     * @param event
     * @throws IOException
     */
    public void backAction(ActionEvent event) throws IOException{

        Employee scene = new Employee();
        scene.newScene("boss.fxml");


    }
    /**
     * End backAction
     */


} // End Class
