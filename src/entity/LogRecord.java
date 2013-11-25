/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author harryp
 */
public class LogRecord {
    
    DayRoute dayRoute;
    DateTime day;
    double personalTravel;
    
    public LogRecord(DayRoute dr,DateTime d)
    {
        dayRoute = dr;
        day = d;
        personalTravel = 0.0;
    }
    
    public void SetPersonalTravel(double d)
    {
        personalTravel = d;
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
    
    
    public ArrayList GetLogItems() {
        
        ArrayList retList;
        
        if(dayRoute != null)
        { retList = dayRoute.GetTripItems(); }
        else
        { retList = new ArrayList();        }
        
        retList.add(0,personalTravel);
        
        return retList;
    }
    
    
    
    
    public String GetTrip()
    {
        if(dayRoute != null)
        {   return dayRoute.GetTrip(); }
        else
        {   return ""; }
    }
    
    public double GetDistance(){
        if(dayRoute != null)
        {   return dayRoute.GetDistance(); }
        else
        {   return 0.0; }
    }
    
    public double GetDeductableDistance(){
        if(dayRoute != null)
        {   return dayRoute.GetDeductableDistance(); }
        else
        {   return 0.0; }
    }

    public double GetPersonalTravel() {
        return personalTravel;
    }

    
}
