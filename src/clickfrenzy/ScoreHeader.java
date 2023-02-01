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
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.ProgressBar;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.effect.Glow; 
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ScoreHeader extends BorderPane {   
    Text timeAndStatusDisplay = new Text(0, 0, "60");
    Text scoreDisplay = new Text(0, 10, "0");
    Text multiplierDisplay = new Text(0, 0, "x1");
    
    Button pauseButton = new Button();
    Image pauseImg = new Image("resources/pause.png");
    ImageView pauseIcon = new ImageView(pauseImg);
    
    public static int highScore = 0;
    public static int scoreCount = 0;
    public static int frenzyCount = 0;
    public static int frenzyCap = 50;
    public static int credits = 0;
    public int timeLeft;
    
    private int abilityTimeLeft;
    private static int constMulti;
    public static int userMultiplier;
    public static int userTime;
    
    public static boolean touchOfMidas = false;
    static boolean availB = true;
    private int colorCount = 0;
    
    ProgressBar frenzyMeter = new ProgressBar();
    BorderPane middlePane = new BorderPane();
    
    VBox scoreDetails = new VBox();
    StackPane multiPane = new StackPane();
    StackPane pausePane = new StackPane();
    
    Glow glow = new Glow();
    
    public Timeline bobbingMultiplier;
    
    public Timeline doubleMultiplierA;
    public Timeline touchOfMidasA;  
    
    public ScaleTransition scaleMeter;
    
    private FadeTransition ftS;
    
    public ScoreHeader() {
        
        // =======================================
            // CSS
        // =======================================
        
        this.getStyleClass().add("container"); 
        
        timeAndStatusDisplay.getStyleClass().add("timer-display");
        scoreDisplay.getStyleClass().add("score-display");
        multiplierDisplay.getStyleClass().add("multiplier-display");
        
        pauseButton.getStyleClass().add("pausebtn");
        
        frenzyMeter.getStyleClass().add("custom-progress-bar");
        
        this.getStylesheets().add("fxcss/scoreboard.css");
        this.getStylesheets().add("fxcss/headerstyles.css");
        
        // =======================================
            // Alignments
        // =======================================
        
        pauseButton.setGraphic(pauseIcon);
        pauseIcon.setFitHeight(30);
        pauseIcon.setFitWidth(20);
        
        multiPane.getChildren().add(multiplierDisplay);
        multiPane.setAlignment(multiplierDisplay, Pos.CENTER);
        multiPane.prefWidthProperty().bind(this.widthProperty().multiply(0.1)); 
        
        frenzyMeter.setProgress(0);
        frenzyMeter.setStyle("-fx-accent: rgb(44, 255, 213);");
        frenzyMeter.prefWidthProperty().bind(scoreDetails.widthProperty());
        frenzyMeter.setOnMouseClicked(e -> handleFrenzyClick());
        
        scoreDetails.setAlignment(Pos.CENTER);
        scoreDetails.getChildren().addAll(timeAndStatusDisplay, scoreDisplay, frenzyMeter);
        
        pausePane.getChildren().add(pauseButton);
        pausePane.setAlignment(pauseButton, Pos.CENTER);
        pausePane.prefWidthProperty().bind(this.widthProperty().multiply(0.1));
        
        this.setLeft(multiPane);
        this.setCenter(scoreDetails);
        this.setRight(pausePane);  
       
        aniConstruct();
    }
    
    // =======================================
        // Construct Animations
    // =======================================
    private void aniConstruct() {
        doubleMultiplierA = new Timeline(new KeyFrame(Duration.seconds(1), e -> doubleMultiplier()));
        doubleMultiplierA.setCycleCount(Timeline.INDEFINITE);
        
        touchOfMidasA = new Timeline(new KeyFrame(Duration.seconds(1), e -> touchOfMidasA()));
        touchOfMidasA.setCycleCount(Timeline.INDEFINITE);
        
        bobbingMultiplier = new Timeline(new KeyFrame(Duration.seconds(1), e -> changeColor()));
        bobbingMultiplier.setCycleCount(Timeline.INDEFINITE);
        ftS = new FadeTransition(Duration.millis(500), multiplierDisplay);
        fadeTrans();
        
        scaleMeter = new ScaleTransition(Duration.millis(500), frenzyMeter);
        scaleMeter.setFromX(1.0);
        scaleMeter.setFromY(1.0);
        scaleMeter.setToX(1.2);
        scaleMeter.setToY(1.2);
        scaleMeter.setCycleCount(Timeline.INDEFINITE);
        scaleMeter.setAutoReverse(true);
    }
    
    private void fadeTrans() {
        ftS.setFromValue(0.5);
        ftS.setToValue(1);
        ftS.setAutoReverse(true);
        ftS.setCycleCount(Timeline.INDEFINITE);
    }
    
    // =======================================
        // Frenzy Click Event
    // =======================================
    private void handleFrenzyClick() {   
        // if frenzyMeter bar is full, and ability is open, enable it
        if(frenzyMeter.getProgress() >= 1 && availB == true) {
            frenzyMeter.setStyle("-fx-accent: rgb(44, 255, 213);");
            frenzyMeter.setProgress(0);
            frenzyCount = 0;
            scaleMeter.stop();
            frenzyMeter.setScaleX(1);
            frenzyMeter.setScaleY(1);
            
            specialAbility();
        }
    }
    
    // =============================================
        // Count score
    // =============================================
    public void scoreControl(int id) {        
        if(userMultiplier == 0)
            userMultiplier = constMulti; // userMultiplier goes 0 sometimes
        
        int temp = id * userMultiplier; // if less than 0, a grey box has been clicked        
        
        // if current score + temp is lower than 0, dont let it breach under 0
        if(scoreCount + temp < 0) {
            scoreCount = 0;
        }
        else {
            scoreCount = scoreCount + temp; // otherwise, store current score

            // increase count for frenzy meter
                // do not let grey block count as a click
            if(temp > 0) {
                if(frenzyCount < frenzyCap) {
                    frenzyCount++;
                } 
                if(frenzyCount == frenzyCap) {
                    glowMeter(); // make bar glow
                }
            }
        }
        
        scoreDisplay.setText(String.valueOf(scoreCount)); // update score display
        frenzyMeter.setProgress(0.02 * frenzyCount); // update frenzy meter
    }
    
    private void glowMeter() {
        frenzyMeter.setStyle("-fx-accent: yellow;");
        glow.setLevel(10);
        scaleMeter.play();
    }  
    
    // =======================================
        // Abilities
    // =======================================
    
    private void specialAbility() {
        int random = (int)(Math.random() * 1000);

        if(random < 799) {
            doubleMController();
            
        } else if(random < 996) {
            touchOfMController();
            
        } else {
            ClickFrenzy.purge();
        }
    }
    
    // =======================================
        // Multiplier
    // =======================================
    public void doubleMController() {
        abilityTimeLeft = 10;
        userMultiplier = userMultiplier * 2;
        
        availB = false;
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                AbilityFooter.changeAbility(1);
                AbilityFooter.progress.setProgress(1);
            }
        });
        
        bobbingMultiplier.play();
        doubleMultiplierA.play();
    }
    
    public void doubleMultiplier() {
        if(abilityTimeLeft == 0) {
            userMultiplier = userMultiplier / 2;
            bobbingMultiplier.stop();
            multiplierDisplay.setText("X" + String.valueOf(userMultiplier));
            multiplierDisplay.setStyle("-fx-fill: rgb(230, 230, 230);");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AbilityFooter.changeAbility(0);
                    AbilityFooter.progress.setProgress(-1);
                }
            });
            availB = true;
            doubleMultiplierA.stop();
        }
        else {
            multiplierDisplay.setText("X" + String.valueOf(userMultiplier));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AbilityFooter.progress.setProgress(0.1 * abilityTimeLeft);
                }
            });
            abilityTimeLeft--;
        }
    }
    
    private void changeColor() {       
        switch(colorCount) {
            case 0:
                multiplierDisplay.setStyle("-fx-font-size: 24px; -fx-font-weight: 700; -fx-fill: rgb(0, 229, 66); -fx-stroke: white;");
                colorCount++;
                break;
            case 1:
                multiplierDisplay.setStyle("-fx-font-size: 24px; -fx-font-weight: 700; -fx-fill: rgb(0, 219, 229); -fx-stroke: white;");
                colorCount++;
                break;
            case 2:
                multiplierDisplay.setStyle("-fx-font-size: 24px; -fx-font-weight: 700; -fx-fill: rgb(242, 0, 231); -fx-stroke: white;");
                colorCount++;
                break;
            case 3:
                multiplierDisplay.setStyle("-fx-font-size: 24px; -fx-font-weight: 700; -fx-fill: rgb(229, 226, 0); -fx-stroke: white;");
                colorCount = 0;
                break;
            default:
        }
    }
    
    // =======================================
        // Touch of Midas
    // =======================================
    
    public void touchOfMController() {
        abilityTimeLeft = 5;
        
        availB = false;
        touchOfMidas = true;
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                AbilityFooter.changeAbility(2);
                AbilityFooter.progress.setProgress(1);
                AbilityFooter.progress.setStyle("-fx-accent: yellow;");
            }
        });
        ClickFrenzy.forceChange(true);
        touchOfMidasA.play();
    }
    
    public void touchOfMidasA() {
        if(abilityTimeLeft == 0) {
            touchOfMidasA.stop();
            touchOfMidas = false;
            ClickFrenzy.forceChange(true);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AbilityFooter.changeAbility(0);
                    AbilityFooter.progress.setProgress(-1);
                    AbilityFooter.progress.setStyle("-fx-accent: rgb(44, 255, 213);");
                }
            });
            AbilityFooter.changeAbility(0);
            availB = true;
        } 
        else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AbilityFooter.progress.setProgress(0.2 * abilityTimeLeft);
                }
            });
            abilityTimeLeft--;
        }
    }
}

