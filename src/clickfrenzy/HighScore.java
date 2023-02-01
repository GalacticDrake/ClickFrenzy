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
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

public class HighScore {
    
    // if status == true, game is running
    public static void writeUserData(boolean status) throws IOException {
        File highScFile = new File("./userdata/user.txt");
        
        int[] dataStore = new int[4];
        
        if(status == true) {
            dataStore[0] = ScoreHeader.userTime;
            dataStore[1] = ScoreHeader.userMultiplier;
            dataStore[2] = ScoreHeader.highScore;
            dataStore[3] = ScoreHeader.credits;
        } else {
            dataStore[0] = 60;
            dataStore[1] = 1;
            dataStore[2] = 0;
            dataStore[3] = 0;
        }
        
        try (
            PrintWriter highSc = new PrintWriter(highScFile);
        ) {
            highSc.println("# playertime - min 60, max 110");
            highSc.println(dataStore[0]);
            highSc.println("# playermultiplier - min 1, max 50");
            highSc.println(dataStore[1]);
            highSc.println("# highscore");
            highSc.println(dataStore[2]);
            highSc.println("# credits");
            highSc.println(dataStore[3]);
        }
    }
    
    public static void readUserData() throws IOException {
        File highScFile = new File("./userdata/user.txt");
        String strComp;
        int count = 0;
        
        try (
            Scanner input = new Scanner(highScFile)
        ) {
            while(input.hasNext()) {
                strComp = input.nextLine();
                
                if(!strComp.startsWith("#")) {
                    switch(count) {
                        case 0:
                            ScoreHeader.userTime = Integer.parseInt(strComp);
                            break;
                        case 1:
                            ScoreHeader.userMultiplier = Integer.parseInt(strComp);
                            break;
                        case 2:
                            ScoreHeader.highScore = Integer.parseInt(strComp);
                            break;
                        case 3:
                            ScoreHeader.credits = Integer.parseInt(strComp);
                            break;
                    }
                    count++;
                }
            }
        }
    }
    
}
