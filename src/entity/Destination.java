/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author harryp
 */
public class Destination {
        
        String address;
        Double latitude;
        Double longitude;
        
        boolean isClient;
        boolean isHome;
        boolean isWork;
        
        public Destination(String add,Double lat,Double lng)
        {
            address = add;
            latitude = lat;
            longitude = lng;
            
        }
        
        public Destination(String add,boolean isCl,boolean isHm,boolean isWk)
        {
            address = add;
            isClient = isCl;
            isHome = isHm;
            isWork = isWk;
        }
        
        
        public boolean IsClient()
        {
            return isClient;
        }
        
        public boolean IsWork()
        {
            return isWork;
        }
        
       
        
        
        public String print()
        {
            return address + "( LAT : " + latitude + " LNG : " + longitude;
        }

    
        
        public String GetAddress() 
        {
            return address;
        }
        
        
        
        
}
