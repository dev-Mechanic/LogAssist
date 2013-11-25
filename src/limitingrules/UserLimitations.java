/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package limitingrules;

import entity.LogRecord;
import java.util.ArrayList;
import org.joda.time.DateTime;

/**
 *
 * @author harryp
 */
public class UserLimitations {
    
    boolean TravelOnWeekends;
    ArrayList <LogRecord>LogRecords;
    ArrayList <DateTime> DateCache;
    
    public UserLimitations(boolean tWeekends)
    {
        TravelOnWeekends = tWeekends;
        LogRecords = new ArrayList();
        DateCache = new ArrayList();
        
    }
    
    public void AddRecord(LogRecord lr)
    {
        LogRecords.add(lr);
        DateCache.add(lr.GetDate());
        
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
        
        if(DateCache.indexOf(dt)>=0)
        {
            return false;
        }
        
        return true;
    }
    
    public ArrayList <LogRecord> GetPreFil()
    {
        return LogRecords;
    }
    
    public boolean IsAllocated(DateTime dt)
    {
        if(DateCache.indexOf(dt)>=0)
        {
            return true;
        }
        
        return false;
    }
    
    public LogRecord GetLogForDate(DateTime dt)
    {
        if(DateCache.indexOf(dt)>=0)
        {
            return LogRecords.get(DateCache.indexOf(dt));
        }
        return null;
    }
    
    
}
