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

import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer; 

public class ClickFrenzy extends StackPane {
    
    // =======================================
        // Game Area
    // =======================================
    
    protected static Cell[][] cell = new Cell[10][10]; // blocks area
    private int[] scoreBound = new int[6]; // keeps score boundary
    private boolean startStatus = false; 

    // =======================================
        // Game Animation
    // =======================================
    
    public Timeline gameWatch; // watch timer
    public Timeline setUpTimeA; // setup timer
    
    // =======================================
        // Game Panes
    // =======================================
    
    Pane emptyPaneL = new Pane(); // left pane
    Pane emptyPaneR = new Pane(); // right pane
    AbilityFooter abilityPane = new AbilityFooter(); // bottom pane for abilities
    protected static GridPane gamePane = new GridPane(); // centre pane for game
    protected static ScoreHeader scoreBoard = new ScoreHeader(); // top pane for scoreboard
    PauseMenu pausePane = new PauseMenu(); // pause pane for pause menu
    GameOverPopUp gameOverPane = new GameOverPopUp(); // game over pane for game over pop up
    
    BorderPane gameArea = new BorderPane(); // group entire pane
    
    Stage popupStage = new Stage(StageStyle.TRANSPARENT); // stage
    
    private int currentActiveAbility = 0; // current ability
    
    Media bgm = new Media(getClass().getClassLoader().getResource("music/world_of_fun.mp3").toExternalForm());
    MediaPlayer mediaPlayer = new MediaPlayer(bgm);
        
    public ClickFrenzy() {
        
        // =======================================
            // CSS
        // =======================================
        
        gameArea.setStyle("-fx-background-color: rgb(30, 30, 30)");
        
        // =======================================
            // Alignments
        // =======================================
        
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++)
                gamePane.add(cell[i][j] = new Cell(), j, i);
        
        // set up score
        scoreBound[0] = 1000;
        scoreBound[1] = 5000;
        scoreBound[2] = 20000;
        scoreBound[3] = 50000;
        scoreBound[4] = 100000;
        scoreBound[5] = 500000;     
        
        gameArea.setCenter(gamePane);
        gameArea.setTop(scoreBoard);
        gameArea.setLeft(emptyPaneL);
        gameArea.setRight(emptyPaneR);
        gameArea.setBottom(abilityPane);
        
        emptyPaneL.prefWidthProperty().bind(this.widthProperty().multiply(0.1));
        emptyPaneR.prefWidthProperty().bind(this.widthProperty().multiply(0.1));
        
        gameArea.setMargin(scoreBoard, new Insets(0, 0, 20, 0));
        gameArea.setMargin(abilityPane, new Insets(20, 0, 0, 0));
        
        this.getChildren().addAll(gameOverPane, pausePane, gameArea);
        
        // construct animation
        aniConstruct();
        
        // setup pause menu and click options
        scoreBoard.pauseButton.setOnMouseClicked(e -> handlePauseAction());
        pauseOptions(); 
        
