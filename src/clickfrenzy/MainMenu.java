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
import static clickfrenzy.ClickFrenzy.scoreBoard;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  
import javafx.util.Duration;

public class MainMenu extends BorderPane {   
    Button btPlay = new Button("PLAY");
    Button btHTP = new Button("HOW TO PLAY");
    Button btUpg = new Button("UPGRADES");
    Button btOpt = new Button("OPTIONS");
    Button btQuit = new Button("QUIT");
    
    Text title = new Text(0, 0, "ClickFrenzy");
    Text footerText = new Text(0, 0, "Game Design by CST1904695");
    
    VBox menuBtn = new VBox(10);
    HBox bottomBtn = new HBox(10);
    
    Media bgm = new Media(getClass().getClassLoader().getResource("music/smoky.mp3").toExternalForm());
    MediaPlayer mediaPlayer = new MediaPlayer(bgm);
    
    // made static to prevent changing of windows by creating another object
    static ClickFrenzy gameSpace = new ClickFrenzy();
    static HowToPlay HTPSpace = new HowToPlay();
    static Options optSpace = new Options();
    static Upgrades upgradeSpace = new Upgrades();
    
    public MainMenu() {
        // =======================================
            // CSS
        // =======================================
        this.getStyleClass().add("container");
        
        title.getStyleClass().add("title");
        
        btPlay.getStyleClass().add("mainbutton");
        btHTP.getStyleClass().add("mainbutton");
        btUpg.getStyleClass().add("mainbutton");
        btOpt.getStyleClass().add("mainbutton");
        btQuit.getStyleClass().add("mainbutton");
        
        this.getStylesheets().add("fxcss/mainmenu.css");
        
        // =======================================
            // Alignments
        // =======================================
        this.setTop(title);    
        this.setAlignment(title, Pos.CENTER);
        
        // styling game developer's name
        footerText.setStyle("-fx-font-size: 1em; font-weight: bold; -fx-fill: white;");
        this.setBottom(footerText);    
        this.setAlignment(footerText, Pos.CENTER);
        
        menuBtn.getChildren().add(btPlay);
        menuBtn.getChildren().add(btHTP);
        menuBtn.getChildren().add(btUpg);
        
        bottomBtn.getChildren().addAll(btOpt, btQuit);
        bottomBtn.setAlignment(Pos.CENTER);
        menuBtn.getChildren().add(bottomBtn);
        menuClick();
        
        menuBtn.setAlignment(Pos.CENTER);
        
        this.setCenter(menuBtn);
        this.setMargin(title, new Insets(100, 0, 0, 0));
        this.setPadding(new Insets(0, 150, 10, 150));
        
        btPlay.prefWidthProperty().bind(menuBtn.widthProperty());
        btHTP.prefWidthProperty().bind(menuBtn.widthProperty());
        btUpg.prefWidthProperty().bind(menuBtn.widthProperty());
        btOpt.prefWidthProperty().bind(bottomBtn.widthProperty().divide(2));
        btQuit.prefWidthProperty().bind(bottomBtn.widthProperty().divide(2));
        
        // read data on initialisation
        try {
            HighScore.readUserData();
            LockData.readLockData();
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        // update credits
        upgradeSpace.storeLockValues();
        upgradeSpace.userCredits.setText(String.valueOf(scoreBoard.credits));
        
        // play music        
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
              mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play(); 
    }
    
    // =======================================
        // Click Options
    // =======================================
    public void menuClick() {
        // play button
        btPlay.setOnAction(e -> {
            mediaPlayer.stop(); 
            gameSpace.startController();
            DriverClass.getStage().getScene().setRoot(gameSpace);
        });
        
        // how to play button
        btHTP.setOnAction(e -> {
            mediaPlayer.stop();
            DriverClass.getStage().getScene().setRoot(HTPSpace);
        });
        
        // upgrade button
        btUpg.setOnAction(e -> {
            mediaPlayer.stop();
            upgradeSpace.checkCreditsReq(); // check credits requirements       
            upgradeSpace.adjMult(); // adjust multiplier data
            upgradeSpace.adjTime(); // adjust time data
            DriverClass.getStage().getScene().setRoot(upgradeSpace);
        });
        
        // option button
        btOpt.setOnAction(e -> {
            mediaPlayer.stop();
            optSpace.resetInfo.setText("This will reset your progress.");
            DriverClass.getStage().getScene().setRoot(optSpace);
        });
        
        // quit button
        btQuit.setOnAction(e -> {
            mediaPlayer.stop(); 
            System.exit(0);
        }); 
    }

    
}
