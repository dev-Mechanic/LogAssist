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
    private double TotalDistanceKms;
    private double TotalDeductableKms;
    private double DistanceComplexityRatio;
    private double HopsComplexityRatio;
    private int DistGroupIndex;
    private int HopsGroupIndex;
    private int GroupIndex;
    
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
        
        System.out.print(DistGroupIndex + "\t" + HopsGroupIndex + "\t" + GroupIndex + "\t[ Total Distance = " + TotalDistanceKms + " : Deductable = " + TotalDeductableKms + " ]\t"+TotalDistanceKms+"\t"+DistanceComplexityRatio+"\t"+HopsComplexityRatio+"\t");
        
        for(Route dr : routesForDay)
        {
           System.out.print("\t" + dr.GetFrom());
        }
        
        System.out.println("\t" + routesForDay.get(routesForDay.size()-1).GetTo());
    }
    
    
    public ArrayList GetTripItems() {
        
        ArrayList returnSet = new ArrayList();
        
        returnSet.add(DistGroupIndex);
        returnSet.add(HopsGroupIndex);
        returnSet.add(GroupIndex);
        returnSet.add(TotalDistanceKms);
        returnSet.add(TotalDeductableKms);
        returnSet.add(DistanceComplexityRatio);
        returnSet.add(HopsComplexityRatio);
        
        for(Route dr : routesForDay)
        {
           returnSet.add(dr.GetFrom());
        }
        
        returnSet.add(routesForDay.get(routesForDay.size()-1).GetTo());
        
        return returnSet;
    }
    
    
   
    
    
    public String GetTrip() {
        
        StringBuilder response  = new StringBuilder();
        
        
        
        for(Route dr : routesForDay)
        {
           response.append(dr.GetFrom().substring(0, 7));
           response.append("...");
        }
        
        response.append(routesForDay.get(routesForDay.size()-1).GetTo().substring(0, 7));
        response.append("...");
        
        response.append("[ TD = " + Math.round(TotalDistanceKms) + " : DeD = " + Math.round(TotalDeductableKms) + " ]");
        
        
        return response.toString();
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
    
    
    public void SetDistGroupIndex(int n)
    {
        DistGroupIndex = n;
    }
    
    public void SetHopGroupIndex(int n)
    {
        HopsGroupIndex = n;
    }
    
    public void SetGroupIndex(int n)
    {
        GroupIndex = n;
    }
    
    
    public int GetDistGroupIndex()
    {
        return DistGroupIndex;
    }
    
    public int GetHopGroupIndex()
    {
        return HopsGroupIndex;
    }
    
    public int GetGroupIndex()
    {
        return GroupIndex;
    }
    
    
    void UpdateDistance() {
        TotalDistanceKms = 0.0;
        TotalDeductableKms= 0.0;
       
        for(Route r : routesForDay)
        {
            r.UpdateDistance();
            TotalDistanceKms += r.GetDistance();
            TotalDeductableKms += r.GetDeductableDistance();
        }
     
    }
    
     public double GetDistance() {
       return TotalDistanceKms;
    }
    
    public double GetDeductableDistance() {
       return  TotalDeductableKms;
    }

    public int GetHopsInRoute()
    {
        return this.routesForDay.size();
    }
    
    
    public void SetRatio(double maxDistance,int maxHops)
    {
        DistanceComplexityRatio = TotalDistanceKms/maxDistance;
//        System.out.println("CR : " + GetHopsInRoute() + " : " + maxHops);
        HopsComplexityRatio = (double)GetHopsInRoute()/(double)maxHops;
    }
    
    
    public double GetDistRatio()
    {
        return DistanceComplexityRatio;
    }
    
    
    public double GetHopRatio()
    {
        return HopsComplexityRatio;
    }
}
