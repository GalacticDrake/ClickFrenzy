/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickfrenzy;

/**
 *
 * @author thefl
 */
import java.io.IOException;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Options extends StackPane {    
    BorderPane optionGroup = new BorderPane();
    BorderPane promptPop = new BorderPane();
    VBox headerGroup = new VBox(5);
    
    Button backBtn = new Button();
    Image backImg = new Image("resources/back.png");
    ImageView backIcon = new ImageView(backImg);
    
    Text headerText = new Text("OPTIONS");
    HBox headerSect = new HBox(5);
    
    VBox innerPromptPop = new VBox();
    Text popUpHeaderText = new Text("Confirm reset");
    Text popUpDescText = new Text();
    HBox popUpButtonSect = new HBox(5); 
    Button yesBtn = new Button("OK");
    Button noBtn = new Button("CANCEL");
    
    VBox optionSect = new VBox(10);
    
    // reset game
    VBox resetSect = new VBox(5);
    Text resetLbl = new Text("Reset Game"); 
    Text resetInfo = new Text();    
    Button resetBtn = new Button("Reset");
    
    Options() {
        // =======================================
            // CSS
        // =======================================
        
        backBtn.getStyleClass().add("backbtn");
        headerText.getStyleClass().add("header-text");
        headerSect.getStyleClass().add("header-container");
        
        innerPromptPop.getStyleClass().add("container");
        optionGroup.getStyleClass().add("container");
        
        resetSect.getStyleClass().add("inner-container");
        innerPromptPop.getStyleClass().add("inner-container");
        
        resetLbl.getStyleClass().add("opt-heading");
        popUpHeaderText.getStyleClass().add("opt-heading");
        resetInfo.getStyleClass().add("prompt-desc");
        
        popUpHeaderText.getStyleClass().add("prompt-heading");
        popUpDescText.getStyleClass().add("prompt-desc");
        popUpButtonSect.getStyleClass().add("promptbtn");
        
        noBtn.getStyleClass().add("confirmbtn");
        yesBtn.getStyleClass().add("confirmbtn");
        resetBtn.getStyleClass().add("resetbtn");
        
        this.getStylesheets().add("fxcss/headerstyles.css");
        this.getStylesheets().add("fxcss/options.css");
        
        // =======================================
            // Alignments
        // =======================================
        
        backBtn.setGraphic(backIcon);
        backIcon.setFitHeight(20);
        backIcon.setFitWidth(20);
        
        headerSect.getChildren().addAll(backBtn, headerText);
        headerSect.setAlignment(Pos.CENTER_LEFT);
        
       
        resetSect.getChildren().addAll(resetLbl, resetInfo, resetBtn);
        
        optionSect.getChildren().addAll(resetSect);
        
        optionGroup.setTop(headerSect);
        optionGroup.setCenter(optionSect);
        
        popUpButtonSect.getChildren().addAll(noBtn, yesBtn);
        
        popUpDescText.setText("Are you sure you want to reset your progress?");
        
        innerPromptPop.getChildren().addAll(popUpHeaderText, popUpDescText, popUpButtonSect);
        popUpButtonSect.setAlignment(Pos.CENTER_RIGHT);
        
        promptPop.setCenter(innerPromptPop);
        
        this.getChildren().addAll(promptPop, optionGroup);
        
        clickOptions();
    }
    
    // =======================================
        // Click Options
    // =======================================
    private void clickOptions() {
        // back button
        backBtn.setOnAction(e -> {
            MainMenu menuSpace = new MainMenu();
            DriverClass.getStage().getScene().setRoot(menuSpace);
        });
        
        // reset button
        resetBtn.setOnAction(e -> {
            confirmChange();
        });
    } 
    
    // =======================================
        // Confirmation Prompt
    // =======================================
    private void confirmChange() {
        promptPop.toFront();
        
        yesBtn.setOnAction(e -> {
            try {
                HighScore.writeUserData(false);
            } catch(IOException ex) {
                ex.printStackTrace();  
            }
            
            resetInfo.setText("Progress has been reset.");
            promptPop.toBack();
        });
        
        noBtn.setOnAction(e -> {
            promptPop.toBack();
        });
    }
}
