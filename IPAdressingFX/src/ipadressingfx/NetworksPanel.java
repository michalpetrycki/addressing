/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipadressingfx;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

/**
 *
 * @author 7
 */
public class NetworksPanel extends HBox{
    
    public NetworksPanel(){
    
        root = new VBox();
        networkPanels = new ArrayList<>();
    
    }
    
    public HBox buildUpPanel(String ip, String mask){
    
        this.getChildren().clear();
        root.getChildren().clear();
        
        String ipAddress = ip;
        String maskAddress = mask;
        int [][][] addresses = calculate(ipAddress, maskAddress);
        
        for(int i = 0; i < 5; i++){
        
            
        tempNetworkPanel = new HBox();
        tempLeftPanel = new VBox();
        tempMiddlePanel = new VBox();
        tempRightPanel = new VBox();
        
        tempIpPanel = new HBox();
        tempFirstPanel = new HBox();
        tempLastPanel = new HBox();
        tempBroadPanel = new HBox();
        
        tempNetworkLabel = new Label();
        tempIpLabel = new Label();
        tempFirstLabel = new Label();
        tempLastLabel = new Label();
        tempBroadLabel = new Label();
        tempIpField = new TextField();
        tempFirstField = new TextField();
        tempLastField = new TextField();
        tempBroadField = new TextField();
        
        tempLeftPanel.getChildren().add(tempNetworkLabel);
        
        switch(i){
        
            case 0: tempNetworkLabel.setText("1st network: ");
                    break;
            case 1: tempNetworkLabel.setText("2nd network: ");
                    break;
            case 2: tempNetworkLabel.setText("3rd network: ");
                break;
            default: tempNetworkLabel.setText((i+1) + "th network: ");
                break;
        }
        
        tempLeftPanel.setMinWidth(80);
        
        
        tempIpLabel.setText("IP: ");
        tempFirstLabel.setText("First usable IP:" );
        tempLastLabel.setText("Last usable IP: ");
        tempBroadLabel.setText("Broadcast IP: ");
        
        tempIpLabel.setMinWidth(80);
        tempLastLabel.setMinWidth(80);
        
        tempFirstLabel.setMinWidth(90);
        tempBroadLabel.setMinWidth(90);
        tempIpPanel.getChildren().addAll(tempIpLabel, tempIpField);
        tempIpPanel.setPadding(new Insets(0, 3, 10, 3));
        tempLastPanel.getChildren().addAll(tempLastLabel, tempLastField);
        tempLastPanel.setPadding(new Insets(0, 3, 0, 3));
        tempMiddlePanel.getChildren().addAll(tempIpPanel, tempLastPanel);
        tempMiddlePanel.setPadding(new Insets(10, 10, 10, 25));
        
        tempFirstPanel.getChildren().addAll(tempFirstLabel, tempFirstField);
        tempFirstPanel.setPadding(new Insets(0, 3, 10, 3));
        tempBroadPanel.getChildren().addAll(tempBroadLabel, tempBroadField);
        tempBroadPanel.setPadding(new Insets(0, 3, 0, 3));
        tempRightPanel.getChildren().addAll(tempFirstPanel, tempBroadPanel);
        tempRightPanel.setPadding(new Insets(10, 10, 10, 10));
        
        tempNetworkPanel.getChildren().addAll(tempLeftPanel, tempMiddlePanel, tempRightPanel);
        tempNetworkPanel.setPadding(new Insets(10, 10, 10, 10));
        tempNetworkPanel.setStyle("-fx-border-color: black;");
        
        
        tempIpField.setEditable(true);
        tempIpField.setText(toDecimal(addresses[i][0]));
        tempFirstField.setEditable(true);
        tempFirstField.setText(toDecimal(addresses[i][1]));
        tempLastField.setEditable(true);
        tempLastField.setText(toDecimal(addresses[i][2]));
        tempBroadField.setEditable(true);
        tempBroadField.setText(toDecimal(addresses[i][3]));
        
        networkPanels.add(tempNetworkPanel);
        
        }
        
        root.getChildren().addAll(networkPanels);
        getChildren().addAll(root);
        return this;
    }
    
