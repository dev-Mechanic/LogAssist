/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.Random;
import tripsgen.FrequencyType;
/**
 *
 * @author harryp
 */
public class DayRoute {
    
    ArrayList <Route> routesForDay;
    private int FreqType;
    private int FreqLimit;
    
    public DayRoute()
    {
        routesForDay = new ArrayList();
    }
    
    public void AddRoute(Route rt)
    {
        routesForDay.add(rt);
    }
    
    
    public String GetRouteCode()
    {
        StringBuilder result= new StringBuilder();
        
        for(Route rd : routesForDay)
        {
            result.append(rd.GetFrom());
        }
        
        result.append(routesForDay.get(routesForDay.size()-1).GetTo());
        
        return result.toString();
    }

    public void print() {
        for(Route dr : routesForDay)
        {
           System.out.print("\t" + dr.GetFrom());
        }
        
        System.out.println("\t" + routesForDay.get(routesForDay.size()-1).GetTo());
    }
    
    public void setFrequencyType(int fType)
    {
       if(fType == FrequencyType.Weekly)
       {
           FreqType = fType;
           FreqLimit = (new Random()).nextInt(FrequencyType.WeeklyMax)+1;
       }
       else if(fType == FrequencyType.Monthly)
       {
           FreqType = fType;
           FreqLimit = (new Random()).nextInt(FrequencyType.MonthlyMax)+1;
       }
       else if(fType == FrequencyType.Yearly)
       {
           FreqType = fType;
           FreqLimit = (new Random()).nextInt(FrequencyType.YearlyMax)+1;
       }
    }

    public int GetFrequencyLimit() {
        return FreqLimit;
    }
    
    public int GetFrequencyType() {
        return FreqType;
    }

    
    
    
}