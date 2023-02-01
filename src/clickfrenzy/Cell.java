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

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.PauseTransition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Cell extends Pane {
    
    // =======================================
        // Animations
    // =======================================
    private Timeline animation;
    SequentialTransition seqTr = new SequentialTransition();
    
    private int id = 0; // id / score of each block
    int[][] colourSet = new int[5][4]; // colour sets
    Rectangle r = new Rectangle(0, 0, 5, 5); // rectangles to click
    
    Media click = new Media(getClass().getClassLoader().getResource("music/beep.mp3").toExternalForm());
    MediaPlayer mediaPlayer = new MediaPlayer(click);
    
    public Cell() {
        setStyle("-fx-border-color: white");
        
        r.setStroke(Color.WHITE);
        r.widthProperty().bind(this.widthProperty());
        r.heightProperty().bind(this.heightProperty());
        
        // must be in this order
        selectedColours();
        changeFill(true);
        
        this.getChildren().add(r);
        this.setPrefSize(2000, 2000);
        this.setOnMouseClicked(e -> {
            try {handleMouseClick();}
            catch(Exception ex) {ex.printStackTrace();}
        }); 
        
        animation = new Timeline(new KeyFrame(Duration.INDEFINITE, e -> changeFill(true)));
        animation.setCycleCount(1);
        timeFade();
    }
    
    // return score
    public int getID() {
        return this.id;
    }
    
    // return animation
    public Timeline getAnimation() {
        return this.animation;
    }
    
    public SequentialTransition getSeqAnimation() {
        return this.seqTr;
    }
    
    // =======================================
        // Seq transition Setup
    // =======================================
    private void timeFade() {
        Duration timeh = Duration.millis(500);
        
        FadeTransition ftS = new FadeTransition(timeh, r);
        ftS.setFromValue(0);
        ftS.setToValue(1);
        ftS.setAutoReverse(false);
        ftS.setCycleCount(1);
        
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        
        FadeTransition ftE = new FadeTransition(timeh, r);
        ftE.setFromValue(1);
        ftE.setToValue(0);
        ftE.setAutoReverse(false);
        ftE.setCycleCount(1);
        
        seqTr = new SequentialTransition(ftS, pause);
        seqTr.play();
    }
    
    // =======================================
        // Stop animation
    // =======================================
    public void animStop(boolean globStop) {
        if(globStop == false) {
            seqTr.play();
        
        } else {
            seqTr.stop();
        }
    }
    
    // =======================================
        // Change fill for each block
    // =======================================
    public void changeFill(boolean status) {
        int random;
        
        // if game is still running
        if(status == true) {
            // touchOfMidas switches all to gold
            if(ScoreHeader.touchOfMidas == true) {
                random = 3;
            } 
            // rarity selection
            else {        
                random = (int)(Math.random() * 130);     
                if(random < 50) {
                    random = 0; // green
                } 
                else if(random < 80) {
                    random = 1; // blue
                } 
                else if(random < 99) {
                    random = 2; // pink
                } 
                else if(random < 101) {
                    random = 3; // gold
                } 
                else {
                    random = 4; // grey
                }
            }
        }
        // if game is not running
        else {
            random = 4;
        }
        
        // setup rectangles
        r.setFill(Color.rgb(colourSet[random][0], colourSet[random][1], colourSet[random][2]));
        
        // setup id associated with the rectangle
        id = colourSet[random][3];
        
        // double the score
        if(ScoreHeader.touchOfMidas == true)
            id = id * 2;
        
        this.setDisable(false);
    }
    
    // =======================================
        // Colour Palette
    // =======================================
    private void selectedColours() {
        // pale green
        colourSet[0][0] = 0;
        colourSet[0][1] = 229;
        colourSet[0][2] = 66;
        colourSet[0][3] = 1; // colour ID and score multiplier
        
        // cyan
        colourSet[1][0] = 0;
        colourSet[1][1] = 219;
        colourSet[1][2] = 229;
        colourSet[1][3] = 2; // colour ID
        
        // pink
        colourSet[2][0] = 242;
        colourSet[2][1] = 0;
        colourSet[2][2] = 231;
        colourSet[2][3] = 3; // colour ID
        
        // bright yellow
        colourSet[3][0] = 229;
        colourSet[3][1] = 226;
        colourSet[3][2] = 0;
        colourSet[3][3] = 5; // colour ID
        
        // grey
        colourSet[4][0] = 110;
        colourSet[4][1] = 110;
        colourSet[4][2] = 110;
        colourSet[4][3] = -4; // colour ID
    }
    
    // =======================================
        // Click Event
    // =======================================
    private void handleMouseClick() {
        mediaPlayer.play();
        mediaPlayer.seek(mediaPlayer.getStartTime());
        ClickFrenzy.scoreSystem(id); // update score
            
        this.setDisable(true); // disable block in case user spams block   
        this.changeFill(true); // change fill
        this.getSeqAnimation().play();  // replay animation 
    }
}
