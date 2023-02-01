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
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.ContentDisplay;
import javafx.geometry.Pos;

public class Upgrades extends BorderPane {    
    public static int multiLock;
    public static int addTimeLock;
    
    public static int[] storeLock = new int[5];
    
    Text headerText = new Text("UPGRADES");
    Text multText = new Text("Multiplier Upgrades");
    Text multNextData = new Text("1");
    Text addTimeText = new Text("Time Upgrades");
    Text addTimeData = new Text("60");
    Text multCost = new Text("0");
    Text addTimeCost = new Text("0");
    Text userCredits = new Text("0");
    
    Label userlblCredits = new Label("Your credits: ", userCredits);
    Label multLabText = new Label("Next multipler: ", multNextData);
    Label addTimeLabText = new Label("Next addition: ", addTimeData);
    Label multlblCost = new Label("Cost: ", multCost);
    Label addTimelblCost = new Label("Cost: ", addTimeCost);
    
    Button backBtn = new Button();
    Image backImg = new Image("resources/back.png");
    ImageView backIcon = new ImageView(backImg);
    
    Button purMultBtn = new Button("PURCHASE");
    Button purAddTimeBtn = new Button("PURCHASE");
    
    ProgressBar multUpg = new ProgressBar(0);
    ProgressBar addTimeUpg = new ProgressBar(0);
    
    Image multImg1 = new Image("resources/x1.png");
    Image multImg2 = new Image("resources/x2.png");
    Image multImg3 = new Image("resources/x5.png");
    Image multImg4 = new Image("resources/x10.png");
    Image multImg5 = new Image("resources/x20.png");
    Image multImg6 = new Image("resources/x50.png");
    
    Image addTime1 = new Image("resources/aT1.png");
    Image addTime2 = new Image("resources/aT2.png");
    Image addTime3 = new Image("resources/aT3.png");
    Image addTime4 = new Image("resources/aT4.png");
    Image addTime5 = new Image("resources/aT5.png");
    Image addTime6 = new Image("resources/aT6.png");
    
    ImageView multIcon = new ImageView(multImg1);    
    ImageView addTimeIcon = new ImageView(addTime1);
    
    HBox headerSect = new HBox(5);
    HBox creditSect = new HBox();
    HBox multStat = new HBox(5);
    HBox addTimeStat = new HBox(5);
    HBox multDesc = new HBox(5);
    HBox addTimeDesc = new HBox(5);
    
    VBox mainGroup = new VBox(20);
    VBox multGroup = new VBox(5);
    VBox addTimeGroup = new VBox(5);
    
