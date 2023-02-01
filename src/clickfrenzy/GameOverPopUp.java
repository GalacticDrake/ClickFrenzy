/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickfrenzy;

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
public class GameOverPopUp extends BorderPane {

    // =======================================
        // GameOver Required Components
    // =======================================
    // text for updated data
    Text gameStatus = new Text("Game Over"); // header text
    Text currentScore = new Text("0"); // current score
    Text previewHighScore = new Text("0"); // high score
    Text currentCredit = new Text("0"); // current credits
    Text receiveCredit = new Text("0"); // credits received
    Text nextTier = new Text("Hit the next score bound (300) to get more credits!"); // boundary text
    
    // labels for static text
    Label labelCurrentScore = new Label("Current score: ", currentScore); 
    Label labelHighScore = new Label("HighScore: ", previewHighScore);
    Label labelCreditR = new Label("Credit received: ", receiveCredit);
    Label labelCredit = new Label("Credit: ", currentCredit);
    
    // =======================================
        // Buttons
    // =======================================
    Button retryButton = new Button();
    Image retryImg = new Image("resources/restart.png");
    ImageView retryIcon = new ImageView(retryImg);
    
    Button quitButton = new Button();
    Image quitImg = new Image("resources/quit.png");
    ImageView quitIcon = new ImageView(quitImg);
    
    HBox buttonContain = new HBox();
    
    VBox container = new VBox();
    VBox scoreContainer = new VBox();
    VBox creditContainer = new VBox();
    
    public GameOverPopUp() {
        
        // =======================================
            // CSS
        // =======================================
        
        this.getStyleClass().add("container");
        
        gameStatus.getStyleClass().add("game-status");
        previewHighScore.getStyleClass().add("high-score");
        labelHighScore.getStyleClass().add("high-score");

        retryButton.getStyleClass().add("opt-btn");
        quitButton.getStyleClass().add("opt-btn");
        
        labelCurrentScore.getStyleClass().add("label");
        labelHighScore.getStyleClass().add("label");
        labelCreditR.getStyleClass().add("label");
        labelCredit.getStyleClass().add("label");
        
        nextTier.getStyleClass().add("data");
        nextTier.setStyle("-fx-font-weight: 800");
        currentScore.getStyleClass().add("data");
        previewHighScore.getStyleClass().add("data");
        currentCredit.getStyleClass().add("data");
        receiveCredit.getStyleClass().add("data");
        
        creditContainer.getStyleClass().add("credit-container");
        
        this.getStylesheets().add("fxcss/pause.css");
        this.getStylesheets().add("fxcss/gameover.css");
        
        // =======================================
            // Alignments
        // =======================================
        
        retryButton.setGraphic(retryIcon);
        retryIcon.setFitHeight(40);
        retryIcon.setFitWidth(40);
        
        quitButton.setGraphic(quitIcon);
        quitIcon.setFitHeight(40);
        quitIcon.setFitWidth(40);
        
        labelCurrentScore.setContentDisplay(ContentDisplay.RIGHT);
        labelHighScore.setContentDisplay(ContentDisplay.RIGHT);
        labelCreditR.setContentDisplay(ContentDisplay.RIGHT);
        labelCredit.setContentDisplay(ContentDisplay.RIGHT);
        
        buttonContain.getChildren().addAll(retryButton, quitButton);
        buttonContain.setAlignment(Pos.CENTER);
        buttonContain.setPadding(new Insets(40, 0, 0, 0));
        buttonContain.setSpacing(10);
        
        scoreContainer.getChildren().addAll(labelCurrentScore, labelHighScore);
        scoreContainer.setAlignment(Pos.CENTER);
        scoreContainer.setPadding(new Insets(20, 0, 20, 0));

        creditContainer.getChildren().addAll(nextTier, labelCreditR, labelCredit);
        creditContainer.setAlignment(Pos.CENTER);
        creditContainer.setPadding(new Insets(20, 0, 20, 0));
        
        container.getChildren().addAll(scoreContainer, creditContainer, buttonContain);
        container.setAlignment(Pos.CENTER);
        
        this.setTop(gameStatus);
        this.setCenter(container);
        
        setAlignment(gameStatus, Pos.CENTER);
        setAlignment(container, Pos.CENTER);
    }
}

