/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logbook;

import entity.DayRoute;
import entity.Destination;
import entity.LogRecord;
import entity.RouteRepository;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.table.DefaultTableModel;
import limitingrules.UserLimitations;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import tripsgen.UserInput;

/**
 *
 * @author Himanshu
 */
public class LogBookCore {
    
    private static UserInput ui;
    private static RouteRepository repo;
    private static LogGenerator lg;
    
    /**
   * This method is used to add two integers. This is
   * a the simplest form of a class method, just to
   * show the usage of various javadoc Tags.
   * @param ha Home Address String
   * @param hal Other Home Address List ArrayList<String>
   * @param wa Work Address String
   * @param wal Other Work Address List ArrayList<String>
   * @param cal Client Address List ArrayList<String>
   * @param StartAtAny Start At Any Bool
   * @param WorkOnWeekends  WorkOnWeekends Bool
   * @param startDate Start Date String
   * @param endDate  End Date String
   * @param startODO startODO int
   * @param endODO  endODO int
   * @param percentClaim percentClaim int
   * @param groupCount  Cluster Count int
   * @param complexMode Group Mode : DIST|HOPS|DISTHOPS String
   * @return LogGenerator This returns sum of numA and numB.
   */
    public static ArrayList<LogRecord> GetLogBook( String ha,ArrayList<String> hal,
                                   String wa,ArrayList<String> wal,
                                   ArrayList<String> cal, 
                                   boolean StartAtAny,
                                   boolean WorkOnWeekends,
                                   String startDate, String endDate,
                                   int startODO,int endODO,
                                   float percentClaim,
                                   int groupCount,
                                   String complexMode)
    {
        
        ArrayList <Destination>WorkAddresses = new ArrayList();
        ArrayList <Destination>HomeAddresses = new ArrayList();
        
        
        Destination home = new Destination(ha,false,true,false);
        Destination workHQ = new Destination(wa,false,false,true);
        
        
        for(String h : hal)
        {
            System.out.println(" H : " + h);
            HomeAddresses.add(new Destination(h,false,true,false));
        }
        
        
        for(String s : wal)
        {
            System.out.println(" W : " + s);
            WorkAddresses.add(new Destination(s,false,false,true));
        }
        
        for(String c : cal)
        {
            System.out.println(" C : " + c);
            WorkAddresses.add(new Destination(c,true,false,false));
        } 
        
        ui = new UserInput(home,workHQ,WorkAddresses,HomeAddresses,StartAtAny);
        
        repo = ui.DistinctRoutes(50);
        
        repo.UpdateRouteDistances();
        repo.print();
        System.out.println("Net Distinct Routes : " + repo.GetSize());
        
        
        //repo.AllocateRandomFrequencies();
        repo = ui.ProfileRouteRepository(repo, groupCount);
        
        
        
        
        /////////////////////////////////////////////////////////////////////////////
        
        UserLimitations restr = new UserLimitations(WorkOnWeekends);
        DateTimeFormatter dstrFmt = DateTimeFormat.forPattern("dd-MMM-yyyy");
        DateTime tempDate;
        DayRoute dr;
        int routeRef;
        
        
        
        
        
        
//        for(int i=0;i<this.jTable2.getModel().getRowCount();i++)
//        {
//            tempDate = dstrFmt.parseDateTime(String.valueOf(this.jTable2.getModel().getValueAt(i, 0)));
//            routeRef = Integer.parseInt(this.jTable2.getModel().getValueAt(i,1).toString()); 
//            dr = repo.GetRoute(routeRef-1);
//            LogRecord lr = new LogRecord(dr,tempDate);
//            restr.AddRecord(lr);
//        }
        
        
        DateTime startOn = dstrFmt.parseDateTime(startDate);
        DateTime endOn = dstrFmt.parseDateTime(endDate);
        
        
        int countIncrement = 0 ;
        boolean upd = false;
        int setIncr = -10;
        double maxP=0.0;
        
        ArrayList<Double> loadingList = new ArrayList();
            
        if(percentClaim>0)
        {
            loadingList.add(50.0);
            loadingList.add(80.0);
            loadingList.add(100.0);
            
            
        }
//        else
//        {
//            
//            double RegionValue = 0.0;
//            for(int i=0;i<groupCount;i++)
//            {
//                RegionValue += Double.parseDouble(this.jTable4.getModel().getValueAt(i, 1).toString());
//                loadingList.add(RegionValue);
//            }
//        }
        
        ArrayList<Double> itr = loadingList;
        boolean loopBrk = false;
        ArrayList<LogGenerator> iterateThrough = new ArrayList<LogGenerator>();
        int maxIndex = 0;
        
        for(int i=0;i<40;i++)
        {
            if(!loopBrk)
            {
//            upd = false;
//            for(Double d : itr)
//            {
//                if(d < 100 && upd == false && d+setIncr >= 0 && itr.indexOf(d) >= countIncrement)
//                {
//                    itr.set(itr.indexOf(d), d + setIncr);
//                    upd = true;
//                    countIncrement = itr.indexOf(d);
//                }
//                
//                if( d == 0 && upd == false)
//                {
//                    setIncr = 10;
//                    countIncrement++;
//                }
//                
//            }
        
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
            
//        print(itr);
        repo.SetGroupLoading(itr);
        
        
        
        lg = new LogGenerator(repo, startOn,endOn,restr,complexMode);
        lg.Allocate();
        
        ArrayList<LogRecord> result = lg.GetRecords();
        
        int totalRecords = result.size();
        double TotalDeductable = 0.0,TotalDistance = 0.0;
        
        
        for(LogRecord l : result)
        {
            TotalDistance += Math.round(l.GetDistance());
            TotalDeductable += Math.round(l.GetDeductableDistance());
        }
        
        
         
         System.out.println(String.valueOf(TotalDeductable) + " of Total : " + String.valueOf(TotalDistance) + " :  Percentage = " + String.valueOf(TotalDeductable*100/TotalDistance));
         
         if(maxP < TotalDeductable*100/TotalDistance)
         {
             maxP = TotalDeductable*100/TotalDistance;
             iterateThrough.add(lg);
             maxIndex = iterateThrough.indexOf(lg);
         }
        
        }    
        

        
        if(maxP >= percentClaim)
        {
            loopBrk = true;
        }
            
            
        }
        
        System.out.println(" Max P : " + maxP);
        
        return SetPersonalRecords(iterateThrough.get(maxIndex).GetRecords(),startODO,endODO);
        
        
        
    }
    
    
    public static void main(String[] args)
    {
        String home = "80 Balcombe Road Mentone VIC 3194";
        String workHQ = "35 Koornang Road Scoresby VIC 3179";
        
        ArrayList<String> HomeAddresses = new ArrayList<String>();
        ArrayList<String> WorkAddresses = new ArrayList<String>();
        ArrayList<String> ClientAddresses = new ArrayList<String>();
        //HomeAddresses.add(new Destination("Monash University Caulfield Melbournce VIC 3145",false,true,false));
        HomeAddresses.add("5 Warley Road Malvern East VIC 3145");
        
        WorkAddresses.add("800 Stud Road Scoresby VIC 3179");
        WorkAddresses.add("9 Helen Kob Drive Braeside VIC 3195");
        
        ClientAddresses.add("47 Robinson St,Dandenong VIC 3175");
        
        ArrayList<LogRecord> lgtest = LogBookCore.GetLogBook(home, HomeAddresses, 
                                                     workHQ, WorkAddresses, 
                                                     ClientAddresses, false, false, 
                                                     "1-Mar-2013", "1-Mar-2014", 
                                                     10000, 25000, 
                                                     65, 3, "DIST");
        
        
        System.out.println(" LG TEST : " + lgtest.size());
    }
    
    
    