    public Upgrades() {        
        // =======================================
            // CSS
        // =======================================
        
        backBtn.getStyleClass().add("backbtn");
        headerText.getStyleClass().add("header-text");
        headerSect.getStyleClass().add("header-container");
        
        mainGroup.getStyleClass().add("container");
        
        multGroup.getStyleClass().add("ab-container");
        addTimeGroup.getStyleClass().add("ab-container");
        
        multText.getStyleClass().add("ab-header");
        addTimeText.getStyleClass().add("ab-header");
        
        multDesc.getStyleClass().add("ab-desc");
        addTimeDesc.getStyleClass().add("ab-desc");
        
        userlblCredits.getStyleClass().add("ab-desc-lbl");
        multLabText.getStyleClass().add("ab-desc-lbl");
        addTimeLabText.getStyleClass().add("ab-desc-lbl");
        multlblCost.getStyleClass().add("ab-desc-lbl");
        addTimelblCost.getStyleClass().add("ab-desc-lbl");
        
        userCredits.getStyleClass().add("ab-desc-counter");
        multNextData.getStyleClass().add("ab-desc-counter");
        addTimeData.getStyleClass().add("ab-desc-counter");
        multCost.getStyleClass().add("ab-desc-counter");
        addTimeCost.getStyleClass().add("ab-desc-counter");
        
        multStat.getStyleClass().add("ab-stat");
        addTimeStat.getStyleClass().add("ab-stat");
        
        multUpg.getStyleClass().add("custom-progress-bar-2");
        addTimeUpg.getStyleClass().add("custom-progress-bar-2");
        
        purMultBtn.getStyleClass().add("purchase");
        purAddTimeBtn.getStyleClass().add("purchase");
        
        this.getStylesheets().add("fxcss/upgrades.css");
        this.getStylesheets().add("fxcss/headerstyles.css");
        
        // =======================================
            // Alignments
        // =======================================
        
        backBtn.setGraphic(backIcon);
        backIcon.setFitHeight(20);
        backIcon.setFitWidth(20);
        
        headerSect.getChildren().addAll(backBtn, headerText);
        headerSect.setAlignment(Pos.CENTER_LEFT);
        
        multlblCost.setContentDisplay(ContentDisplay.RIGHT);
        addTimelblCost.setContentDisplay(ContentDisplay.RIGHT);
        userlblCredits.setContentDisplay(ContentDisplay.RIGHT);
        multLabText.setContentDisplay(ContentDisplay.RIGHT);
        addTimeLabText.setContentDisplay(ContentDisplay.RIGHT);
        userCredits.setStyle("-fx-font-size: 1em;");
        userlblCredits.setStyle("-fx-font-size: 1em;");
        
        creditSect.getChildren().add(userlblCredits);
        creditSect.setAlignment(Pos.CENTER_RIGHT);
        
        multIcon.setFitHeight(40);
        multIcon.setPreserveRatio(true);
        addTimeIcon.setFitHeight(40);
        addTimeIcon.setPreserveRatio(true);
        imageSelect();
        
        multUpg.prefWidthProperty().bind(multStat.widthProperty().subtract(40));
        addTimeUpg.prefWidthProperty().bind(multStat.widthProperty().subtract(40));
        
        purMultBtn.prefWidthProperty().bind(multStat.widthProperty());
        purAddTimeBtn.prefWidthProperty().bind(multStat.widthProperty());
        
        multDesc.getChildren().addAll(multLabText, multlblCost);
        multDesc.setAlignment(Pos.CENTER);
        addTimeDesc.getChildren().addAll(addTimeLabText, addTimelblCost);
        addTimeDesc.setAlignment(Pos.CENTER);
        
        multStat.getChildren().addAll(multIcon, multUpg);
        multStat.setAlignment(Pos.CENTER_LEFT);
        multGroup.getChildren().addAll(multText, multDesc, multStat, purMultBtn);
        multGroup.setAlignment(Pos.CENTER);
        
        addTimeStat.getChildren().addAll(addTimeIcon, addTimeUpg);
        addTimeStat.setAlignment(Pos.CENTER_LEFT);
        addTimeGroup.getChildren().addAll(addTimeText, addTimeDesc, addTimeStat, purAddTimeBtn);
        addTimeGroup.setAlignment(Pos.CENTER);
        
        mainGroup.getChildren().addAll(creditSect, multGroup, addTimeGroup);
        
        this.setTop(headerSect);
        this.setCenter(mainGroup);
        
        clickAction();
        
        // disable the purchase button if maxed
        if(ScoreHeader.userMultiplier < 50) {
            purMultBtn.setDisable(true);
        }
        
        if(ScoreHeader.userTime < 110) {
            purAddTimeBtn.setDisable(true);
        }
    }
    
    // =======================================
        // Icon Selection
    // =======================================
    private void imageSelect() {
        switch(ScoreHeader.userMultiplier) {
            case 1:
                multIcon.setImage(multImg1);
                break;
            case 2:
                multIcon.setImage(multImg2);
                break;
            case 5:
                multIcon.setImage(multImg3);
                break;
            case 10:
                multIcon.setImage(multImg4);
                break;
            case 20:
                multIcon.setImage(multImg5);
                break;
            case 50:
                multIcon.setImage(multImg6);
                break;
            default:
        }
        
        switch(ScoreHeader.userTime) {
            case 60:
                addTimeIcon.setImage(addTime1);
                break;
            case 70:
                addTimeIcon.setImage(addTime2);
                break;
            case 80:
                addTimeIcon.setImage(addTime3);
                break;
            case 90:
                addTimeIcon.setImage(addTime4);
                break;
            case 100:
                addTimeIcon.setImage(addTime5);
                break;
            case 110:
                addTimeIcon.setImage(addTime6);
                break;
            default:
        }
    }
    
    // =======================================
        // Click Action
    // =======================================
    private void clickAction() {
        backBtn.setOnAction(e -> {
            MainMenu menuSpace = new MainMenu();
            DriverClass.getStage().getScene().setRoot(menuSpace);
        });
        
        // multiplier purchase button
        purMultBtn.setOnAction(e -> {
            checkMult();           
        });
        
        // add time purchase button
        purAddTimeBtn.setOnAction(e -> {  
            checkTime();
        });
    }
    
