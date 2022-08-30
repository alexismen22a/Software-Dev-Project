package com.example.cs370;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.collections.*;

import java.io.IOException;
import java.io.*;
import java.util.*;

/**
 * Class addRemoveController is to controll add.fxml
 *
 */
public class addRemoveController {

    /**
     * Declaring all the necessary java FXML
     *
     */
    @FXML
    private Button back; // declaring button back
    @FXML
    private TextField userN;
    @FXML
    private PasswordField passW;
    @FXML
    private PasswordField cPassW;
    @FXML
    private TextField wage;
    @FXML
    private TextField first;
    @FXML
    private TextField last;
    @FXML
    private Label output;
    @FXML
    private Button adminB;
    @FXML
    private Button wButton;

    //end declaring javaFX neccessary

    /**
     * Empty Constructor
     */
    public addRemoveController(){

    } // end addRemoveController constructor

    /**
     * storeAdmin method
     * This method is to store the data of a new user with the Level of access of an admin (Employer )
     *This method will store the data on the csv of users
     * @param event
     * @throws IOException
     */
    public void storeAdmin(ActionEvent event) throws IOException{

        /**
         *
         *This first implementation of this code  is to find the place where is going  to be store the new user
         *
         */
        Scanner scan = new Scanner(new File(".\\src\\main\\resources\\com\\example\\cs370\\users.csv"));

            String lastLine = "" ;
            int count = 1 ;
            while(scan.hasNextLine()){

                lastLine = scan.nextLine();
                count ++;

            } // end whiile

        //************************** End of 1 implementaiton **************************************************************

        /**
         *  Implementaiton 2
         * Next if statements is to print an error on the FXML screen to the user
         * when they dont input an username or a password where is requiered
         *
         * ************NOTE **********************************
         * We probably want to add anther box for password to do a double check if they input the correct password
         *
         */
            if (userN.getText().isEmpty() == true){

                output.setText("Please input a Username");

            }

            else if (passW.getText().isEmpty() == true){

                output.setText("Please input a Password");

            }// end if

            else if (cPassW.getText().isEmpty() == true){

                output.setText("Missing Verification of password");


            }

            else if (wage.getText().isEmpty() == true){

                output.setText("Please input the wage");


            }

            else if (first.getText().isEmpty() == true){


                output.setText("Please input a First Name");



            }

            else if (last.getText().isEmpty() == true){

                output.setText("Please input a Last Name");

            }



            //***************** End of Implementaiton 2

            /***
             * Implementaiton OFFSET
             * THIS IMPLEMENTAITON IS FOR THE ADDITION OF PASSWORD VERIFICATION, WAGE, FIRST AND LAST NAME STORAGE ON THE
             * USERS CSV FILE
             *
             *
             *
             */




            else if (passW.getText().equals(cPassW.getText()) == false){



                output.setText("PASSWORDS DONT MATCH");



            }

                /**
                 * Implementation 3
                 * Next else if is when we have and username and a password input by the end user
                 * and the next lines to code is to store it in the required format to the csv file
                 * The format we agree was ............... USERNAME,PASSWORD,EMPLOPYEE ID (eID),LEVEL OF ACCESS
                 *and the method will print the user eID
                 */
            else if (userN.getText().isEmpty() == false && passW.getText().isEmpty() == false && cPassW.getText().isEmpty() == false && wage.getText().isEmpty() == false
                        && first.getText().isEmpty() == false && last.getText().isEmpty() == false) {

                    FileWriter writer = new FileWriter(".\\src\\main\\resources\\com\\example\\cs370\\users.csv", true); // FILE WRITER WITH APPEND TRUE TO DONT OVERRIDE THE FILE
                    StringBuilder sb = new StringBuilder(); // DECLARING A STRING BUILDER TO STORE THE STRING BEFORE WRITING TO THE CSV FILE

                    /**
                     * Next lines is to set the string builder to the format agreed
                     *
                     */
                    sb.append(userN.getText());
                    sb.append(",");
                    sb.append(passW.getText());
                    sb.append(",");
                    sb.append("" + count);
                    sb.append(",");
                    sb.append("2");
                     sb.append(",");
                    sb.append(wage.getText());
                    sb.append(",");
                    sb.append(last.getText());
                    sb.append(",");
                    sb.append(first.getText());

                sb.append('\n');

                    writer.write(sb.toString()); // writing the data on the file
                    writer.close();

                    output.setText(userN.getText() + " Has been save and eID: " + count);

                } // end else if


                // End of Implementation 3









        }// end store admin