        // play music        
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
              mediaPlayer.seek(Duration.ZERO);
            }
        });
    }
    
    // =======================================
        // Construct Animation
    // =======================================
    private void aniConstruct() {
        gameWatch = new Timeline(new KeyFrame(Duration.seconds(1), e -> gameTime()));
        gameWatch.setCycleCount(Timeline.INDEFINITE);
         
        setUpTimeA = new Timeline(new KeyFrame(Duration.seconds(1), e -> setUpTime()));
        setUpTimeA.setCycleCount(Timeline.INDEFINITE);
    }
    
    // =======================================
        // Pause Activity
    // =======================================
    private void handlePauseAction() {
        if(gameWatch.getStatus() == Animation.Status.RUNNING) 
            gameWatch.pause();
        
        // pause all abilities if running
        if(scoreBoard.doubleMultiplierA.getStatus() == Animation.Status.RUNNING) {
            scoreBoard.doubleMultiplierA.pause();
            currentActiveAbility = 1;
        }
        if(scoreBoard.touchOfMidasA.getStatus() == Animation.Status.RUNNING) {
            scoreBoard.touchOfMidasA.pause();
            currentActiveAbility = 2;
        }    
        
        pausePane.toFront();
    }
    
    // =======================================
        // Game Pane
    // =======================================
    private void pauseOptions() {
        // =======================================
            // Pause Options
        // =======================================
        pausePane.resumeButton.setOnAction(e-> {            
            // resume time
            gameWatch.play();
            
            // resume all animation
            if(currentActiveAbility == 1) {
                scoreBoard.doubleMultiplierA.play();
            }
            if(currentActiveAbility == 2) {
                scoreBoard.touchOfMidasA.play();
            }
            
            currentActiveAbility = 0;            
            pausePane.toBack();
        });
        
        pausePane.retryButton.setOnAction(e-> {
            gameWatch.stop();
            startController();
            pausePane.toBack();
        });
        
        pausePane.quitButton.setOnAction(e-> {
            resetGame();
            mediaPlayer.stop();
            MainMenu menuSpace = new MainMenu();
            DriverClass.getStage().getScene().setRoot(menuSpace); 
        });
        
        // =======================================
            // Game Over Options
        // =======================================
        
        gameOverPane.retryButton.setOnAction(e-> {
            gameWatch.stop();
            gameArea.toFront();
            startController();            
        });
        
        gameOverPane.quitButton.setOnAction(e-> {
            resetGame();
            mediaPlayer.stop();
            MainMenu menuSpace = new MainMenu();
            DriverClass.getStage().getScene().setRoot(menuSpace); 
        });
    }
    
    // =======================================
        // Reset all Blocks
    // =======================================
    public static void forceChange(boolean status) {
        // if status = true if game is running
        for(int j = 0; j < 10; j++) {
            for(int k = 0; k < 10; k++) {
                cell[j][k].setDisable(true);
                cell[j][k].animStop(true);
                cell[j][k].changeFill(status);
                cell[j][k].getSeqAnimation().play();
                cell[j][k].getAnimation().play();                
            }
        }
    }
    
    // =======================================
        // Update Score
    // =======================================
    public static void scoreSystem(int id) {
        Platform.runLater(() -> {
            scoreBoard.scoreControl(id);
        });
    }
    
    // =======================================
        // Manage Game Commencement
    // =======================================
    public void startController() {
        resetGame(); // reset game to 0
        mediaPlayer.play();
        
        // reset any components
        scoreBoard.multiplierDisplay.setText("X" + String.valueOf(scoreBoard.userMultiplier));
        pausePane.previewHighScore.setText(String.valueOf(scoreBoard.highScore));
        
        scoreBoard.timeLeft = 3; // allocate time to 3 for countdown timer to start
        countDown(); // start countdown
    }
    
    // =======================================
        // Reset Entire Game
    // =======================================
    public void resetGame() {
        gameWatch.stop(); // stop if game is running
        gameArea.toFront(); // push game area to front if not
        
        Platform.runLater(() -> {
            scoreBoard.multiplierDisplay.setText("X" + String.valueOf(scoreBoard.userMultiplier)); // multiplier preview + user
            scoreBoard.multiplierDisplay.setStyle("-fx-fill: rgb(230, 230, 230); -fx-stroke: rgb(230, 230, 230);");
            scoreBoard.scoreDisplay.setText("0"); // score preview
        });
        
        scoreBoard.scoreCount = 0; // score user
        scoreBoard.frenzyMeter.setProgress(0); // frenzy meter 
        scoreBoard.frenzyCount = 0; // frenzy meter integer
        
        if(scoreBoard.doubleMultiplierA.getStatus() == Animation.Status.RUNNING) {
            scoreBoard.userMultiplier /= 2; // divide 2
            scoreBoard.doubleMultiplierA.stop(); // reset animations
            scoreBoard.bobbingMultiplier.stop();
        }
        
        if(scoreBoard.touchOfMidasA.getStatus() == Animation.Status.RUNNING) {
            scoreBoard.touchOfMidasA.stop(); // reset midas animation
            scoreBoard.touchOfMidas = false; // force change will reset the gold blocks
        }
        
        scoreBoard.availB = true; // allow abilities to be clicked again
        currentActiveAbility = 0; // reset current active ability
        
        Platform.runLater(() -> {
            AbilityFooter.progress.setProgress(-1);
            AbilityFooter.changeAbility(0);
        });
        
        forceChange(true); // force a big reset on gameplay blocks
    }
    
    // =======================================
        // Countdown before Game Starts
    // =======================================
    public void countDown() { 
        if(startStatus == false) {
            gamePane.setDisable(true); // disable game area
            scoreBoard.pauseButton.setDisable(true); // enable pause button
            
            // print loading for effect
            Platform.runLater(() -> {
                scoreBoard.timeAndStatusDisplay.setText("Loading...");
            });
            
            setUpTimeA.play(); // setup time counts from 3 to 1
            startStatus = true; // enable startStatus for the next run
        } 
        else {
            gamePane.setDisable(false); // enable game area
            scoreBoard.pauseButton.setDisable(false); // enable pause button
            
            scoreBoard.timeLeft = scoreBoard.userTime; // update timeLeft to userTime
            gameWatch.play(); // play
            startStatus = false; // setup for the next retry
        }
    } 
    
    // =======================================
        // Setup Timer
    // =======================================
    public void setUpTime() {
        if(scoreBoard.timeLeft == 0) {
            // display text
            Platform.runLater(() -> {
                scoreBoard.timeAndStatusDisplay.setText("Start!");
            });
            
            setUpTimeA.stop(); // stop animation
            countDown(); // force countDown to unlock 
        } 
        else {
            Platform.runLater(() -> {
                scoreBoard.timeAndStatusDisplay.setText("Starting in " + String.valueOf(scoreBoard.timeLeft + 1));
            });
            
            scoreBoard.timeLeft--; // reduce time by 1
        }
    }
    
    // =======================================
        // Timer Clock
    // =======================================
    public void gameTime() {        
        if(scoreBoard.timeLeft == 0) {
            // print status
            Platform.runLater(() -> {
                scoreBoard.timeAndStatusDisplay.setText("0");
            });
            gameWatch.stop(); // stop game
            gameOver(); // game over
        } 
        else {
            Platform.runLater(() -> {
                scoreBoard.timeAndStatusDisplay.setText(String.valueOf(scoreBoard.timeLeft + 1));
            });
            scoreBoard.timeLeft--; // reduce time
        }
        
        // Reset blocks
        if(scoreBoard.timeLeft % 30 == 0 && scoreBoard.timeLeft < scoreBoard.userTime) {
            forceChange(true);
        }
    }
    
    // =======================================
        // Game Over BTS
    // =======================================
    public void gameOver() {        
        gamePane.setDisable(true); // disable game area
        forceChange(false);

        // add credits
        creditGain();
        
        // store new high score
        if(scoreBoard.highScore < scoreBoard.scoreCount) {
            scoreBoard.highScore = scoreBoard.scoreCount;
            gameOverPane.previewHighScore.setText("New Highscore!");
        } 
        else {
            gameOverPane.previewHighScore.setText(String.valueOf(scoreBoard.highScore));
        }
        
        // stop all animations
        if(scoreBoard.doubleMultiplierA.getStatus() == Animation.Status.RUNNING) {
            scoreBoard.doubleMultiplierA.stop();
            scoreBoard.bobbingMultiplier.stop();
            scoreBoard.userMultiplier /= 2;
        }
        if(scoreBoard.touchOfMidasA.getStatus() == Animation.Status.RUNNING) {
            scoreBoard.touchOfMidasA.stop();
        }
        
        // update user data
        try {
            HighScore.writeUserData(true);
        } catch(IOException ex) {
            ex.printStackTrace();  
        }
        
        // change scene
        gameOverOverlay();
    }
    
    // =======================================
        // Calculate Credits and Boundary
    // =======================================
    private void creditGain() {
        // increase credits
        // print receive credit on Pane
        if(scoreBoard.scoreCount < scoreBound[0]) {
            scoreBoard.credits += 10;
            gameOverPane.nextTier.setText("Hit the next score bound " + String.valueOf(scoreBound[0]) + " to get more credits!");
            gameOverPane.receiveCredit.setText("10");
        } 
        else if(scoreBoard.scoreCount < scoreBound[1]) {
            scoreBoard.credits += 20;
            gameOverPane.nextTier.setText("Hit the next score bound " + String.valueOf(scoreBound[1]) + " to get more credits!");
            gameOverPane.receiveCredit.setText("20");
        }
        else if(scoreBoard.scoreCount < scoreBound[2]) {
            scoreBoard.credits += 30;
            gameOverPane.nextTier.setText("Hit the next score bound " + String.valueOf(scoreBound[2]) + " to get more credits!");
            gameOverPane.receiveCredit.setText("30");
        }
        else if(scoreBoard.scoreCount < scoreBound[3]) {
            scoreBoard.credits += 40;
            gameOverPane.nextTier.setText("Hit the next score bound " + String.valueOf(scoreBound[3]) + " to get more credits!");
            gameOverPane.receiveCredit.setText("40");
        }
        else if(scoreBoard.scoreCount < scoreBound[4]) {
            scoreBoard.credits += 50;
            gameOverPane.nextTier.setText("Hit the next score bound " + String.valueOf(scoreBound[4]) + " to get more credits!");
            gameOverPane.receiveCredit.setText("50");
        }
        else if(scoreBoard.scoreCount < scoreBound[5]) {
            scoreBoard.credits += 60;
            gameOverPane.nextTier.setText("Hit the next score bound " + String.valueOf(scoreBound[5]) + " to get more credits!");
            gameOverPane.receiveCredit.setText("60");
        }
        else {
            scoreBoard.credits += 70;
            gameOverPane.nextTier.setText("You have hit the upper limit!");
            gameOverPane.receiveCredit.setText("70");
        }
        
        // print currentCredit on Game Over Pane
        gameOverPane.currentCredit.setText(String.valueOf(scoreBoard.credits));
    }
    
    // =======================================
        // Game Over Overlay
    // =======================================
    public void gameOverOverlay() {
        // print high score        
        gameOverPane.currentScore.setText(String.valueOf(scoreBoard.scoreCount));
        gameOverPane.toFront();
    }
    
    // =======================================
        // Purge Ability
    // =======================================
    public static void purge() {
        
        Thread delayThread = new Thread(new Runnable() {
            
            @Override
            public void run() {
                try {
                    int total = 0;  

                    for(int j = 0; j < 10; j++) {
                        for(int k = 0; k < 10; k++) {
                            if(cell[j][k].getID() < 0)
                                total = total + 1;
                            else
                                total = total + (cell[j][k].getID() * 5);
                            
                            cell[j][k].r.setFill(Color.TRANSPARENT); 
                            cell[j][k].setDisable(true);                
                        }
                    }

                    scoreBoard.scoreCount += total;
                    scoreBoard.scoreDisplay.setText(String.valueOf(scoreBoard.scoreCount));

                    try {
                        Thread.sleep(2000);   
                    } catch(InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    forceChange(true);
               
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        delayThread.setDaemon(true);
        delayThread.start();
    }       
}
