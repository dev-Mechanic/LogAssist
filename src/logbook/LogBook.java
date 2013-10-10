/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logbook;

import entity.DayRoute;
import entity.LogRecord;
import java.util.ArrayList;
import org.joda.time.DateTime;
import tripsgen.FrequencyType;

/**
 *
 * @author harryp
 */
public class LogBook {
    ArrayList<LogRecord> logBook;
    
    
    public LogBook()
    {
        logBook = new ArrayList();
    }
    
    public boolean AddLogRecord(DayRoute dr , DateTime dt)
    {
        if(IsValid(dr,dt))
        {
            logBook.add(new LogRecord(dr,dt));
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean IsValid(DayRoute dr,DateTime dt) {
        if(dr != null)
        {
            int ftype = dr.GetFrequencyType();
            int flimit = dr.GetFrequencyLimit();

            int DateWindow = FrequencyType.GetMaxAllowed(ftype);

            DateTime endWindow = dt;
            DateTime startWindow = endWindow.minusDays(DateWindow);

            int countOfLogRecord = 0;

            for(LogRecord l : logBook)
            {
                
                    if(l.GetDate().isAfter(startWindow) && l.GetDate().isBefore(endWindow) && l.GetCode() == dr.GetRouteCode().hashCode())
                    {
                        countOfLogRecord++;
                    }
                
            }

            if(countOfLogRecord < flimit) {
                return true;
            }
            else {
                return false;
            }
        }
        else
        {
            
            return true;
        }
    }
    
    public void print()
    {
        for(LogRecord lr : logBook)
        {
            lr.print();
        }
    }
    
}