    /**
     * storeEmp This method is to store the new user as a Employee (Not with admin access )
     *
     * @param event
     * @throws IOException
     */
    public void storeEmp (ActionEvent event) throws IOException{
        /**
         * Implementaiton 1
         /*
         *
         *This first implementation of this code  is to find the place where is going  to be store the new user
         *
         */

        Scanner scan = new Scanner(new File(".\\src\\main\\resources\\com\\example\\cs370\\users.csv"));
        String lastLine = "" ;
        int count = 1 ;
        while(scan.hasNextLine()){

            lastLine = scan.nextLine();
            count ++;

        }

        //******************************* end implementation one

        /**
         *  Implementaiton 2
         * Next if statements is to print an error on the FXML screen to the user
         * when they dont input an username or a password where is requiered
         *
         * ************NOTE **********************************
         * We probably want to add anther box for password to do a double check if they input the correct password
         *
         */

        if (userN.getText().isEmpty() == true){

            output.setText("Please input a Username");

        }

        else if (passW.getText().isEmpty() == true){

            output.setText("Please input a Password");

        }// end if

        else if (cPassW.getText().isEmpty() == true){

            output.setText("Missing Verification of password");


        }

        else if (wage.getText().isEmpty() == true){

            output.setText("Please input the wage");


        }

        else if (first.getText().isEmpty() == true){


            output.setText("Please input a First Name");



        }

        else if (last.getText().isEmpty() == true){

            output.setText("Please input a Last Name");

        }



        //***************** End of Implementaiton 2

        /***
         * Implementaiton OFFSET
         * THIS IMPLEMENTAITON IS FOR THE ADDITION OF PASSWORD VERIFICATION, WAGE, FIRST AND LAST NAME STORAGE ON THE
         * USERS CSV FILE
         *
         *
         *
         */




        else if (passW.getText().equals(cPassW.getText()) == false){



            output.setText("PASSWORDS DONT MATCH");



        }

        /**
         * Implementation 3
         * Next else if is when we have and username and a password input by the end user
         * and the next lines to code is to store it in the required format to the csv file
         * The format we agree was ............... USERNAME,PASSWORD,EMPLOPYEE ID (eID),LEVEL OF ACCESS
         *and the method will print the user eID
         */
        else if (userN.getText().isEmpty() == false && passW.getText().isEmpty() == false && cPassW.getText().isEmpty() == false && wage.getText().isEmpty() == false
                && first.getText().isEmpty() == false && last.getText().isEmpty() == false ) {

            FileWriter writer = new FileWriter(".\\src\\main\\resources\\com\\example\\cs370\\users.csv", true);
            StringBuilder sb = new StringBuilder();

            /**
             * Next lines is to set the string builder to the format agreed
             *
             */
            sb.append(userN.getText());
            sb.append(",");
            sb.append(passW.getText());
            sb.append(",");
            sb.append("" + count);
            sb.append(",");
            sb.append("1");
            sb.append(",");
            sb.append(wage.getText());
            sb.append(",");
            sb.append(last.getText());
            sb.append(",");
            sb.append(first.getText());
            sb.append("\n");
            writer.write(sb.toString());
            writer.close();

            output.setText(userN.getText() + " Has been save and eID: " + count);

        }

        // End Implementation 3

    }
    /**
     * This method is for the functionality of method back
     * @param event
     * @throws IOException
     */

    public void backAction(ActionEvent event) throws IOException{

        Employee canvas = new Employee(); // creating a new canvas
        canvas.newScene("boss.fxml"); // Displaying boss.fxml

    } // end backAction

}// end class
