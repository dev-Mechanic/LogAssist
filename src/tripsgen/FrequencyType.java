/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tripsgen;

import java.util.Random;

/**
 *
 * @author harryp
 */
public final class FrequencyType {
    
    public static final int Weekly = 1;
    public static final int Monthly = 2;
    public static final int Yearly = 3;
    
    public static final int WeeklyMax = 7;
    public static final int MonthlyMax = 30;
    public static final int YearlyMax = 365;    
    
    
    public static int GetRandomType()
    {
        int pickType = (new Random()).nextInt(Yearly)+1;
        if(pickType == Weekly)
        {
            return Weekly;
        }
        else if(pickType == Monthly)
        {
            return Monthly;
        }
        else if(pickType == Yearly)
        {
            return Yearly;
        }
        
        //Default
        return Weekly;
    }
    
    public static int GetMaxAllowed(int fType)
    {
        if(fType == Weekly)
        {
            return WeeklyMax;
        }
        else if(fType == Monthly)
        {
            return MonthlyMax;
        }
        else if(fType == Yearly)
        {
            return YearlyMax;
        }
        
        return WeeklyMax;
    }
}
