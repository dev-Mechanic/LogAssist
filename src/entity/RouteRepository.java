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
public class RouteRepository {
    
    ArrayList<DayRoute> dayRouteList;
    ArrayList<Integer> routeListCache;
    private double TotalDistanceKms;
    private double TotalDeductableKms;
    private double MaxTotalDistanceKms;
    private int MaxTotalHops;
    
    private ArrayList<Double> GroupLoading;
    
    
    public RouteRepository()
    {
        dayRouteList = new ArrayList();
        routeListCache = new ArrayList();
        GroupLoading = new ArrayList();
    }
    
    
    public void AddRoute(DayRoute dr)
    {
        dayRouteList.add(dr);
        routeListCache.add(dr.GetRouteCode().hashCode());
    }
    
    public ArrayList<DayRoute> GetRouteList()
    {
        return dayRouteList;
    }
    
    public boolean IsRoutePresent(DayRoute dr)
    {
        if(routeListCache.contains(dr.GetRouteCode().hashCode()))
        {
            return true;
        }
        
        return false;
    }
    
    public void SetGroupLoading(ArrayList loading)
    {
        GroupLoading = loading;
    }
    
    public void print()
    {
        for(DayRoute dr : dayRouteList)
        {
            dr.print();
            System.out.println();
        }
        
       
        
    }

    public int GetSize() {
        return dayRouteList.size();
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
    
    
    public DayRoute GetProbableRoute(String mode)
    {
        double groupSel = Math.random()*100;
        int grpIndex = 0,leftVal=0;
        ArrayList<DayRoute> groupSet = new ArrayList();
        Random routePicker = new Random();
        System.out.println(" Random Sel : " + groupSel);
        for(Double groupFactor : GroupLoading)
        {
            if(groupSel <= groupFactor)
            {
                // Select this group
                for(DayRoute dr : dayRouteList)
                {
                    if(mode.equals("DIST"))
                    {
                        leftVal = dr.GetDistGroupIndex();
                    }
                    else if(mode.equals("HOPS"))
                    {
                        leftVal = dr.GetHopGroupIndex();
                    }
                    else if(mode.equals("DISTHOPS"))
                    {
                        leftVal = dr.GetGroupIndex();
                    }
                    
                    if(leftVal == grpIndex)
                    {
                        groupSet.add(dr);
                    }
                }
                
                return groupSet.get(routePicker.nextInt(groupSet.size()));                
            }
            grpIndex ++;
        }
        
        return null;
    }
    
    
    public void UpdateRouteDistances()
    {   
        TotalDistanceKms = 0.0;
        TotalDeductableKms= 0.0;
        
        MaxTotalDistanceKms = dayRouteList.get(0).GetDistance();
        MaxTotalHops = dayRouteList.get(0).GetHopsInRoute();
        
        
        
        for(DayRoute dr : dayRouteList)
        {
            dr.UpdateDistance();
            
            if(dr.GetDistance()>MaxTotalDistanceKms)
            {
                MaxTotalDistanceKms = dr.GetDistance();
            }
            
            if(dr.GetHopsInRoute()>MaxTotalHops)
            {
                MaxTotalHops = dr.GetHopsInRoute();
            }
            
            
            
            TotalDistanceKms += dr.GetDistance();
            TotalDeductableKms += dr.GetDeductableDistance();
        }
        
        
        for(DayRoute dr : dayRouteList)
        {
            dr.SetRatio(MaxTotalDistanceKms, MaxTotalHops);
        }
    }

    public Iterable<DayRoute> GetRepo() {
        return dayRouteList;
    }
    
    
    
    
    
}
