/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logbook;

import entity.RouteRepository;
import java.util.Random;
import limitingrules.UserLimitations;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author harryp
 */
public class LogGenerator {
    
    RouteRepository distinctRepo;
    LogBook myBook;
    DateTime startDate;
    DateTime endDate;
    UserLimitations diaryLimiter;
    
    public LogGenerator(RouteRepository rep,DateTime sd, DateTime ed,UserLimitations dl)
    {
        distinctRepo = rep;
        myBook = new LogBook();
        startDate = sd;
        endDate = ed;
        diaryLimiter = dl;
    }
    
    
    public void Allocate()
    {
        int records = Days.daysBetween(startDate, endDate).getDays();
        Random routePicker = new Random();
        DateTime temp;
        
        for(int r=0;r<records;)
        {
            temp = startDate.plusDays(r);
            
            if(diaryLimiter.IsDayAvailable(temp))
            {
            
                if(myBook.AddLogRecord(distinctRepo.GetRoute(   routePicker.nextInt(
                                                                            Integer.parseInt(distinctRepo.GetSize())
                                                                                    )
                                                            ),temp))
                {
                   r++; 
                }
            }
            else {
                myBook.AddLogRecord(null, temp);
                r++;
            }
            
        }
        
        
        myBook.print();
        
    }
    
    
    
    
}
