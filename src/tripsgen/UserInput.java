/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tripsgen;

import entity.DayRoute;
import entity.Destination;
import entity.Route;
import entity.RouteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import limitingrules.UserLimitations;

/**
 *
 * @author harryp
 */
public class UserInput {
    
    Destination HomeAddress;
    Destination WorkHQAddress;
    
    ArrayList <Destination> WorkAddresses;
    ArrayList <Destination> AlternateHomeAddresses;
    
    ArrayList <UserLimitations> Rules;
    
    boolean StartAtAnyWorkLocation;
    
    public UserInput(Destination ha,Destination workHQ,ArrayList was,ArrayList aha,boolean startAtAny)
    {
        HomeAddress = ha;
        WorkAddresses = was;
        AlternateHomeAddresses = aha;
        StartAtAnyWorkLocation = startAtAny;
        if(startAtAny) {
            WorkAddresses.add(workHQ);
            WorkHQAddress = null;
        }
        else {
            WorkHQAddress = workHQ;
        }
    }
    
    
    public UserInput(Destination ha,ArrayList was,ArrayList aha,ArrayList rules,boolean startAtAny)
    {
        HomeAddress = ha;
        WorkAddresses = was;
        AlternateHomeAddresses = aha;
        Rules = rules;
        StartAtAnyWorkLocation = startAtAny;
    }
    
    
    public RouteRepository DistinctRoutes(int n)
    {
        RouteRepository distinctRoutes = new RouteRepository();
        DayRoute temp;
        Random hopsPicker = new Random();
        int hops=0;
        
        
        
        Destination start = null,next = null;
        
        for(int iterate=0;iterate<n;iterate++)
        {
           temp = new DayRoute();
           start = HomeAddress;

            if(StartAtAnyWorkLocation)
            {
                next = GetNextWorkAddress(start);
                //hops = hopsPicker.nextInt(WorkAddresses.size()-1) + 1;
            }
            else
            {
                next = WorkHQAddress;
                //hops = hopsPicker.nextInt(WorkAddresses.size()-1) + 1 + 1;
            }
            
            hops = hopsPicker.nextInt(WorkAddresses.size());
            //System.out.println(" Hops : " + hops);

            temp.AddRoute(new Route(start,next,0));

            for(int h=0;h<hops;h++)
            {
                start = next;
                next = GetNextWorkAddress(start);

                temp.AddRoute(new Route(start,next,0));
            }

            //Add Final Route to home
            temp.AddRoute(new Route(next,GetHomeStop(),0));



            if(!distinctRoutes.IsRoutePresent(temp))
            {
                //System.out.println("Route Added");
                //temp.print();
                distinctRoutes.AddRoute(temp);
            }
            else
            {
                //System.out.println("Route Rejected");
            }

        }
        
        return distinctRoutes;
        
        
    }
    
