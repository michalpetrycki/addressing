/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipadressingfx;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author 1707586
 */
public class IPAdressingFX extends Application {
    
    @Override
    public void start(Stage primaryStage) {
      
        //Creating box for ip and mask boxes
        topBox = new HBox();
        bottomBox = new HBox();
        
        
        //Creating ip box
        ipBox = new HBox();
        ipLabel = new Label("IP Address:");
        ipField = new TextField();
        ipBox.setPadding(new Insets(0, 15, 15, 15));
        ipBox.getChildren().addAll(ipLabel, ipField);
        
        //Creating mask box======
        maskBox = new HBox();
        maskLabel = new Label("Network mask: ");
        maskField = new TextField();
        
        maskBox.setPadding(new Insets(0, 15, 15, 15));
        maskBox.getChildren().addAll(maskLabel, maskField);
        
        //Creating buttons 
        btn = new Button("Gimme details");
        netBtn = new Button("Gimme networks");
        subBtn = new Button("Do subnetting");
        
        //Setting margins of topBox
        topBox.setPadding(new Insets(15, 0, 15, 0));
        //Adding items to top box 
        topBox.getChildren().addAll(ipBox, maskBox, btn, netBtn, subBtn);
        
        
        //Creating panel for calculating details of networks
        dp = new DetailsPanel();
        //Settin up fields
        dp.buildUpPanel();
        //Adding panel to bottomBox
        bottomBox.getChildren().add(dp);
        
        np = new NetworksPanel();
        sp = new SubnettingPanel();
        
        /*********************Creating main panel**************************/
        root = new VBox();
        
        root.getChildren().addAll(topBox, bottomBox);
        
        scene = new Scene(root, 640, 450);
        
        
        btn.setOnAction(new EventHandler(){
            
            @Override
            public void handle(Event event) {
                
                try{
                
                    String ip_address = ipField.getText();
                    String ip_mask = maskField.getText();
                
                    root.getChildren().clear();
                    bottomBox = dp.calculate(ip_address, ip_mask);
                    root.getChildren().addAll(topBox, bottomBox);
                
                }catch(Exception ex){
                
                    System.out.println("Exception caught: " + ex.getMessage());
                    root.getChildren().addAll(topBox, bottomBox);
                    
                    final Stage dialog = new Stage();
                    VBox dialogBox = new VBox();
                    dialogBox.getChildren().add(new Text("Enter ip and mask"));
                    Scene dialogScene = new Scene(dialogBox, 300, 200);
                    dialog.setScene(dialogScene);
                    dialog.show();
                
                }
                

            }
            
        });
        
        netBtn.setOnAction(new EventHandler(){
        
            @Override
            public void handle(Event event){
                
                String ip_address = ipField.getText();
                String ip_mask = maskField.getText();

                if(ip_address.matches("\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}")
                        || ip_mask.matches("\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}")){

                    try{

                        root.getChildren().clear();
                        bottomBox = np.buildUpPanel(ip_address, ip_mask);
                        root.getChildren().addAll(topBox, bottomBox);
                        primaryStage.setHeight(620);

                    }
                    catch(Exception ex){

                        ex.printStackTrace();
                        final Stage dialog = new Stage();
                        VBox dialogBox = new VBox();
                        dialogBox.getChildren().add(new Text("Wrong ip or mask format!"));
                        Scene dialogScene = new Scene(dialogBox, 300, 200);
                        dialog.setScene(dialogScene);
                        dialog.show();
                        root.getChildren().clear();
                        root.getChildren().addAll(topBox, bottomBox);

                    }


                }
                else{

                    final Stage dialog = new Stage();
                    VBox dialogBox = new VBox();
                    dialogBox.getChildren().add(new Text("Enter ip and mask!"));
                    Scene dialogScene = new Scene(dialogBox, 300, 200);
                    dialog.setScene(dialogScene);
                    dialog.show();

                }
                
            }
        
        });
        
        subBtn.setOnAction(new EventHandler(){
        
            @Override
            public void handle(Event event){
            
                String ip_address = getIP();
                String ip_mask = getMask();

                if(ip_address.matches("\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}")
                        || ip_mask.matches("\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}")){
                    
                    try{

                        root.getChildren().clear();
                        sp.buildUpPanel(ip_address, ip_mask);
                        root.getChildren().addAll(topBox, sp);
                        primaryStage.setHeight(450);

                    }
                    catch(Exception ex){

                        ex.printStackTrace();
                        final Stage dialog = new Stage();
                        VBox dialogBox = new VBox();
                        dialogBox.getChildren().add(new Text("Wrong ip or mask format!"));
                        Scene dialogScene = new Scene(dialogBox, 300, 200);
                        dialog.setScene(dialogScene);
                        dialog.show();
                        root.getChildren().clear();
                        root.getChildren().addAll(topBox, sp);

                    }
                    
                }
                else{

                    final Stage dialog = new Stage();
                    VBox dialogBox = new VBox();
                    dialogBox.getChildren().add(new Text("Enter ip and mask!"));
                    System.out.println(ip_address);
                    System.out.println(ip_mask);
                    Scene dialogScene = new Scene(dialogBox, 300, 200);
                    dialog.setScene(dialogScene);
                    dialog.show();

                }
            
            }
        
        });
        
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public String getIP(){
    
        return ipField.getText();
    
    }
    
    public String getMask(){
    
        return maskField.getText();
    
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private VBox root;
    
    private HBox topBox;
    private HBox ipBox;
    private HBox maskBox;
    private Label ipLabel;
    private Label maskLabel;
    
    private Button btn;
    private Button netBtn;
    private Button subBtn;
    
    
    private HBox bottomBox;
    private DetailsPanel dp;
    private NetworksPanel np;
    private SubnettingPanel sp;
    
    private Scene scene;
    
    private TextField ipField;
    private TextField maskField;
    
}
