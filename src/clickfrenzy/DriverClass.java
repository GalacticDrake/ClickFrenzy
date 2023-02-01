/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickfrenzy;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;

/**
 *
 * @author thefl
 */
public class DriverClass extends Application {
    private static Stage primaryStageX; // copy stage
    
    MainMenu menuSpace = new MainMenu();
        
    @Override
    public void start(Stage primaryStage) {
        primaryStageX = primaryStage;
        
        // default dimensions
        Scene menuScene = new Scene(menuSpace, 800, 800);
        
        primaryStage.setTitle("ClickFrenzy");        
        primaryStage.setScene(menuScene);
        primaryStage.setResizable(false);   
        
        // padding is adjusting the scenes 
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        
        primaryStage.show(); 
        
        primaryStage.setOnCloseRequest(closeEvent -> {
            System.exit(0);
        }); 
    }
    
    // return stage
    public static Stage getStage() {
        return primaryStageX;
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }    
}
    
