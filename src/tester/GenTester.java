/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import entity.Destination;
import entity.RouteRepository;
import java.util.ArrayList;
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
        
        
//        Destination home = new Destination("80 Balcombe Road Mentone VIC 3194",false);
//        Destination workHQ = new Destination("35 Koornang Road Scoresby VIC 3179",false);
//        
//        HomeAddresses.add(new Destination("Monash University Caulfield VIC 3145",false));
//        HomeAddresses.add(new Destination("5 Warley Road Malvern East VIC 3145",false));
//        
//        WorkAddresses.add(new Destination("800 Stud Road Scoresby VIC 3179",false));
//        WorkAddresses.add(new Destination("9 Helen Kob Drive Braeside VIC 3195",false));
//        
//        WorkAddresses.add(new Destination("47 Robinson St,Dandenong VIC 3175",true));
        
        
        
        Destination home = new Destination("80",false);
        Destination workHQ = new Destination("35",false);
        
        //HomeAddresses.add(new Destination("Monash",false));
        //HomeAddresses.add(new Destination("5",false));
        
        WorkAddresses.add(new Destination("800",false));
        WorkAddresses.add(new Destination("9",false));
        WorkAddresses.add(new Destination("47",true));
        
        
        UserInput ui = new UserInput(home,workHQ,WorkAddresses,HomeAddresses,true);
        
        RouteRepository repo = ui.DistinctRoutes(50);
        repo.print();
        
        System.out.println("Net Distinct Routes : " + repo.GetSize());
        
        repo.AllocateRandomFrequencies();
        
        DateTimeFormatter dstrFmt = DateTimeFormat.forPattern("dd-MMM-yyyy");
        DateTime startOn = dstrFmt.parseDateTime("1-Oct-2013");
        DateTime endOn = dstrFmt.parseDateTime("31-Oct-2013");
        
        LogGenerator lg = new LogGenerator(repo, startOn,endOn);
        lg.Allocate();
        
        
        
        
    }
}
