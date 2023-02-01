/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickfrenzy;

import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author thefl
 */
public class PauseMenu extends BorderPane {    
    Text gameStatus = new Text("Game Paused");
    Text previewHighScore = new Text();
    
    Label labelHighScore = new Label("HighScore: ", previewHighScore);
    
    Button resumeButton = new Button();
    Image resumeImg = new Image("resources/resume.png");
    ImageView resumeIcon = new ImageView(resumeImg);
    
    Button retryButton = new Button();
    Image retryImg = new Image("resources/restart.png");
    ImageView retryIcon = new ImageView(retryImg);
    
    Button quitButton = new Button();
    Image quitImg = new Image("resources/quit.png");
    ImageView quitIcon = new ImageView(quitImg);
    
    HBox buttonContain = new HBox();
    VBox descContain = new VBox();
    
    public PauseMenu() { 
        
        // =======================================
            // CSS
        // =======================================
        
        this.getStyleClass().add("container");
        
        gameStatus.getStyleClass().add("game-status");
        previewHighScore.getStyleClass().add("high-score");
        labelHighScore.getStyleClass().add("high-score");

        resumeButton.getStyleClass().add("opt-btn");
        retryButton.getStyleClass().add("opt-btn");
        quitButton.getStyleClass().add("opt-btn");
        
        this.getStylesheets().add("fxcss/pause.css");
        
        // =======================================
            // Alignments
        // =======================================
        
        resumeButton.setGraphic(resumeIcon);
        resumeIcon.setFitHeight(40);
        resumeIcon.setFitWidth(40);
        
        retryButton.setGraphic(retryIcon);
        retryIcon.setFitHeight(40);
        retryIcon.setFitWidth(40);
        
        quitButton.setGraphic(quitIcon);
        quitIcon.setFitHeight(40);
        quitIcon.setFitWidth(40);
        
        labelHighScore.setContentDisplay(ContentDisplay.RIGHT);
        
        buttonContain.getChildren().addAll(resumeButton, retryButton, quitButton);
        buttonContain.setAlignment(Pos.CENTER);
        buttonContain.setPadding(new Insets(40, 0, 0, 0));
        buttonContain.setSpacing(10);
        
        descContain.getChildren().addAll(labelHighScore, buttonContain);
        descContain.setAlignment(Pos.CENTER);
        
        this.setTop(gameStatus);
        this.setCenter(descContain);
        
        setAlignment(gameStatus, Pos.CENTER);
        setAlignment(descContain, Pos.CENTER);
    }
}