    public int[][][] calculate(String ipString, String maskString){
    
        int[][] net1 = new int[4][32];
        int[][] net2 = new int[4][32];
        int[][] net3 = new int[4][32];
        int[][]net4 = new int[4][32];
        int[][] net5 = new int[4][32];
        int[][][] addresses = {net1, net2, net3, net4, net5};
        
        int[] a = toBinary(ipString);
        int[] b = toBinary(maskString);
        int[] hosts = new int[32];
        
        int power = 0;
        int carrier = 0;
        
        //Calculating power of two greter than hosts number 
        for(int i = 31; i >= 0; i--){
        
            if(b[i] == 0 && b[i - 1] == 1)
                power = 31 - i;
        
        }
        
        //Turning hosts value into binary in order to add it to network ID
        for(int i = 31; i >= 0; i--){
        
            if(31 - i == power)
                hosts[i] = 1;
            else 
                hosts[i] = 0;
        
        }
        
        //Setting broadcastIP, lastUsableIP, fistUsableIP and network IP --> 1
        net1[0] = toBinary(ipString);
        net1[3] = broadcast(toBinary(ipString), toBinary(maskString));
        net1[2] = lastUsableIP(net1[3]);
        net1[1] = firstUsableIP(net1[0]);
        
        //Setting broadcastIP, lastUsableIP, fistUsableIP and network IP --> 2
        net2[0] = firstUsableIP(net1[3]);
        net2[1] = firstUsableIP(net2[0]);
        net2[3] = broadcast(net2[0], toBinary(maskString));
        net2[2] = lastUsableIP(net2[3]);
        
        //Setting broadcastIP, lastUsableIP, fistUsableIP and network IP --> 3
        net3[0] = firstUsableIP(net2[3]);
        net3[1] = firstUsableIP(net3[0]);
        net3[3] = broadcast(net3[0], toBinary(maskString));
        net3[2] = lastUsableIP(net3[3]);
        
        //Setting broadcastIP, lastUsableIP, fistUsableIP and network IP --> 4
        net4[0] = firstUsableIP(net3[3]);
        net4[1] = firstUsableIP(net4[0]);
        net4[3] = broadcast(net4[0], toBinary(maskString));
        net4[2] = lastUsableIP(net4[3]);
        
        //Setting broadcastIP, lastUsableIP, fistUsableIP and network IP --> 5
        net5[0] = firstUsableIP(net4[3]);
        net5[1] = firstUsableIP(net5[0]);
        net5[3] = broadcast(net5[0], toBinary(maskString));
        net5[2] = lastUsableIP(net5[3]);
        
        addresses[0][0] = net1[0];
        addresses[0][1] = net1[1];
        addresses[0][2] = net1[2];
        addresses[0][3] = net1[3];
        
        addresses[1][0] = net2[0];
        addresses[1][1] = net2[1];
        addresses[1][2] = net2[2];
        addresses[1][3] = net2[3];
        
        addresses[2][0] = net3[0];
        addresses[2][1] = net3[1];
        addresses[2][2] = net3[2];
        addresses[2][3] = net3[3];
        
        addresses[3][0] = net4[0];
        addresses[3][1] = net4[1];
        addresses[3][2] = net4[2];
        addresses[3][3] = net4[3];
        
        addresses[4][0] = net5[0];
        addresses[4][1] = net5[1];
        addresses[4][2] = net5[2];
        addresses[4][3] = net5[3];
        
        return addresses;
    
    }
    