    public RouteRepository ProfileRouteRepository(RouteRepository repo,int groupCount)
    {
        ArrayList<DayRoute> repoList = new ArrayList<DayRoute>();
        ArrayList<ArrayList<DayRoute>> groupStore;
        RouteRepository repoReturn = new RouteRepository();
        int itemsPerGroup = Math.round(repo.GetSize()/groupCount);
        
        System.out.println(" Group Size : " + itemsPerGroup + " : " + repo.GetSize()/groupCount);
        
        
        groupStore = GetGroup(repo.GetRouteList(),itemsPerGroup,groupCount,"DIST");
        
        for(ArrayList<DayRoute> dGrp : groupStore)
        {
            //System.out.println("Group " + groupStore.indexOf(dGrp) + " ---------------------------------------------------");
            for(DayRoute dr : dGrp )
            {
                
                dr.SetDistGroupIndex(groupStore.indexOf(dGrp));
                //dr.print();
                repoList.add(dr);
            }
        }
        
        groupStore = GetGroup(repoList,itemsPerGroup,groupCount,"HOPS");
        repoList.clear();
        
        for(ArrayList<DayRoute> dGrp : groupStore)
        {
            //System.out.println("Group " + groupStore.indexOf(dGrp) + " ---------------------------------------------------");
            for(DayRoute dr : dGrp )
            {
                
                dr.SetHopGroupIndex(groupStore.indexOf(dGrp));
                //dr.print();
                repoList.add(dr);
            }
        }
        
        
        groupStore = GetGroup(repoList,itemsPerGroup,groupCount,"DISTHOPS");
        repoList.clear();
        
        for(ArrayList<DayRoute> dGrp : groupStore)
        {
            System.out.println("Group " + groupStore.indexOf(dGrp) + " ---------------------------------------------------");
            for(DayRoute dr : dGrp )
            {
                
                dr.SetGroupIndex(groupStore.indexOf(dGrp));
                dr.print();
                //repoList.add(dr);
                repoReturn.AddRoute(dr);
            }
        }
        
        return repoReturn;
    }
    
    
    public ArrayList<ArrayList<DayRoute>> GetGroup(ArrayList<DayRoute> clone, int itemsInGroup,int GroupCount,String mode)
    {
        ArrayList<DayRoute> tempGroup;
        ArrayList<ArrayList<DayRoute>> GroupHolder = new ArrayList();
        int groupIndex = 1;
        DayRoute tempHolder;
        double leftRatio = 0.0,rightRatio = 0.0;
        
        do
        {
            tempGroup = new ArrayList() ;
            int swapCount = 0;
            int groupSize = 0;
            
            if(clone.size()>= itemsInGroup && groupIndex < GroupCount)
            {
                groupSize = itemsInGroup;
            }
            else
            {
                groupSize = clone.size();
            }
        
            for(DayRoute dr : clone.subList(0, groupSize))
            {
                tempGroup.add(dr);
            }
            clone.removeAll(tempGroup);
            
            //System.out.println(" TEMP Size : " + tempGroup.size() + " : Clone Size -" + clone.size());
            
            
            
            do
            {
                swapCount = 0;

                for(DayRoute dr : tempGroup)
                {
                    for(DayRoute dm : clone)
                    {
                        if(mode.equals("DIST"))
                        {
                            leftRatio = dr.GetDistRatio();
                            rightRatio = dm.GetDistRatio();
                        }
                        else if(mode.equals("HOPS"))
                        {
                            leftRatio = dr.GetHopRatio();
                            rightRatio = dm.GetHopRatio();
                        }
                        else if(mode.equals("DISTHOPS"))
                        {
                            leftRatio = dr.GetDistRatio()*dr.GetHopRatio();
                            rightRatio = dm.GetDistRatio()*dm.GetHopRatio();
                        }
                        
                        if(leftRatio>rightRatio)
                        {
                            //swap items
                            //System.out.println("Swap Items : " + tempGroup.indexOf(dr) + " : " + clone.indexOf(dm));
                            tempHolder = dr;
                            tempGroup.set(tempGroup.indexOf(dr), dm);
                            clone.set(clone.indexOf(dm), tempHolder);
                            swapCount ++;
                            break;
                        }
                        
                        
                    }
                }
                
                //System.out.println("Swap Count : " + swapCount);
               
            }while(swapCount != 0);
        
            GroupHolder.add(tempGroup);
            groupIndex ++;
            
        }while(groupIndex <= GroupCount);
        
        
        return GroupHolder;
    }
    
    
    
    public Destination GetNextWorkAddress(Destination start)
    {
        Random workPicker = new Random();
        int pickWork = workPicker.nextInt(WorkAddresses.size()+1);
        
        if(pickWork == 0 && WorkHQAddress != null && start != WorkHQAddress)
        {
            return WorkHQAddress;
        }
        else
        {
            if(pickWork == WorkAddresses.size())
            {
                pickWork --;
            }
            
            if(start != WorkAddresses.get(pickWork))
            {
                return WorkAddresses.get(pickWork);
            }
            else
            {
                return GetNextWorkAddress(start);
            }
        }
    }
    
    public Destination GetHomeStop()
    {
        if(AlternateHomeAddresses.size()>0)
        {
            Random homePicker = new Random();
            int pickHome = homePicker.nextInt(AlternateHomeAddresses.size()+1);
            //System.out.println(":" + pickHome + " : " + AlternateHomeAddresses.size());
            if(pickHome == AlternateHomeAddresses.size())
            {
                return HomeAddress;
            }
            else
            {
                return AlternateHomeAddresses.get(pickHome);
            }
        }
        else
        {
            return HomeAddress;
        }
    }
}
