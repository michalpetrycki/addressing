/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipadressingfx;

import java.util.Arrays;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author 7
 */
public class DetailsPanel extends HBox{
    
    //PANEL CONSTRUCTOR
    public DetailsPanel(){
    
        //Initializing left side of panel
        ipBinaryLabel = new Label();
        maskBinaryLabel = new Label();
        networkBinaryLabel = new Label();
        firstUsableBinaryLabel = new Label();
        lastUsableBinaryLabel = new Label();
        broadcastBinaryLabel = new Label();
        
        ipBinaryText = new Text();
        maskBinaryText = new Text();
        networkBinaryText = new Text();
        firstUsableBinaryText = new Text();
        lastUsableBinaryText = new Text();
        broadcastBinaryText = new Text();
        
        ipBinaryBox = new HBox();
        maskBinaryBox = new HBox();
        networkBinaryBox = new HBox();
        firstUsableBinaryBox = new HBox();
        lastUsableBinaryBox = new HBox();
        broadcastBinaryBox = new HBox();
        
        //Initializing right side of panel 
        ipDecimal = new Label();
        maskDecimal = new Label();
        networkDecimal = new Label();
        firstUsableDecimal = new Label();
        lastUsableDecimal = new Label();
        broadcastDecimal = new Label();
        
    }
    
    
    //Building up panel; Setting default values;
    public void buildUpPanel(){
    
        ipBinaryLabel.setText("IP Binary: ");
        maskBinaryLabel.setText("Mask Binary:" );
        networkBinaryLabel.setText("Network binary");
        firstUsableBinaryLabel.setText("First usable host: ");
        lastUsableBinaryLabel.setText("Last usable host: ");
        broadcastBinaryLabel.setText("Broadcast binary: ");
        
        ipBinaryText.setText("???");
        maskBinaryText.setText("???");
        networkBinaryText.setText("???");
        firstUsableBinaryText.setText("???");
        lastUsableBinaryText.setText("???");
        broadcastBinaryText.setText("???");
        
        ipBinaryBox.getChildren().addAll(ipBinaryLabel, ipBinaryText);
        maskBinaryBox.getChildren().addAll(maskBinaryLabel, maskBinaryText);
        networkBinaryBox.getChildren().addAll(networkBinaryLabel, networkBinaryText);
        firstUsableBinaryBox.getChildren().addAll(firstUsableBinaryLabel, firstUsableBinaryText);
        lastUsableBinaryBox.getChildren().addAll(lastUsableBinaryLabel, lastUsableBinaryText);
        broadcastBinaryBox.getChildren().addAll(broadcastBinaryLabel, broadcastBinaryText);
        
        leftBox.getChildren().addAll(ipBinaryBox, maskBinaryBox, networkBinaryBox,
                firstUsableBinaryBox, lastUsableBinaryBox, broadcastBinaryBox);
        
        leftBox.setMinWidth(350);
        ipBinaryLabel.setMinWidth(100);
        maskBinaryLabel.setMinWidth(100);
        networkBinaryLabel.setMinWidth(100);
        firstUsableBinaryLabel.setMinWidth(100);
        lastUsableBinaryLabel.setMinWidth(100);
        broadcastBinaryLabel.setMinWidth(100);
        
        ipBinaryBox.setPadding(new Insets(0, 0, 10, 10));
        maskBinaryBox.setPadding(new Insets(0, 0, 10, 10));
        networkBinaryBox.setPadding(new Insets(0, 0, 10, 10));
        firstUsableBinaryBox.setPadding(new Insets(0, 0, 10, 10));
        lastUsableBinaryBox.setPadding(new Insets(0, 0, 10, 10));
        broadcastBinaryBox.setPadding(new Insets(0, 0, 10, 10));    
        
        
        
        
        
        
        
        
        ipDecimal.setText("???");
        maskDecimal.setText("???");
        networkDecimal.setText("???");
        firstUsableDecimal.setText("???");
        lastUsableDecimal.setText("???");
        broadcastDecimal.setText("???");
        
        
        rightBox.getChildren().addAll(ipDecimal, maskDecimal, networkDecimal,
                firstUsableDecimal, lastUsableDecimal, broadcastDecimal);
        
        ipDecimal.setPadding(new Insets(0, 0, 10, 110));
        maskDecimal.setPadding(new Insets(0, 0, 10, 110));
        networkDecimal.setPadding(new Insets(0, 0, 10, 110));
        firstUsableDecimal.setPadding(new Insets(0, 0, 10, 110));
        lastUsableDecimal.setPadding(new Insets(0, 0, 10, 110));
        broadcastDecimal.setPadding(new Insets(0, 0, 10, 110));
        
        
        
        getChildren().addAll(leftBox, rightBox);
        
    }
    