    public static String firstFiveNetworks(int[]a, int[] b){
    
        String networks = "";
        int hostBits = DetailsPanel.hostBits(b);
        int firstDecimal, secondDecimal, thirdDecimal, lastDecimal;
        firstDecimal = secondDecimal = thirdDecimal = lastDecimal = 0;
        
        for(int i = 0; i < 32; i++){
        
            if(i >= 0 && i < 8){
            
                if(a[i] == 1)
                    firstDecimal += Math.pow(2, 7 - i);
            
            }else if(i >= 8 && i < 16){
            
                if(a[i] == 1)
                    secondDecimal += Math.pow(2, 15 - i);
            
            }else if(i >= 16 && i < 24){
            
                if(a[i] == 1)
                    thirdDecimal += Math.pow(2, 23 - i);
            
            }else if(i >= 24 && i < 32){
            
                if(a[i] == 1)
                    lastDecimal += Math.pow(2, 31 - i);
            
            }
        
        }
        
        for(int i = 0; i < 5; i++){
        
            networks += "" + i + " network: " + firstDecimal + "." + secondDecimal + "."
                + thirdDecimal + "." + lastDecimal + "\n";
            
            lastDecimal += Math.pow(2, hostBits);
            
            if(lastDecimal > 255){
        
                if(thirdDecimal > 255){
            
                    if(secondDecimal > 255){
                    
                        firstDecimal++;
                        secondDecimal = secondDecimal - 256;
                    
                    }
                    else{
                    
                        secondDecimal++;
                        thirdDecimal = thirdDecimal -256;
                    
                    }
            
                }else{
                
                    thirdDecimal++;
                    lastDecimal  = lastDecimal - 256;
                
                }
        
            }
        
        }
        
        
        return networks;
    
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
    
    /**
     * 
     * @param a networkIP
     * @param b mask IP
     * @return broadcast IP
     */
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
            if(a[i] == 1 && nandMask[i] == 1){
            
                broadcast[i] = 1;
            
            //Otherwise equals sum of bits
            }else{
            
                broadcast[i] = a[i] + nandMask[i];
            
            }
        
        }
        
        //Return broadcast IP binary array
        return broadcast;
    
    }
    
    /**
     * Calculates last usable IP(In general broadcast - 1)
     * @param a Broadcast IP
     * @return Last usable host IP 
     */
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
    
    /**
     * Calculates first usable IP address
     * @param a Network IP
     * @return first usable IP 
     */
    public static int[] firstUsableIP(int[] a){
    
        //Declaring variable for first usable address
        int[] firstIP = new int[32];
        //One(1) to binary
        int[] one = new int[32];
        //Variable for carrying bits 
        int carrier = 0;
        
        //Setting up ONE array
        for(int i = 31; i >= 0; i--){
        
            //THe most rught bit = 1
            if(i == 31)
                one[i] = 1;
            
            //All other bits are 0
            else 
                one[i] = 0;
        
        }
        //Looping through network address, starting from end
        for(int i = 31; i >= 0; i--){
        
            //FirstIP array equals networks + one + carrier 
            firstIP[i] = a[i] + one[i] + carrier;
            //If bit > 1; set as 0 and set carrier as 1
            if(firstIP[i] > 1){
            
                firstIP[i] = 0;
                carrier = 1;
            
            }
            //Otherwise set carrier as 0;(If I put 'set as 1' in else clause it 
            //would set everything up as 1; 
            else
                carrier = 0;
            
        }
        
        //Return first usable IP 
        return firstIP;
        
    
    }
    
    /**
     * Return string with decimal IP address
     * @param a IP as array 
     * @return decimal IP
     */
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
    
    
    /**********VARIABLE TO BUILT UP A PANEL **********************************/
    private VBox root;
    
    //=======ITEMS
    private HBox tempNetworkPanel;
    private VBox tempLeftPanel;
    private VBox tempMiddlePanel;
    private VBox tempRightPanel;
    
    private HBox tempIpPanel;
    private HBox tempFirstPanel;
    private HBox tempLastPanel;
    private HBox tempBroadPanel;
    private Label tempNetworkLabel;
    private Label tempIpLabel;
    private Label tempFirstLabel;
    private Label tempLastLabel;
    private Label tempBroadLabel;
    private TextField tempIpField;
    private TextField tempFirstField;
    private TextField tempLastField;
    private TextField tempBroadField;
    
    private ArrayList<HBox> networkPanels;
}
