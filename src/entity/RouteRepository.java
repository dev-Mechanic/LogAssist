/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import tripsgen.FrequencyType;

/**
 *
 * @author harryp
 */
public class RouteRepository {
    
    ArrayList<DayRoute> dayRouteList;
    ArrayList<Integer> routeListCache;
    private double TotalDistanceKms;
    private double TotalDeductableKms;
    
    public RouteRepository()
    {
        dayRouteList = new ArrayList();
        routeListCache = new ArrayList();
    }
    
    public void AddRoute(DayRoute dr)
    {
        dayRouteList.add(dr);
        routeListCache.add(dr.GetRouteCode().hashCode());
    }
    
    public boolean IsRoutePresent(DayRoute dr)
    {
        if(routeListCache.contains(dr.GetRouteCode().hashCode()))
        {
            return true;
        }
        
        return false;
    }
    
    
    public void print()
    {
        for(DayRoute dr : dayRouteList)
        {
            dr.print();
            System.out.println();
        }
        
       
        
    }

    public String GetSize() {
        return String.valueOf(dayRouteList.size());
    }
    
    
    public void AllocateRandomFrequencies()
    {
        for(DayRoute dr : dayRouteList)
        {
            dr.setFrequencyType(FrequencyType.GetRandomType());
        }
    }

    public DayRoute GetRoute(int ix) {
        return dayRouteList.get(ix);
    }
    
    
    public void UpdateRouteDistances()
    {   
        TotalDistanceKms = 0.0;
        TotalDeductableKms= 0.0;
        
        for(DayRoute dr : dayRouteList)
        {
            dr.UpdateDistance();
            TotalDistanceKms += dr.GetDistance();
            TotalDeductableKms += dr.GetDeductableDistance();
        }
    }
    
}
