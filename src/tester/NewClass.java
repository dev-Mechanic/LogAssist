/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.util.ArrayList;
import logbook.LogGenerator;

/**
 *
 * @author himanshupahuja
 */
public class NewClass {
    
    
    public static void main(String [] args)
    {
        int countIncrement = 0 ;
        boolean upd = false;
        int setIncr = -10;
        double maxP=0.0;
        
        int percentClaim = 65;
        
        ArrayList<Double> loadingList = new ArrayList();
            
        if(percentClaim>0)
        {
            loadingList.add(50.0);
            loadingList.add(80.0);
            loadingList.add(100.0);
        }
        
        ArrayList<Double> itr = loadingList;
        boolean loopBrk = false;
        ArrayList<LogGenerator> iterateThrough = new ArrayList<LogGenerator>();
        int maxIndex = 0;
        
        for(int i=0;i<20;i++)
        {
            
            if(itr.get(countIncrement)>=10 && countIncrement < itr.size()-1)
            {

                itr.set(countIncrement, itr.get(countIncrement)-10);
            }   
            else
            {
                if(countIncrement < itr.size()-1)
                {
                    countIncrement++;
                }
                //itr.set(countIncrement, itr.get(countIncrement)-10);
            }
            
            for(Double d : loadingList)
            {
                System.out.print(" -"+ d);
            }
            System.out.println("");
            
        }
    }
}
