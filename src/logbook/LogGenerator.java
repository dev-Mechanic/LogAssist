/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logbook;

import entity.LogRecord;
import entity.RouteRepository;
import java.util.ArrayList;
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
    String ProbableMode;
    
    
    public LogGenerator(RouteRepository rep,DateTime sd, DateTime ed,UserLimitations dl,String pmode)
    {
        distinctRepo = rep;
        myBook = new LogBook();
        startDate = sd;
        endDate = ed;
        diaryLimiter = dl;
        ProbableMode = pmode;
    }
    
    
    public void Allocate()
    {
        int records = Days.daysBetween(startDate, endDate).getDays();
        Random routePicker = new Random();
        DateTime temp;
        
        //myBook.PreFil(diaryLimiter.GetPreFil());
        
        for(int r=0;r<records;)
        {
            temp = startDate.plusDays(r);
            
            
            if(diaryLimiter.IsDayAvailable(temp))
            {
                
                //Frequency Based Allocation
            
//                if(myBook.AddLogRecord(distinctRepo.GetRoute(   routePicker.nextInt(
//                                                                            distinctRepo.GetSize()
//                                                                                    )
//                                                            ),temp))
//                {
//                   r++; 
//                }
                
                
                // Probabilistic Based Allocation
                if(myBook.AddLogRecord(distinctRepo.GetProbableRoute(ProbableMode),temp,true))
                {
                   r++; 
                }
                
                
                
            }
            else {
                if(diaryLimiter.IsAllocated(temp))
                {
                    
                   myBook.AddLogRecord(diaryLimiter.GetLogForDate(temp)); 
                }
                else
                {
                    myBook.AddLogRecord(null, temp);
                }
                r++;
            }
            
        }
        
        
        myBook.print();
        
    }
    
    
    public ArrayList<LogRecord> GetRecords()
    {
        return myBook.GetBook();
    }
    
    
    
}
