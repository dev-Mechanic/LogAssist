/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package limitingrules;

import entity.LogRecord;
import org.joda.time.DateTime;

/**
 *
 * @author harryp
 */
public class UserLimitations {
    
    boolean TravelOnWeekends;
    
    
    public UserLimitations(boolean tWeekends)
    {
        TravelOnWeekends = tWeekends;
    }
    
    
    public boolean IsDayAvailable(DateTime dt)
    {
        if(!TravelOnWeekends)
        {
            if(dt.dayOfWeek().get()>5)
            {
                return false;
            }
        }
        
        return true;
    }
    
    
    
}
