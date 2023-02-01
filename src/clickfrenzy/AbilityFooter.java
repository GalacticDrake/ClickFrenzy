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
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.text.Text;
import javafx.scene.control.ProgressBar;

public class AbilityFooter extends GridPane {
    static Text ability = new Text(0, 0, "None"); // name of ability
    static Text abilityDesc = new Text(0, 0, "-"); // ability description
    static ProgressBar progress = new ProgressBar(); // ability timer
       
    AbilityFooter() {
        // =======================================
            // CSS
        // =======================================
        
        this.getStyleClass().add("container");
        
        ability.getStyleClass().add("ab-title");
        abilityDesc.getStyleClass().add("ab-desc");
        
        progress.getStyleClass().add("custom-progress-bar");
        progress.setStyle("-fx-accent: rgb(44, 255, 213);");
        
        this.getStylesheets().add("fxcss/footer.css");
        this.getStylesheets().add("fxcss/headerstyles.css");
        
        // =======================================
            // Alignments
        // =======================================
        
        
        progress.prefWidthProperty().bind(this.widthProperty());
        
        this.add(progress, 0, 0);
        this.add(ability, 0, 1);
        this.add(abilityDesc, 0, 2);
        
        this.setHalignment(progress, HPos.CENTER);
        this.setHalignment(ability, HPos.CENTER);
        this.setHalignment(abilityDesc, HPos.CENTER);
        this.setAlignment(Pos.CENTER);
    }
    
    // =======================================
        // Update Ability
    // =======================================
    public static void changeAbility(int mode) {
        Platform.runLater(new Runnable() {
            String string;
            String desc;
            
            // if mode == 1, x2 multiplier enabled
            // if mode == 2, touch of midas enabled
            @Override 
            public void run() {
                switch(mode) {
                    case 0:
                        string = "None";
                        desc = "-"; 
                        break;
                    case 1:
                        string = "x2 Multiplier!";
                        desc = "Your score has been doubled!";
                        break;
                    case 2:
                        string = "Touch of Midas";
                        desc = "Bless your gold blocks with double scoring!";
                        break;
                    default:                        
                }
                
                ability.setText(string);
                abilityDesc.setText(desc);
            }
        });
    }
}
