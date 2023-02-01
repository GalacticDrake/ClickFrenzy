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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HowToPlay extends BorderPane {
    
    ScrollPane scrollContainer = new ScrollPane();
    
    StackPane groupAll = new StackPane();
    
    VBox headerGroup = new VBox(5);
    
    Button backBtn = new Button();
    Image backImg = new Image("resources/back.png");
    ImageView backIcon = new ImageView(backImg);
    
    Text headerText = new Text("GAME TUTORIAL");
    HBox headerSect = new HBox(5);
    
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    
    public HowToPlay() {
        // =======================================
            // CSS
        // =======================================
        
        backBtn.getStyleClass().add("backbtn");
        headerText.getStyleClass().add("header-text");
        headerSect.getStyleClass().add("header-container");
        
        this.getStylesheets().add("fxcss/headerstyles.css");
        
        // =======================================
            // Alignments
        // =======================================
        
        backBtn.setGraphic(backIcon);
        backIcon.setFitHeight(20);
        backIcon.setFitWidth(20);
        
        headerSect.getChildren().addAll(backBtn, headerText);
        headerSect.setAlignment(Pos.CENTER_LEFT);
        this.setTop(headerSect);
        this.setCenter(webView);
        
        webEngine.load(getClass().getClassLoader().getResource("html/index.html").toExternalForm()); 
        webEngine.setUserStyleSheetLocation(getClass().getClassLoader().getResource("html/main.css").toString());
        webEngine.setJavaScriptEnabled(true);
        clickOptions();
    }
    
    private void clickOptions() {
        backBtn.setOnAction(e -> {
            MainMenu menuSpace = new MainMenu();
            DriverClass.getStage().getScene().setRoot(menuSpace);
        });
    }    
}