    public HBox calculate(String ipString, String maskString){
    
        int[] ip = toBinary(ipString);
        int[] mask = toBinary(maskString);
                
        ipBinaryText.setText(toBinaryString(toBinary(ipString)));
        maskBinaryText.setText(toBinaryString(toBinary(maskString)));
        networkBinaryText.setText(toBinaryString(networkIP(ip, mask)));
        firstUsableBinaryText.setText(toBinaryString(firstUsableIP(ip)));
        lastUsableBinaryText.setText(toBinaryString(lastUsableIP(broadcast(ip, mask))));
        broadcastBinaryText.setText(toBinaryString(broadcast(ip, mask)));

        ipDecimal.setText(toDecimal(ip));
        maskDecimal.setText(toDecimal(mask));
        networkDecimal.setText(toDecimal(networkIP(ip, mask)));
        firstUsableDecimal.setText(toDecimal(firstUsableIP(ip)));
        lastUsableDecimal.setText(toDecimal(lastUsableIP(broadcast(ip, mask))));
        broadcastDecimal.setText(toDecimal(broadcast(ip, mask)));
        
        return this;
    }
    
    public static int[] toBinary(String number){
    
        //Variables for extracting decimals from string 
        int firstNumber, secondNumber, thirdNumber, lastNumber;
        //Assigning 0 to variables
        firstNumber = secondNumber = thirdNumber = lastNumber = 0;
        
        //Creating new array for binary numbers 
        int[] binary = new int[32];
        
        //Extracting first decimal from string
        firstNumber = Integer.parseInt(number.substring(0, number.indexOf(".")));
        //Cutting of first number from string 
        number = number.substring(number.indexOf(".") + 1);
        
        //Extracting second decimal from string
        secondNumber = Integer.parseInt(number.substring(0, number.indexOf(".")));
        //Cutting off second decimal
        number = number.substring(number.indexOf(".") + 1);
        
        //Extracting third decimal from string
        thirdNumber = Integer.parseInt(number.substring(0, number.indexOf(".")));
        //Cutting off third number from string
        number = number.substring(number.indexOf(".") + 1);
        
        //Extracting last decimal from string
        lastNumber = Integer.parseInt(number);
        
        
        //Looping through binary array length
        for(int i = 0; i < 32; i++){
        
            //Extracting first octet 
            if(i >= 0 && i < 8){
            
                //Checking if number is grater than 128, 64, 32...
                if(firstNumber >= Math.pow(2, 7 - i)){
                
                    //If yes, set nth bit as 1
                    binary[i] = 1;
                    //Number is reduced by nth power of 2
                    firstNumber -= Math.pow(2, 7 - i);
                
                }
            
            //Extracting second octet
            }else if(i >= 8 && i < 16){
            
                //Checking if number is greater than 128, 64, 32...
                if(secondNumber >= Math.pow(2, 15 - i)){
                
                    //If yes, set nth bit as 1
                    binary[i] = 1;
                    //Number is reduced by nth power of 2
                    secondNumber -= Math.pow(2, 15 - i);
                
                }
            
            //Extracting third octet 
            }else if(i >= 16 && i < 24){
            
                //Checking if number is greater than 128, 64, 32...
                if(thirdNumber >= Math.pow(2, 23 - i)){
                
                    //If yes, set nth bit as 1
                    binary[i] = 1;
                    //Number is reduced by nth power of 2
                    thirdNumber -= Math.pow(2, 23 - i);
                
                }
            
            //Extracting last octet 
            }else if(i >= 24 && i < 32){
            
                //Checking if number is greater than 128, 64, 32...
                if(lastNumber >= Math.pow(2 , 31 - i)){
                
                    //If yes, set nth bit as 1
                    binary[i] = 1;
                    //Number is reduced by nth power of 2
                    lastNumber -= Math.pow(2, 31 - i);
                
                }
            
            
            }else{
            
                System.out.println("Out of range, bycz");
            
            }
        
        }
        
        return binary;
    
    }
    
    public static String toBinaryString(int[] a){
    
        String str = "";
        
        for(int i = 0; i < 32; i++){
        
            if(i != 0 && i % 8 == 0){
            
                str += "." + a[i];
            
            }else{
            
                str += a[i];
            
            }
        
        }
        
        return str;
    
    }
    
    public static String toDecimal(int[] a){
    
        String decimal = "";
        
        int firstDecimal, secondDecimal, thirdDecimal, lastDecimal;
        
        firstDecimal = secondDecimal = thirdDecimal = lastDecimal = 0;
        
        for(int i = 0; i < a.length; i++){
        
            if(i >= 0 && i < 8){
            
                if(a[i] == 1)
                    firstDecimal += Math.pow(2, 7 - i);
            
            }else if(i >= 8 && i < 16){
            
                if(a[i] == 1)
                    secondDecimal += Math.pow(2, 15 - i);
            
            }else if(i >= 16 && i < 24){
            
                if(a[i] == 1)
                    thirdDecimal += Math.pow(2, 23 - i);
            
            }else{
            
                if(a[i] == 1)
                    lastDecimal += Math.pow(2, 31 - i);
            
            }
        
        }
        
        decimal = "" + firstDecimal + "." + secondDecimal + "." + thirdDecimal
                + "." + lastDecimal;
        
        return decimal;
    
    }
    
