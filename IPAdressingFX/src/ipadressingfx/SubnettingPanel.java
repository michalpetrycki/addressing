/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipadressingfx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author 7
 */
public class SubnettingPanel extends VBox{
    
    public SubnettingPanel(){
    
        bottomPanel = new VBox();
      
        topPanel = new VBox();
        howManyNetworksPanel = new HBox();
        networksText = new Text();
        networksCombo = new ComboBox();
        options = FXCollections.observableArrayList();
        hostsList = FXCollections.observableArrayList();   
        
        hostsNumber = "0";
      
    }
    
    public VBox buildUpPanel(String ip, String mask){

        this.getChildren().clear();
        bottomPanel.getChildren().clear();
        topPanel.getChildren().clear();
        howManyNetworksPanel.getChildren().clear();
        
        //Top panel 
        howManyNetworksPanel.setMinWidth(320);
        howManyNetworksPanel.setMinHeight(80);
        networksText.setText("How many networks do you need?");
        howManyNetworksPanel.getChildren().addAll(networksText, networksCombo);
        //Adding values to list choose from as number of networks needed 
        options.addAll(
        
            "1", "2", "3", "4", "5"
        
        );
        
        //Assingning passed parameters as local variables 
        ipAddress = ip;
        maskAddress = mask;
        
        //Setting up list of possible values to 'how many networks' comboBox 
        networksCombo.setItems(options);
        //Selecting first item 
        networksCombo.getSelectionModel().selectFirst();
        
        /********************************Creating panel for first network******/
        //MAIN PANEL
        networkPanels = new ArrayList<>();
        
        //LEFT PANEL
        labelPanels = new ArrayList();
        networkLabels = new ArrayList();
        
        
        //MIDDLE PANEL
        fieldsPanels = new ArrayList<>();
        ipBoxes = new ArrayList();
        maskBoxes = new ArrayList();
        firstBoxes = new ArrayList();
        lastBoxes = new ArrayList();
        broadBoxes = new ArrayList();
        ipLabels = new ArrayList();
        maskLabels = new ArrayList();
        firstLabels = new ArrayList();
        lastLabels = new ArrayList();
        broadLabels = new ArrayList();
        ipFields = new ArrayList();
        maskFields = new ArrayList();
        firstFields = new ArrayList();
        lastFields = new ArrayList();
        broadFields = new ArrayList();
        
        
        //RIGHT PANEL
        hostsComboPanels = new ArrayList<>();
        hostsCombo = new ArrayList<>();
        
        //Setting up hosts amount
        for(int i = 0; i < 129; i++){
        
            hostsList.add("" + i);
        
        }
        
        //Adding items of first network into lists 
        networkPanels.add(0, new HBox());
        labelPanels.add(0, new VBox());
        fieldsPanels.add(0, new VBox());
        hostsComboPanels.add(0, new VBox());
        networkLabels.add(0, new Label("1st network: "));
        ipBoxes.add(0, new HBox());
        maskBoxes.add(0, new HBox());
        firstBoxes.add(0, new HBox());
        lastBoxes.add(0, new HBox());
        broadBoxes.add(0, new HBox());
        ipLabels.add(0, new Label("IP Address: "));
        maskLabels.add(0, new Label("Mask Address: "));
        firstLabels.add(0, new Label("First IP Address: "));
        lastLabels.add(0, new Label("Last IP Address: "));
        broadLabels.add(0, new Label("Broadcast Address: "));
        
        ipFields.add(0, new TextField());
        maskFields.add(0, new TextField());
        firstFields.add(0, new TextField());
        lastFields.add(0, new TextField());
        broadFields.add(0, new TextField());
        
        ipFields.get(0).setEditable(false);
        maskFields.get(0).setEditable(false);
        firstFields.get(0).setEditable(false);
        lastFields.get(0).setEditable(false);
        broadFields.get(0).setEditable(false);
        
        hostsCombo.add(0, new ComboBox(hostsList));
        hostsCombo.get(0).getSelectionModel().selectFirst();
        
        //Setting action for first combobox with hosts per mask 
        hostsCombo.get(0).setOnAction(new EventHandler(){
        
            @Override
            public void handle(Event event){
            
                //Getting source of action 
                ComboBox cb = (ComboBox) event.getSource();
                //Setting up all fields of first network 
                ipFields.get(0).setText(ipAddress);
                maskFields.get(0).setText(calculateMask(maskAddress, cb.getSelectionModel().getSelectedItem().toString()));
                firstFields.get(0).setText("" + toDecimal(firstUsableIP(toBinary(ipAddress))));
                lastFields.get(0).setText("" + toDecimal(lastUsableIP(broadcast(toBinary(ipAddress), toBinary(maskFields.get(0).getText())))));
                broadFields.get(0).setText("" + toDecimal(broadcast(toBinary(ipAddress), toBinary(maskFields.get(0).getText()))));
                
                //Setting next combobox as available to choose from. It prevents erorrs while choosing mask for networks which are not calculated yet.
                hostsCombo.get(1).setDisable(false);
            
            } 
                
        });
        
        //ADDING ITEMS TO LEFT PANEL 
        labelPanels.get(0).getChildren().addAll(networkLabels.get(0));
        
        //ADDING ITEMS TO MIDDLE PANEL 
        ipBoxes.get(0).getChildren().addAll(ipLabels.get(0), ipFields.get(0));
        maskBoxes.get(0).getChildren().addAll(maskLabels.get(0), maskFields.get(0));
        firstBoxes.get(0).getChildren().addAll(firstLabels.get(0), firstFields.get(0));
        lastBoxes.get(0).getChildren().addAll(lastLabels.get(0), lastFields.get(0));
        broadBoxes.get(0).getChildren().addAll(broadLabels.get(0), broadFields.get(0));
        fieldsPanels.get(0).getChildren().addAll(ipBoxes.get(0), maskBoxes.get(0),
            firstBoxes.get(0), lastBoxes.get(0), broadBoxes.get(0));
        
        //ADDING ITEMS TO RIGHT PANEL
        hostsComboPanels.get(0).getChildren().addAll(hostsCombo.get(0));
        
        //ADDING PANELS INTO MAIN PANEL 
        networkPanels.get(0).getChildren().addAll(labelPanels.get(0), fieldsPanels.get(0), hostsComboPanels.get(0));
        bottomPanel.getChildren().addAll(networkPanels);
        
        networkLabels.get(0).setMinWidth(80);
        hostsComboPanels.get(0).setPadding(new Insets(0, 0, 0, 5));
        networkPanels.get(0).setPadding(new Insets(0, 0, 0, 5));
        networkPanels.get(0).setStyle("-fx-border-color: black;");
        
        //Setting sizes of labels 
        networkLabels.get(0).setPadding(new Insets(50, 10, 0, 10));
        networkLabels.get(0).setMinWidth(100);
        ipLabels.get(0).setMinWidth(115);
        maskLabels.get(0).setMinWidth(115);
        firstLabels.get(0).setMinWidth(115);
        lastLabels.get(0).setMinWidth(115);
        broadLabels.get(0).setMinWidth(115);
        
        //Moving hosts combob box more to the middle 
        hostsComboPanels.get(0).setPadding(new Insets(50, 0, 0, 50));
        
        //If networks comboBox has been changed build up a number of networks 
        networksCombo.setOnAction((Event event) -> {

            //Gettin curernt sieze, how many networks it is 
            int currentSize = networkPanels.size();
            //Operating on Strings not indeces so 3 == 3 not 3 == 2
            int selectedSize = Integer.parseInt(networksCombo.getSelectionModel().getSelectedItem().toString());
            //How many networks add or remove 
            int difference = Math.abs(currentSize - selectedSize);
            
            //Changing window size regarding to number of networks 
            switch(selectedSize){

                case 1: getParent().getScene().getWindow().setHeight(450);
                        getParent().getScene().getWindow().setY(0);
                    break;
                case 2: getParent().getScene().getWindow().setHeight(570);
                        getParent().getScene().getWindow().setY(0);
                    break;
                case 3: getParent().getScene().getWindow().setHeight(680);
                        getParent().getScene().getWindow().setY(0);
                    break;
                case 4: getParent().getScene().getWindow().setHeight(790);
                        getParent().getScene().getWindow().setY(0);
                    break;
                case 5: getParent().getScene().getWindow().setHeight(900);
                        getParent().getScene().getWindow().setY(0);
                    break;
                
                default: getParent().getScene().getWindow().setHeight(450);
                    break;

            }
            
            
            //Operates only when size changes; if not does nothing 
            if(selectedSize != currentSize){

                //When networks have to be added 
                if(selectedSize > currentSize){

                    //Copies everything into copy array list; Especially first network 
                    //Which is added before action on network combo is taken 
                    ArrayList<HBox> np = new ArrayList<>(networkPanels);
                    ArrayList<VBox> lp = new ArrayList<>(labelPanels);
                    ArrayList<VBox> fp = new ArrayList<>(fieldsPanels);
                    ArrayList<VBox> hp = new ArrayList<>(hostsComboPanels);
                    ArrayList<Label> nl = new ArrayList<>(networkLabels);
                    ArrayList<Label> il = new ArrayList<>(ipLabels);
                    ArrayList<Label> ml = new ArrayList<>(maskLabels);
                    ArrayList<Label> fl = new ArrayList<>(firstLabels);
                    ArrayList<Label> ll = new ArrayList<>(lastLabels);
                    ArrayList<Label> bl = new ArrayList<>(broadLabels);
                    ArrayList<TextField> ipf = new ArrayList<>(ipFields);
                    ArrayList<TextField> mf = new ArrayList<>(maskFields);
                    ArrayList<TextField> ff = new ArrayList<>(firstFields);
                    ArrayList<TextField> lf = new ArrayList<>(lastFields);
                    ArrayList<TextField> bf = new ArrayList<>(broadFields);
                    ArrayList<HBox> ib = new ArrayList<>(ipBoxes);
                    ArrayList<HBox> mb = new ArrayList<>(maskBoxes);
                    ArrayList<HBox> fb = new ArrayList<>(firstBoxes);
                    ArrayList<HBox> lb = new ArrayList<>(lastBoxes);
                    ArrayList<HBox> bb = new ArrayList<>(broadBoxes);
                    ArrayList<ComboBox> h = new ArrayList<>(hostsCombo);

                    
                    //Copying into lists 
                    Collections.copy(np, networkPanels);
                    Collections.copy(lp, labelPanels);
                    Collections.copy(fp, fieldsPanels);
                    Collections.copy(hp, hostsComboPanels);
                    Collections.copy(nl, networkLabels);
                    Collections.copy(il, ipLabels);
                    Collections.copy(ml, maskLabels);
                    Collections.copy(fl, firstLabels);
                    Collections.copy(ll, lastLabels);
                    Collections.copy(bl, broadLabels);
                    Collections.copy(ipf, ipFields);
                    Collections.copy(mf, maskFields);
                    Collections.copy(ff, firstFields);
                    Collections.copy(lf, lastFields);
                    Collections.copy(bf, broadFields);
                    Collections.copy(ib, ipBoxes);
                    Collections.copy(mb, maskBoxes);
                    Collections.copy(fb, firstBoxes);
                    Collections.copy(lb, lastBoxes);
                    Collections.copy(bb, broadBoxes);
                    Collections.copy(h, hostsCombo);

                    
                    //Clearing all lists 
                    networkPanels.clear();
                    labelPanels.clear();
                    fieldsPanels.clear();
                    hostsComboPanels.clear();
                    networkLabels.clear();
                    ipLabels.clear();
                    maskLabels.clear();
                    firstLabels.clear();
                    lastLabels.clear();
                    broadLabels.clear();
                    ipFields.clear();
                    maskFields.clear();
                    firstFields.clear();
                    lastFields.clear();
                    broadFields.clear();
                    ipBoxes.clear();
                    maskBoxes.clear();
                    firstBoxes.clear();
                    lastBoxes.clear();
                    broadBoxes.clear();
                    hostsCombo.clear();

                    
                    //Copying back into original lists 
                    networkPanels = np;
                    labelPanels = lp;
                    fieldsPanels = fp;
                    hostsComboPanels = hp;
                    networkLabels = nl;
                    ipLabels = il;
                    maskLabels = ml;
                    firstLabels = fl;
                    lastLabels = ll;
                    broadLabels = bl;
                    ipFields = ipf;
                    maskFields = mf;
                    firstFields = ff;
                    lastFields = lf;
                    broadFields = bf;
                    ipBoxes = ib;
                    maskBoxes = mb;
                    firstBoxes = fb;
                    lastBoxes = lb;
                    broadBoxes = bb;
                    hostsCombo = h;

                    
                    //Looping through difference between current size and new size 
                    //Chosen form combobox 
                    for(int i = 1; i < difference + 1; i++){

                        //Temporary variables which are used to set fields and panels 
                        //Later they are added to lists 
                        tempNetworkPanel = new HBox();
                        tempLabelPanel = new VBox();
                        tempFieldsPanel = new VBox();
                        tempHostsPanel = new VBox();
                        tempNetworkLabel = new Label();
                        tempIpLabel = new Label();
                        tempMaskLabel = new Label();
                        tempFirstLabel = new Label();
                        tempLastLabel = new Label();
                        tempBroadLabel = new Label();
                        tempIpField = new TextField();
                        tempMaskField = new TextField();
                        tempFirstField = new TextField();
                        tempLastField = new TextField();
                        tempBroadField = new TextField();
                        tempIpBox = new HBox();
                        tempMaskBox = new HBox();
                        tempFirstBox = new HBox();
                        tempLastBox = new HBox();
                        tempBroadBox = new HBox();
                        tempHostsCombo = new ComboBox();

                        
                        //Adding items to particular panels 
                        tempLabelPanel.getChildren().add(tempNetworkLabel);
                        tempIpBox.getChildren().addAll(tempIpLabel, tempIpField);
                        tempMaskBox.getChildren().addAll(tempMaskLabel, tempMaskField);
                        tempFirstBox.getChildren().addAll(tempFirstLabel, tempFirstField);
                        tempLastBox.getChildren().addAll(tempLastLabel, tempLastField);
                        tempBroadBox.getChildren().addAll(tempBroadLabel, tempBroadField);
                        tempFieldsPanel.getChildren().addAll(tempIpBox, tempMaskBox, tempFirstBox,
                                tempLastBox, tempBroadBox);
                        tempHostsPanel.getChildren().addAll(tempHostsCombo);
                        tempNetworkPanel.getChildren().addAll(tempLabelPanel, tempFieldsPanel, tempHostsPanel);

                        
                        //Variable which reads networkPanels size and adds it as label count 
                        int ps = networkPanels.size();
                        switch (ps){

                            case 0: tempNetworkLabel.setText("1st network: ");
                            break;

                            case 1: tempNetworkLabel.setText("2nd network: ");
                            break;

                            case 2: tempNetworkLabel.setText("3rd network: ");
                            break;

                            default: tempNetworkLabel.setText((ps + 1) + "th network: ");
                            break;

                        }
                        
                        
                        //hosts combo are bu default blocked to prevent errors while choosing 
                        //hosts number for networks not calculated yet 
                        tempHostsCombo.setDisable(true);
                        
                        
                        //Adding new mask to fields regarding to number of hosts 
                        tempHostsCombo.setOnAction(new EventHandler(){

                            @Override
                            public void handle(Event event){

                                //Creating index of calling hosts combobox
                                int index = 0;
                                ComboBox a = (ComboBox) event.getSource();
                                //Taking number of hosts 
                                hostsNumber = a.getSelectionModel().getSelectedItem().toString();

                                //Setting up index which is used to call appropriate 
                                //Fields regarding to their indeces 
                                for(ComboBox x: hostsCombo){

                                    if(x.equals(a))
                                        index = hostsCombo.indexOf(x);

                                }

                                //Taking fields regarding to index 
                                TextField nf = ipFields.get(index);
                                TextField mf = maskFields.get(index);
                                TextField ff = firstFields.get(index);
                                TextField lf = lastFields.get(index);
                                TextField bf = broadFields.get(index);
                                hostsNumber = hostsCombo.get(index).getSelectionModel().getSelectedItem().toString();

                                //First network is calculated using arguments passed 
                                //From fields on the bottom, from main application
                                if(index == 0){

                                    //Setting up all fields 
                                    nf.setText(ipAddress);
                                    mf.setText(calculateMask(maskAddress, hostsNumber));
                                    ff.setText("" + toDecimal(firstUsableIP(toBinary(ipAddress))));
                                    bf.setText("" + toDecimal(broadcast(toBinary(ipAddress), toBinary(maskFields.get(index).getText()))));
                                    lf.setText("" + toDecimal(lastUsableIP(broadcast(toBinary(ipAddress), toBinary(maskFields.get(index - 1).getText())))));

                                    //Changing window size 
                                    getParent().getScene().getWindow().setHeight(450);
                                    
                                    //Setting next hosts combo as available to choose 
                                    hostsCombo.get(index + 1).setDisable(false);
                                    
                                    
                                }
                                
                                
                                //All other are calculated using previous network 
                                else{

                                    //Setting up mask field 
                                    mf.setText(calculateMask(maskAddress, hostsNumber));
                                    
                                    //ipAddress takes ipAddress from previous network 
                                    ipAddress = ipFields.get(index - 1).getText();
                                    
                                    //hostsNumber takes number of hosts from previous network 
                                    hostsNumber = hostsCombo.get(index - 1).getSelectionModel().getSelectedItem().toString();

                                    //Setting up all other fields 
                                    nf.setText(calculateAddress(ipAddress, hostsNumber));
                                    ff.setText("" + toDecimal(firstUsableIP(toBinary(nf.getText()))));
                                    bf.setText("" + toDecimal(broadcast(toBinary(nf.getText()), toBinary(maskFields.get(index).getText()))));
                                    lf.setText("" + toDecimal(lastUsableIP(broadcast(toBinary(nf.getText()), toBinary(maskFields.get(index).getText())))));
                                    
                                    
                                    //Setting next hosts combo box as available to choose 
                                    if(index < networkPanels.size() - 1)
                                        hostsCombo.get(index + 1).setDisable(false);

                                }
                                
                                //Adding fields into their lists 
                                maskFields.set(index, mf);
                                ipFields.set(index, nf);
                                firstFields.set(index, ff);
                                lastFields.set(index, lf);
                                broadFields.set(index, bf);

                            }

                        });


                        //Setting sizes and pading for networkPanel 
                        tempNetworkLabel.setMinWidth(100);
                        tempNetworkLabel.setPadding(new Insets(50, 10, 0, 10));
                        
                        //Setting up values for label         
                        tempIpLabel.setText("IP Address: ");
                        tempMaskLabel.setText("Mask address: ");
                        tempFirstLabel.setText("First IP: ");
                        tempLastLabel.setText("Last IP: ");
                        tempBroadLabel.setText("Broadcast: ");
                        
                        //Setting labels sizes 
                        tempIpLabel.setMinWidth(115);
                        tempMaskLabel.setMinWidth(115);
                        tempFirstLabel.setMinWidth(115);
                        tempLastLabel.setMinWidth(115);
                        tempBroadLabel.setMinWidth(115);
                        
                        //Adding possibl hosts to choose from to temporary comboBox 
                        tempHostsCombo.setItems(hostsList);
                        //Selecting first item as chosen
                        tempHostsCombo.getSelectionModel().selectFirst();
                        
                        //Adding margins to panels 
                        tempHostsPanel.setPadding(new Insets(50, 0, 0, 50));
                        tempNetworkPanel.setPadding(new Insets(20, 0, 0, 5));
                        
                        //Setting all fields as unavailable to edit 
                        tempIpField.setEditable(false);
                        tempMaskField.setEditable(false);
                        tempFirstField.setEditable(false);
                        tempLastField.setEditable(false);
                        tempBroadField.setEditable(false);

                        
                        //Adding items to their lists 
                        networkPanels.add(tempNetworkPanel);
                        labelPanels.add(tempLabelPanel);
                        fieldsPanels.add(tempFieldsPanel);
                        hostsComboPanels.add(tempHostsPanel);
                        networkLabels.add(tempNetworkLabel);
                        ipLabels.add(tempIpLabel);
                        maskLabels.add(tempMaskLabel);
                        firstLabels.add(tempFirstLabel);
                        lastLabels.add(tempLastLabel);
                        broadLabels.add(tempBroadLabel);
                        ipFields.add(tempIpField);
                        maskFields.add(tempMaskField);
                        firstFields.add(tempFirstField);
                        lastFields.add(tempLastField);
                        broadFields.add(tempBroadField);
                        ipBoxes.add(tempIpBox);
                        maskBoxes.add(tempMaskBox);
                        firstBoxes.add(tempFirstBox);
                        lastBoxes.add(tempLastBox);
                        broadBoxes.add(tempBroadBox);
                        hostsCombo.add(tempHostsCombo);

                    }
                }
                
                //If networks have to be removed
                else{

                    //Removing as many networks as selected from networksComboBox 
                    for(int i = 1; i < difference + 1; i++){

                        networkPanels.remove(currentSize - i);
                        labelPanels.remove(currentSize - i);
                        fieldsPanels.remove(currentSize - i);
                        hostsComboPanels.remove(currentSize - i);
                        networkLabels.remove(currentSize - i);
                        ipLabels.remove(currentSize - i);
                        maskLabels.remove(currentSize - i);
                        firstLabels.remove(currentSize - i);
                        lastLabels.remove(currentSize - i);
                        broadLabels.remove(currentSize - i);
                        ipFields.remove(currentSize - i);
                        maskFields.remove(currentSize - i);
                        firstFields.remove(currentSize - i);
                        lastFields.remove(currentSize - i);
                        broadFields.remove(currentSize - i);
                        ipBoxes.remove(currentSize - i);
                        maskBoxes.remove(currentSize - i);
                        firstBoxes.remove(currentSize - i);
                        lastBoxes.remove(currentSize - i);
                        broadBoxes.remove(currentSize - i);
                        hostsCombo.remove(currentSize - i);

                    }

                }

                //Clearing main networks panel 
                bottomPanel.getChildren().clear();        
                //Adding networks to networks panel 
                bottomPanel.getChildren().addAll(networkPanels);

            }

        });
        
        //Setting minimum width panel 
        bottomPanel.setMinWidth(310);
        
        //Adding networksCombo to its panel 
        topPanel.getChildren().addAll(howManyNetworksPanel);
        
        //Adding both panels into final panel 
        getChildren().addAll(topPanel, bottomPanel);
        
        return this;
        
    }
    
    
    /**
     * Calculates Network address. It takes previous network, calculates how many hosts 
     * are needed, turns it into binary and adds into previous network what gives 
     * me new network IP.
     * @param ip address of previous network 
     * @param nHosts number of hosts per network
     * @return network ip address 
     */
    private String calculateAddress(String ip, String nHosts){
    
        //Array for ip
        int[] a = toBinary(ip);
        //Array for hosts binary
        int[] b = new int[32];
        //Final array
        int[] c = new int[32];
        
        //Power says next power of two, greather than hostsNumber + 2;
        int power = 0;
        
        //Carrier for bits adding 
        int carrier = 0;
        
        //Hosts number is hosts + networkIP and broadcastIP
        int hostsInAddress = Integer.parseInt(nHosts);
        hostsInAddress += 2;
        
        //Calculating power needed for max hosts number
        for(int i = 0; i < 7; i++){
        
            if(Math.pow(2, i) < hostsInAddress && Math.pow(2, i + 1) > hostsInAddress)
                power = i + 1;
            else if(Math.pow(2, i) == hostsInAddress)
                power = i;
        
        }
        
        //Turning hosts into binary value
        for(int i = 31; i >= 0; i--){
        
            if(i == 31 - power)
                b[i] = 1;
            else b[i] = 0;
        
        }
        
        //Calculating network address
        for(int i = 31; i >= 0; i--){

            //Next bit is sum of bit from network + bit from hosts + carrier 
            c[i] = a[i] + b[i] + carrier;
            
            //If sum is greater than 1, bit is 0 and 1 is carried to next bit 
            if(c[i] > 1){
            
                c[i] = 0;
                carrier = 1;
            }
            
            //Oterwise do not carry anything
            else 
                carrier = 0;

        }
        
        //Return decimal IP 
        return toDecimal(c);    
        
    }
    
    
    /**
     * Calculates mask for network, regarding to number of hosts needed. As this is 
     * for small network, it operates only on 128 hosts. 
     * @param mask Default mask 
     * @param nHosts number of hosts needed. 
     * @return 
     */
    private String calculateMask(String mask, String nHosts){
    
        //Final string 
        String str;
        
        //Mask turned into binary 
        int[] a = toBinary(mask);
        //Power its next power of two greater than hosts number 
        int power = 0;
        
        //Hosts number is hosts + networkIP and broadcastIP
        int hostsInMask = Integer.parseInt(nHosts);
        hostsInMask += 2;
        
        
        //It operates only on last octet, because max hosts number is only 128 
        //Calculates power needed to mask.
        for(int i = 0; i < 7; i++){
        
            if(Math.pow(2, i) < hostsInMask && Math.pow(2, i + 1) > hostsInMask)
                power = i + 1;
            else if(Math.pow(2, i) == hostsInMask)
                power = i;
        
        }
        
        //As 'a' is copy of mask, I need to set up only last 8 bits.
        //Sets up 'power' bits as 1s;
        for(int i = 24; i < 32; i++){
        
            if(i + power < 32)
                a[i] = 1;
        
        }
        
        //Return decimal format of mask
        return toDecimal(a);
        
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
    
    /**
     * Converts decimal number to binary
     * @param number decimal number
     * @return binary array
     */
    private static int[] toBinary(String number){
    
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
     * Converts given array into String value 
     * @param a array of int
     * @return String with decimal format 
     */
    public static String toDecimal(int[] a){
    
        //Final String
        String decimal = "";
        
        //Variables for first, second, third and last octet 
        int firstDecimal, secondDecimal, thirdDecimal, lastDecimal;
        
        //Assigning all octets to 0
        firstDecimal = secondDecimal = thirdDecimal = lastDecimal = 0;
        
        //Looping through whole array 
        for(int i = 0; i < a.length; i++){
        
            //If it's first octet --> add next powers of two to first octet 
            if(i >= 0 && i < 8){
            
                if(a[i] == 1)
                    firstDecimal += Math.pow(2, 7 - i);
            
            //If it's second octet --> add next powers of two to second octet 
            }else if(i >= 8 && i < 16){
            
                if(a[i] == 1)
                    secondDecimal += Math.pow(2, 15 - i);
            
            //If it's third octet --> add next powers of two to third octet 
            }else if(i >= 16 && i < 24){
            
                if(a[i] == 1)
                    thirdDecimal += Math.pow(2, 23 - i);
            
            
            //If it's last octet --> add next powers of two to last octet 
            }else{
            
                if(a[i] == 1)
                    lastDecimal += Math.pow(2, 31 - i);
            
            }
        
        }
        
        //Formatting final string using decimals 
        decimal = "" + firstDecimal + "." + secondDecimal + "." + thirdDecimal
                + "." + lastDecimal;
        
        //Return final string 
        return decimal;
    
    }
    
    /***** RIGHT PANEL ********************************************************/
    //Main panel 
    private final VBox topPanel;
    //Panel for question 
    private final HBox howManyNetworksPanel;
    //Text with question 
    private final Text networksText;
    //ComboBox for question 
    private final ComboBox networksCombo;
    //Option in question combobox
    private final ObservableList<String> options;
    /**************************************************************************/
    
    /********LEFT PANEL********************************************************/
    //Main panel 
    private final VBox bottomPanel;
    //Main panel for networks 
    private ArrayList<HBox> networkPanels;
    //Panels for labels
    private ArrayList<VBox> labelPanels;
    //Panels for networkID and mask
    private ArrayList<VBox> fieldsPanels;
    //Panels for combo boxes
    private ArrayList<VBox> hostsComboPanels;
    
    //Variables for label boxes
    private ArrayList<Label> networkLabels;
    private ArrayList<Label> ipLabels;
    private ArrayList<Label> maskLabels;
    private ArrayList<Label> firstLabels;
    private ArrayList<Label> lastLabels;
    private ArrayList<Label> broadLabels;
    
    //Variables for networks panels
    private ArrayList<TextField> ipFields;
    private ArrayList<TextField> maskFields;
    private ArrayList<TextField> firstFields;
    private ArrayList<TextField> lastFields;
    private ArrayList<TextField> broadFields;
    
    private ArrayList<HBox> ipBoxes;
    private ArrayList<HBox> maskBoxes;
    private ArrayList<HBox> firstBoxes;
    private ArrayList<HBox> lastBoxes;
    private ArrayList<HBox> broadBoxes;
    
    //Variables for hosts combo panels
    private ArrayList<ComboBox> hostsCombo;
    
    //List of values for hosts number 
    private final ObservableList<String> hostsList;

    //Local variables which holds parameters form main application, 
    //And hostsNumber to be used inside setOnAction block.
    private String ipAddress;
    private String maskAddress;
    private String hostsNumber;
    
    //Variables for temporary items of 1st network before it is reset in hostsCombo
    //setOnAction body 
    private HBox tempNetworkPanel;
    private VBox tempLabelPanel;
    private VBox tempFieldsPanel;
    private VBox tempHostsPanel;
    private Label tempNetworkLabel;
    private Label tempIpLabel;
    private Label tempMaskLabel;
    private Label tempFirstLabel;
    private Label tempLastLabel;
    private Label tempBroadLabel;
    private TextField tempIpField;
    private TextField tempMaskField;
    private TextField tempFirstField;
    private TextField tempLastField;
    private TextField tempBroadField;
    private ComboBox tempHostsCombo;
    private HBox tempIpBox;
    private HBox tempMaskBox;
    private HBox tempFirstBox;
    private HBox tempLastBox;
    private HBox tempBroadBox;
     
    
}