    // =======================================
        // Multiplier Purchases
    // =======================================
    private void checkMult() {
        ScoreHeader.credits = ScoreHeader.credits - multiLock;
        addMult();
        try {
            HighScore.writeUserData(true);
            LockData.readLockData();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        userCredits.setText(String.valueOf(ScoreHeader.credits));
        checkCreditsReq(); 
    }
    
    private void addMult() {
        switch(ScoreHeader.userMultiplier) {
            case 1:
                ScoreHeader.userMultiplier = 2;
                updateMult();
                break;
            case 2:
                ScoreHeader.userMultiplier = 5;
                updateMult();
                break;
            case 5:
                ScoreHeader.userMultiplier = 10;
                updateMult();
                break;
            case 10:
                ScoreHeader.userMultiplier = 20;
                updateMult();
                break;
            case 20:
                ScoreHeader.userMultiplier = 50;
                purMultBtn.setDisable(true);
                updateMult();
                break;
            default:
        }
        imageSelect();
    }
    
    public void adjMult() {
        updateMult();
    }
    
    private void updateMult() {
        switch(ScoreHeader.userMultiplier) {
            case 1:
                multUpg.setProgress(0);
                multNextData.setText("x2");
                multCost.setText(String.valueOf(storeLock[0]));
                break;
            case 2:
                multUpg.setProgress(0.2);
                multNextData.setText("x5");
                multCost.setText(String.valueOf(storeLock[1]));
                break;
            case 5:
                multUpg.setProgress(0.4);
                multNextData.setText("x10");
                multCost.setText(String.valueOf(storeLock[2]));
                break;
            case 10:
                multUpg.setProgress(0.6);
                multNextData.setText("x20");
                multCost.setText(String.valueOf(storeLock[3]));
                break;
            case 20:
                multUpg.setProgress(0.8);
                multNextData.setText("x50");
                multCost.setText(String.valueOf(storeLock[4]));
                break;
            case 50:
                multUpg.setProgress(1);
                multNextData.setText("Fully upgraded!");
                multlblCost.setText("");
                multCost.setText(""); 
                break;
            default:
                multUpg.setProgress(-1);
                multNextData.setText("");
                multlblCost.setText("Error!");
                multCost.setText("Error!"); 
        }
    }
    
    // =======================================
        // Time Purchases
    // =======================================
    private void checkTime() {
        ScoreHeader.credits = ScoreHeader.credits - addTimeLock;
        addTime();
        try {
            HighScore.writeUserData(true);
            LockData.readLockData();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        userCredits.setText(String.valueOf(ScoreHeader.credits));
        checkCreditsReq(); 
    }
    
    private void addTime() {
        switch(ScoreHeader.userTime) {
            case 60:
                ScoreHeader.userTime = 70;
                updateUserTime(); 
                break;
            case 70:
                ScoreHeader.userTime = 80;
                updateUserTime(); 
                break;
            case 80:
                ScoreHeader.userTime = 90;
                updateUserTime(); 
                break;
            case 90:
                ScoreHeader.userTime = 100;
                updateUserTime(); 
                break;
            case 100:
                ScoreHeader.userTime = 110;
                purAddTimeBtn.setDisable(true);
                updateUserTime(); 
            default:
        }
    }
    
    public void adjTime() {
        updateUserTime();
    }
    
    private void updateUserTime() {
        switch(ScoreHeader.userTime) {
            case 60:
                addTimeData.setText("70s");
                addTimeUpg.setProgress(0);
                addTimeCost.setText(String.valueOf(storeLock[0]));
                break;
            case 70:
                addTimeData.setText("80s");
                addTimeUpg.setProgress(0.2);
                addTimeCost.setText(String.valueOf(storeLock[1]));
                break;
            case 80:
                addTimeData.setText("90s");
                addTimeUpg.setProgress(0.4);
                addTimeCost.setText(String.valueOf(storeLock[2]));
                break;
            case 90:
                addTimeData.setText("100s");
                addTimeUpg.setProgress(0.6);
                addTimeCost.setText(String.valueOf(storeLock[3]));
                break;
            case 100:
                addTimeData.setText("110s");
                addTimeUpg.setProgress(0.8);
                addTimeCost.setText(String.valueOf(storeLock[4]));
                break;
            case 110:
                addTimeUpg.setProgress(1);
                addTimeData.setText("Fully upgraded!");
                addTimelblCost.setText("");
                addTimeCost.setText("");
                break;
            default:
                addTimeUpg.setProgress(-1);
                addTimeData.setText("");
                addTimelblCost.setText("Error");
                addTimeCost.setText("Error");
        }
        imageSelect();
    }
    
    // =======================================
        // Check Credits Requirements
    // =======================================
    public void checkCreditsReq() {
        // resetting credits
        userCredits.setText(String.valueOf(ScoreHeader.credits)); 
        
        if(ScoreHeader.userMultiplier < 50) {
            if(ScoreHeader.credits < multiLock) {
                purMultBtn.setDisable(true);
            } else {
                purMultBtn.setDisable(false);
            }
        }
        
        if(ScoreHeader.userTime < 110) {
            if(ScoreHeader.credits < addTimeLock) {
                purAddTimeBtn.setDisable(true);
            } else {
                purAddTimeBtn.setDisable(false);
            }
        }
    }
    
    // =======================================
        // Store Locked Values
    // =======================================
    public void storeLockValues() {
        for(int i = 0; i < 5; i++) {
            storeLock[i] = LockData.getLockValues(i);
        }
    }
}
