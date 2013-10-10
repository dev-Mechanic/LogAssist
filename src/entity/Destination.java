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
        
        
        public Destination(String add,Double lat,Double lng)
        {
            address = add;
            latitude = lat;
            longitude = lng;
            
        }
        
        public Destination(String add,boolean isCl)
        {
            address = add;
            isClient = isCl;
        }
        
        
        public boolean IsClient()
        {
            return isClient;
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
