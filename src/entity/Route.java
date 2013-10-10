/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author harryp
 */
public class Route {
    
            Destination fromLocation;
            Destination toLocation;
            Double distance;
            
            public Route(Destination from,Destination to,double dist){
                fromLocation = from;
                toLocation = to;
                distance = dist;
            }
            
            
            public void printRoute(){
                System.out.println("\t" + fromLocation.print() + "\t" + toLocation.print() + "(" + distance + "Kms)");
            }

            public String GetFrom() {
                return fromLocation.GetAddress();
            }
            
            
            public String GetTo() {
                return toLocation.GetAddress();
            }

   


    
}
