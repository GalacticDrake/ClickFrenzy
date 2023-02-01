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
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LockData {
    
    private static int[] tempLock = new int[5];
    
    public static void readLockData() throws FileNotFoundException {
        File srcFile = new File("./userdata/lock.txt");
        String strComp;
        int count = 0;
        
        try (
            Scanner input = new Scanner(srcFile)
        ) {
            while(input.hasNext()) {
                strComp = input.nextLine();
                
                if(strComp.startsWith("#")) {
                    continue;
                }
                
                if(count < 5) {
                    tempLock[count] = Integer.parseInt(strComp);
                    count++;
                }         
            }
            
            checkMultLock();
            checkAddTimeLock();
        }
    }
    
    private static void checkMultLock() {
        switch(ScoreHeader.userMultiplier) {
            case 1:
                Upgrades.multiLock = tempLock[0];
                break;
            case 2:
                Upgrades.multiLock = tempLock[1];
                break;
            case 5:
                Upgrades.multiLock = tempLock[2];
                break;
            case 10:
                Upgrades.multiLock = tempLock[3];
                break;
            case 20:
                Upgrades.multiLock = tempLock[4];
                break;
            default:
        }
    }
    
    private static void checkAddTimeLock() {
        switch(ScoreHeader.userTime) {
            case 60:
                Upgrades.addTimeLock = tempLock[0];
                break;
            case 70:
                Upgrades.addTimeLock = tempLock[1];
                break;
            case 80:
                Upgrades.addTimeLock = tempLock[2];
                break;
            case 90:
                Upgrades.addTimeLock = tempLock[3];
                break;
            case 100:
                Upgrades.addTimeLock = tempLock[4];
                break;
            default:
        }
    }
    
    public static int getLockValues(int i) {
        return tempLock[i];
    }
    
}
