/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logbook;

import entity.RouteRepository;
import java.util.Random;
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
    
    
    public LogGenerator(RouteRepository rep,DateTime sd, DateTime ed)
    {
        distinctRepo = rep;
        myBook = new LogBook();
        startDate = sd;
        endDate = ed;
    }
    
    
    public void Allocate()
    {
        int records = Days.daysBetween(startDate, endDate).getDays();
        Random routePicker = new Random();
        
        for(int r=0;r<records;)
        {
            if(myBook.AddLogRecord(distinctRepo.GetRoute(   routePicker.nextInt(
                                                                        Integer.parseInt(distinctRepo.GetSize())
                                                                                )
                                                        ),startDate.plusDays(r)))
            {
               r++; 
            }
        }
        
        
        myBook.print();
        
    }
    
    
    
    
}
