/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author harryp
 */
public class LogRecord {
    
    DayRoute dayRoute;
    DateTime day;
    
    
    public LogRecord(DayRoute dr,DateTime d)
    {
        dayRoute = dr;
        day = d;
    }

//    public int GetFreqLimit() {
//        return dayRoute.GetFrequencyLimit();
//    }
//
//    public int GetFreqType() {
//        return dayRoute.GetFrequencyType();
//    }

    public DateTime GetDate() {
        return day;
    }
    
    public int GetCode() {
        if(dayRoute != null)
        {   return dayRoute.GetRouteCode().hashCode(); }
        else {
            return 0;
        }
    }

    public void print() {
        System.out.print(day.toString(DateTimeFormat.forPattern("dd-MMM-yyyy")) + "\t");
        if(dayRoute != null)
        { dayRoute.print(); }
        System.out.println();
    }
    
}
