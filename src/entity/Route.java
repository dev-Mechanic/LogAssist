/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import mapquestservice.MapQuestService;

/**
 *
 * @author harryp
 */
public class Route {
    
            Destination fromLocation;
            Destination toLocation;
            Double distanceKMs;
            Double deductableKMs;
            
            public Route(Destination from,Destination to,double dist){
                fromLocation = from;
                toLocation = to;
                distanceKMs = dist;
            }
            
            
            public void printRoute(){
                System.out.println("\t" + fromLocation.print() + "\t" + toLocation.print() + "(" + distanceKMs + "Kms)");
            }

            public String GetFrom() {
                return fromLocation.GetAddress();
            }
            
            
            public String GetTo() {
                return toLocation.GetAddress();
            }

    void UpdateDistance() {
        distanceKMs = MapQuestService.GetDistance(fromLocation.GetAddress(), toLocation.GetAddress());
        
        if(fromLocation.IsClient() || toLocation.IsClient())
        {
            deductableKMs = distanceKMs;
        }
        
        if( fromLocation.IsWork() && toLocation.IsWork())
        {
            deductableKMs = distanceKMs;
        }
        else
        {
            deductableKMs = 0.0;
        }
        
        
    }

    public double GetDistance() {
       return distanceKMs;
    }
    
    public double GetDeductableDistance() {
       return deductableKMs;
    }

   


    
}
