/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.regex.Pattern;
import java.io.*;
import jamesfinalproject.LoginWindow;
import java.math.BigInteger;

/**
 *
 * @author James
 */
public class Library {

    
    
    
    public static String username;                                              //Declares a string variable to hold the current username
    public static final String KEY = "^%$,h";                                   //Declares the program's encryption/decryption key 
    
    
    
    public static void main(String[] args) {

        LoginWindow login = new LoginWindow();                                  //Creates a new instance of the login window object 
        login.setVisible(true);                                                 //Makes the window visible
        login.setLocationRelativeTo(null);                                      //Position the window in the centre of the screen

    }

    
    
    
    
    
    //Creates a public method called encrypt that takes a string varaible "plaintext" ------------------------------------------> <encrypt>
    
    public static String encrypt(String plaintext) {    

        String encrypted = "";                                                  //Creates a variable to store the encrypted text
        String tempString = "";                                                 //Creates a variable to store temporary text

        char[] chars = plaintext.toCharArray();                                 //Creates a character array to hold the plaintext's characters
        int[] values = new int[plaintext.length()];                             //Creates an integer array to hold the plaintext's character values

        char[] keyChars = KEY.toCharArray();                                    //Creates a character array to hold the key's characters
        int[] keyValues = new int[KEY.length()];                                //Creates an integer array to hold the key's character values

            //Combines the plaintext together with the key (char1 + KEY + char2 + KEY + char3 + KEY + ...) and stores it in the "tempString" variable
            
        for (int i = 0; i < plaintext.length(); i++) {
            
            values[i] = (int) chars[i];                                         //Stores each plaintext character as an integer
            tempString = tempString + values[i];                                //Adds the integer to the tempString
            
            for (int j = 0; j < KEY.length(); j++) {
                keyValues[j] = (int) keyChars[j];                               //Stores each of the key character's as an integer
                tempString = tempString + keyValues[j];                         //Adds each integer to the tempString
            }
            
        }

        char[] newChars = tempString.toCharArray();                             //Creates an array to store each character in the temporary string
        int[] newValues = new int[tempString.length()];                         //Creates an array to store the integer values of each character in the temporary string

        for (int i = 0; i < tempString.length(); i++) {
            newValues[i] = (int) newChars[i] + 10;                              //Adds 10 to the ascii value of each integer in the tempString 
            encrypted = encrypted + (char) newValues[i];                        //Converts the new integer value back to a character value, then adds the new character to the encrypted text 
        }

        return encrypted;                                                       //Returns the encrypted text

    } //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------> </encrypt>

    
    
    
    
    
    //Creates a public method called decrypt that takes a string varaible "encrypted" ------------------------------------------------------------------------------------------------> <decrypt>
    
    public static String decrypt(String encrypted) {    

        String plaintext = "";                                                  //Creates a varibale to store the decrypted text
        String encryptedKey = "";                                               //Creates a variable to store the encrypted key
        String tempString = "";                                                 //Creates a variable to store temporary strings

        char[] keyChars = KEY.toCharArray();                                    //Creates a character array to hold each character in the key
        int[] keyValues = new int[KEY.length()];                                //Creates an integer array to hold the key's character values

        for (int i = 0; i < KEY.length(); i++) {
            keyValues[i] = (int) keyChars[i];                                   //Converts each character in the key into an integer
            tempString = tempString + keyValues[i];                             //Adds each integer to the temporary string

        }

        char[] encryptedKeyChars = tempString.toCharArray();                    //Creates a character array to hold each character in the temporary string
        int[] encryptedKeyValues = new int[tempString.length()];                //Creates an integer array to hold the temporary string's character values

            //Encrypts the key, so that the program will know how to split the data
            
        for (int i = 0; i < tempString.length(); i++) {
            encryptedKeyValues[i] = (int) encryptedKeyChars[i] + 10;            //Adds 10 to the integr value of each character in the temporary string
            encryptedKey = encryptedKey + (char) encryptedKeyValues[i];         //Converts the new integer value back to a character
        }

            //The variable encryptedKey now holds what the key looks like after it has been encrypted
            
        String[] splitPlaintext = encrypted.split(Pattern.quote(encryptedKey)); //splits the encrypted text around the encrypted key
        
            //Each element in the "splitplaintext" array now holds an encrypted character value

        for (int i = 0; i < splitPlaintext.length; i++) {

            char[] tempChars = splitPlaintext[i].toCharArray();                 //Stores each character in the spot "i" of the "splitPlaintext" array in a temporary character array
            splitPlaintext[i] = "";                                             //Clears the spot "i" of the "splitPlaintext" array

            for (int j = 0; j < tempChars.length; j++) {
                tempChars[j] = (char) ((int) tempChars[j] - 10);                //Subtracts 10 from the integer value of each character in the temporary character array, then converts it back to a character
                splitPlaintext[i] = splitPlaintext[i] + tempChars[j];           //Stores each character back in the "splitPlaintext" array
            }

                //The spot "i" of the "splitPlaintext" array now holds the intger value of the plaintext character as an integer 
                
            splitPlaintext[i] = String.valueOf((char) ((int) Integer.valueOf(splitPlaintext[i])));  //Converts the string into an integer, then into a character 
            plaintext = plaintext + splitPlaintext[i];                          //Adds this character onto the end of the plaintext string

        }

        return plaintext; //returns the plaintext string

    } //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------> </decrypt>

    
    
    
    
    
    //Creates a public method called read that takes a string varaible "fileName" ----------------------------------------------------------------------------------------------------> <read>
    