    public static ArrayList<LogRecord> SetPersonalRecords(ArrayList<LogRecord> logBook,double startOdo,double endOdo)
    {
        //public void ExportLogBook(String ds,ArrayList<LogRecord> logBook,double startOdo,double endOdo)
    
       
        double expectedTotalTravel = 0.0;
        double personalTravelRatio = 0.0; 
        double totalDistance = 0.0,deductableDistance = 0.0,personalTravel = 0.0;
        Random personalShare = new Random();
        
        
        for(LogRecord lr : logBook)
        {
            expectedTotalTravel += lr.GetDistance();
        }
        
        expectedTotalTravel = endOdo - startOdo - expectedTotalTravel;
        personalTravelRatio = expectedTotalTravel/logBook.size();
        
        
        
        for(LogRecord lr : logBook)
        {
            lr.SetPersonalTravel(personalTravelRatio*personalShare.nextFloat()*2);
            //Odometer Columns
            //Total distance : lr.GetLogItems().get(4)
            //Deductable :         lr.GetLogItems().get(5)
            // Personal Travel : lr.GetPersonalTravel()
            totalDistance = 0.0;deductableDistance = 0.0; personalTravel = 0.0;
            if(lr.GetLogItems().size()>2)
            {
                totalDistance = Double.parseDouble(lr.GetLogItems().get(4).toString());
                deductableDistance = Double.parseDouble(lr.GetLogItems().get(5).toString());
            }
            personalTravel = lr.GetPersonalTravel();
            startOdo += totalDistance + personalTravel;
            //System.out.print("PT : " + lr.GetPersonalTravel() + "\t");lr.print();
        }
        System.out.println(" End ODO : " + endOdo);
        return logBook;
        
    }
    
    
}