    public static int[] networkIP(int[] a, int[] b){
    
        //Declaring networkIP array
        int[] networkID = new int[32];
        
        //Looping through networkIP array
        for(int i = 0; i < 32; i++){
        
            //If bits in IP address and network mask are equal 1, then network IP bit is 1
            if(a[i] == 1 && b[i] == 1)
                networkID[i] = 1;
            
            //Otherwise is 0
            else 
                networkID[i] = 0;
        
        }
        
        //Return array of binary network ip 
        return networkID;
    
    }
    
    /**
     * Calculates first usable IP address
     * @param a Network IP
     * @return first usable IP 
     */
    public static int[] firstUsableIP(int[] a){
    
        //Declaring variable for first usable address
        int[] firstIP = new int[32];
        
        //Looping through netwrk address, starting from end
        for(int i = 31; i >= 0; i--){
            
            //If it is last octet
            if(i < 32 && i > 23){
                
                //If bit equals 1, then turn bit to 0 and add 1 to next bit
                if(a[i] > 0){
                
                    firstIP[i] = 0;
                    firstIP[i - 1] += 1;
                
                }
            
            //Otherwise, first IP bit equals NetworkIP bit
            }else{
            
                firstIP[i] = a[i];
            
            }
            
            
        }
        
        //Return first usable IP 
        return firstIP;
    
    }
    
    public static int[] lastUsableIP(int[] a){
    
        //Creating variable for last IP address
        int[] lastIP = new int[32];
        //Creating variable of copy of broadcast number 
        int[] broadcast = Arrays.copyOf(a, a.length);
        //Creating variable for borrowed bits  
        int counter = 1;
        
        //Looping through broadcast IP, starting with end
        for(int i = 31; i >= 0; i--){
        
            //If it is last octet 
            if(i < 32 && i > 23){
            
                //If bit - borrowed bit equals (-1)
                if(broadcast[i] - counter == -1){
                
                    //Bit is set up to one, and borrowed bit is set up to 1
                    lastIP[i] = 1;
                    counter = 1;
                
                //If bit - borrowed bit equals 0
                }else if(broadcast[i] - counter == 0){
            
                    //Bit is set up to 0 and borrowed bit is set up to 0
                    lastIP[i] = 0;
                    counter = 0;
            
                //Otherwise lastIP bit equals broadcast bit 
                }else
                    lastIP[i] = broadcast[i];
            
            //For octets apart last one 
            }else{
            
                lastIP[i] = broadcast[i];
            
            }
            
            
        }
        
        //Return array of binary last usable IP 
        return lastIP;
    
    }
    
    public static int[] broadcast(int[] a, int[] b){
    
        //Declaring variable for broadcast IP
        int[] broadcast = new int[32];
        //Declaring variable for mask after NAND operation
        int[] nandMask = Arrays.copyOf(b, b.length);
        
        //Looping through broadcast IP
        for(int i = 0; i < 32; i++){
            
            //If broadcast IP bit equals 1, then negate it --> put 0
            if(b[i] == 0)
                nandMask[i] = 1;
            
            //If equals 0 put 1
            else
                nandMask[i] = 0;
            
        }
        
        //Looping through mask length
        for(int i = 0; i < 32; i++){
        
            //If networkIP bit and negated mask bit are equal and equal 1 then
            //Broadcast bit equals 1
            if(a[i] == 1&& nandMask[i] == 1){
            
                broadcast[i] = 1;
            
            //Otherwise equals sum of bits
            }else{
            
                broadcast[i] = a[i] + nandMask[i];
            
            }
        
        }
        
        //Return broadcast IP binary array
        return broadcast;
    
    }
    
    public static int hostBits(int[] a){
    
        //Declaring variable for host bits 
        int hostBits = 0;
        
        //Looping through binary mask
        for(int i = 0; i < 32; i++){
        
            //If bit is equal 0, then host of bits is incremented
            if(a[i] == 0)
                hostBits++;
        
        }
        
        //Return number of hosts bits 
        return hostBits;
    
    }
    
    
    /*******************LEFT BOX VARIABLES *******************/
    VBox leftBox = new VBox();
    
    Label ipBinaryLabel;
    Label maskBinaryLabel;
    Label networkBinaryLabel;
    Label firstUsableBinaryLabel;
    Label lastUsableBinaryLabel;
    Label broadcastBinaryLabel;
            
    Text ipBinaryText;
    Text maskBinaryText;
    Text networkBinaryText;
    Text firstUsableBinaryText;
    Text lastUsableBinaryText;
    Text broadcastBinaryText;
    
    HBox ipBinaryBox;
    HBox maskBinaryBox;
    HBox networkBinaryBox;
    HBox firstUsableBinaryBox;
    HBox lastUsableBinaryBox;
    HBox broadcastBinaryBox;
    
    /*******************RIGHT BOX VARIABLES *******************************/
    VBox rightBox = new VBox();
    
    Label ipDecimal;
    Label maskDecimal;
    Label networkDecimal;
    Label firstUsableDecimal;
    Label lastUsableDecimal;
    Label broadcastDecimal;
    
    
}