    public static String[] read(String fileName) {  

        int numLines = 0;                                                       //Declares an integer variable to store the number of lines in the file
        
        try {

            FileReader file = new FileReader(fileName);                         //Creates a FileReader to open the file

            try (BufferedReader buffer = new BufferedReader(file)) {            //Creates a BufferedReader to read the file

                String line;                                                    //Creates a String variable called line
                while ((line = buffer.readLine()) != null) {                    //Runs until it reaches the end of the file (line is null)
                    numLines++;                                                 //Increases the number of lines by one
                }
                
                buffer.close();                                                 //Closes the BufferedReader
            }

        } catch (IOException e) {}                                              //Stops an error from crashing the program

        String[] contents = new String[numLines];                               //Creates a string variable to hold the contents of the file

        try {

            FileReader file = new FileReader(fileName);                         //Creates a FileReader to open the file

            try (BufferedReader buffer = new BufferedReader(file)) {            //Creates a BufferedReader to read the file

                for (int i = 0; i < numLines; i++) {                            
                    contents[i] = decrypt(buffer.readLine());                   //Sets each spot in the contents array equal to a decrypted line in the file
                }
                
                buffer.close();                                                 //Closes the BufferedReader
            }

        } catch (IOException e) {}                                              //Stops an error from crashing the program

        return contents;                                                        //return a string array of the contents

    } //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------> </read>

    
    
    
    
    
    //Creates the public method "write" that takes a file name, an array of new content, and whether or not it should  keep the old content ------------------------------------------> <write>
    
    public static void write(String fileName, String[] newContent, boolean keepOldContent) {    
        
        String[] originalContent = read(fileName);                              //Creates an array that holds the orginal contents of the file 

        try {

            FileWriter fstream = new FileWriter(fileName);                      //Creates a FileWriter to write to the file

            try (BufferedWriter out = new BufferedWriter(fstream)) {            //Creates a BufferedWriter to write to the file

                if (keepOldContent) {                                           //If ther user choses to keep the old content:

                    for (String originalContent1 : originalContent) {           //For each string in the array of old content:

                        out.write(encrypt(originalContent1));                   //Writes the encrypted old content
                        out.newLine();                                          //Moves the BufferedWriter to the next line
                        
                    }
                }

                for (String newContent1 : newContent) {                         //For each string in the array of new content:
                    out.write(encrypt(newContent1));                            //Write the encrypted new content
                    out.newLine();                                              //Moves the BufferedWriter to the next line
                }

                out.close();                                                    //Closes the BufferedWriter
            }

        } catch (IOException e) {}                                              //Stops an error from crashing the program
        
    } //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------> </write>

    
    
    
    
    
    //Creates a public method that sorts from highest to lowest, and takes a 2-Dimensional array of objects to sort, and the index of the array to sort ------------------------------> <sort>
    
    public static Object[][] sortHighToLow(Object[][] arrayToSort, int sortArrayIndex) { 

        Object tempObject;                                                      //Creates an object to hold temporary values

        for (int i = 1; i < arrayToSort[sortArrayIndex].length; i++) {          //The following uses insertion sort to sort the data
            for (int j = i; j > 0; j--) {
                
                    //BigIntegers are necessary since the time stamp placed on the notepad files is too big for regular integers
                
                BigInteger a = new BigInteger(String.valueOf(arrayToSort[sortArrayIndex][j]));      //Creates a BigInteger to hold the integer value at the spot "j" of the array being sorted
                BigInteger b = new BigInteger(String.valueOf(arrayToSort[sortArrayIndex][j - 1]));  //Creates a BigInteger to hold the integer value at the spot "j-1" of the array being sorted
                
                if (a.compareTo(b) > 0) {                                       // If the BigInteger a is greater than the BigInteger b

                    for (Object[] arrayToSort1 : arrayToSort) {                 //This loop switches all the parallel arrays
                        tempObject = arrayToSort1[j];
                        arrayToSort1[j] = arrayToSort1[j - 1];
                        arrayToSort1[j - 1] = tempObject;
                    }
                    
                }
            }
        }

        return arrayToSort;                                                     //Returns the sorted 2-Dimensional object array

    } //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------> </sort>
    
    
    
}
