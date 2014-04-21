/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import DataExport.ExportToExcel;
import entity.Destination;
import entity.RouteRepository;
import java.util.ArrayList;
import limitingrules.UserLimitations;
import logbook.LogGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import tripsgen.UserInput;

/**
 *
 * @author harryp
 */
public class GenTester {
    
    
    public static void main(String[] args)
    {
        
        ArrayList <Destination>WorkAddresses = new ArrayList();
        ArrayList <Destination>HomeAddresses = new ArrayList();
        
        
        Destination home = new Destination("80 Balcombe Road Mentone VIC 3194",false,true,false);
        Destination workHQ = new Destination("35 Koornang Road Scoresby VIC 3179",false,false,true);
        
        //HomeAddresses.add(new Destination("Monash University Caulfield Melbournce VIC 3145",false,true,false));
        HomeAddresses.add(new Destination("5 Warley Road Malvern East VIC 3145",false,true,false));
        
        WorkAddresses.add(new Destination("800 Stud Road Scoresby VIC 3179",false,false,true));
        WorkAddresses.add(new Destination("9 Helen Kob Drive Braeside VIC 3195",false,false,true));
        
        WorkAddresses.add(new Destination("47 Robinson St,Dandenong VIC 3175",true,false,false));
        
        
        
      
        
        
        UserInput ui = new UserInput(home,workHQ,WorkAddresses,HomeAddresses,true);
        
        RouteRepository repo = ui.DistinctRoutes(50);
        
        repo.UpdateRouteDistances();
        //repo.print();
        System.out.println("Net Distinct Routes : " + repo.GetSize());
        
        repo = ui.ProfileRouteRepository(repo, 3);
        
        
        ArrayList<Double> loadingList = new ArrayList();
        loadingList.add(50.00);
        loadingList.add(80.00);
        loadingList.add(100.00);
        
        repo.SetGroupLoading(loadingList);
//        
//        repo.AllocateRandomFrequencies();
//        
        DateTimeFormatter dstrFmt = DateTimeFormat.forPattern("dd-MMM-yyyy");
        DateTime startOn = dstrFmt.parseDateTime("1-Aug-2013");
        DateTime endOn = dstrFmt.parseDateTime("31-Oct-2013");
//        
        UserLimitations ul = new UserLimitations(false);
//        
        LogGenerator lg = new LogGenerator(repo, startOn,endOn,ul,"DIST");
        lg.Allocate();
//        
        
        ExportToExcel exporter = new ExportToExcel("TestExport");
        exporter.ExportRoutes("RouteList", repo.GetRouteList());
        exporter.ExportLogBook("LogBook", lg.GetRecords(),10000,15000);
        exporter.CommitWorkBook();
        
        
        for(Double d : loadingList)
        {
            System.out.println(" Item at : " + loadingList.indexOf(d) + " - " + d);
        }
        
        int countIncrement = 0 ;
        ArrayList<Double> itr = loadingList;
        boolean upd = false;
        int setIncr = -10;
        for(int i=0;i<10;i++)
        {
            upd = false;
            for(Double d : itr)
            {
                if(d < 100 && upd == false && d+setIncr > 0)
                {
                    itr.set(itr.indexOf(d), d + setIncr);
                    upd = true;
                    countIncrement = itr.indexOf(d);
                }
                
                if( d == 0 && upd == false)
                {
                    setIncr = 10;
                }
                
            }

            
            
            
            
            
            
            
            for(Double d : itr)
            {
                System.out.println(" Item at : " + loadingList.indexOf(d) + " - " + d);
            }   
            
        }
        
        
        
        
        
    }
    
    
   
}
