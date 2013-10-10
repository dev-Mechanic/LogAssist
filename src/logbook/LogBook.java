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
    double TotalDistanceKms ;
    double TotalDeductableDistanceKms;
    
    public LogBook()
    {
        logBook = new ArrayList();
        TotalDistanceKms = 0.0;
        TotalDeductableDistanceKms = 0.0;
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
            TotalDistanceKms += lr.GetDistance();
            TotalDeductableDistanceKms += lr.GetDeductableDistance();
        }
        
        System.out.println("Total Distance in Log : " + TotalDistanceKms);
        System.out.println("Total Distance Deductable in Log : " + TotalDeductableDistanceKms);
    }
    
}
