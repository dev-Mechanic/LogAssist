/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataExport;

import entity.DayRoute;
import entity.LogRecord;
import java.awt.FileDialog;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author harryp
 */
public class ExportToExcel {
    Workbook wb;
    String fileName;
    
    public ExportToExcel(String fname)
    {
        wb = new HSSFWorkbook();
        fileName = fname;
    }
    
    public void ExportRoutes(String ds,ArrayList<DayRoute> listRoutes)
    {
        Sheet sheet = wb.createSheet(ds);
        Row row;
        Cell cell;
        int rowIx=1,colIx=1;
        row = sheet.createRow(0);
        
        
        for(Object colH : GetTripHeaders())
        {
            cell = row.createCell(colIx);
            cell.setCellValue(colH.toString());
            colIx++;
        }
        
        
        
        for(DayRoute dr : listRoutes)
        {
            row = sheet.createRow(rowIx);
            colIx = 1;
            
            for(Object val : dr.GetTripItems())
            {
                cell = row.createCell(colIx);
                cell.setCellValue(val.toString());
                colIx++;
            }
            
            rowIx++;
        }
    }
    
    
    public void ExportLogBook(String ds,ArrayList<LogRecord> logBook,double startOdo,double endOdo)
    {
       
        Sheet sheet = wb.createSheet(ds);
        
        Row row;
        Cell cell;
        int rowIx=1,colIx=1;
        row = sheet.createRow(0);
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
        
        for(Object colH : GetLogHeaders())
        {
            cell = row.createCell(colIx);
            cell.setCellValue(colH.toString());
            colIx++;
        }
        
        
        for(LogRecord lr : logBook)
        {
            row = sheet.createRow(rowIx);
            colIx = 1;
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
            
            cell = row.createCell(colIx);
            cell.setCellValue(lr.GetDate().toString(DateTimeFormat.forPattern("dd-MMM-yyyy")));
            colIx++;
            
            //Add start Odo
            cell = row.createCell(colIx);
            cell.setCellValue(startOdo);
            colIx ++;
            
            cell = row.createCell(colIx);
            cell.setCellValue(startOdo +  totalDistance - deductableDistance);
            colIx ++;
            
            cell = row.createCell(colIx);
            cell.setCellValue(startOdo + totalDistance);
            colIx ++;

            cell = row.createCell(colIx);
            cell.setCellValue(startOdo + totalDistance + personalTravel);
            colIx ++;
            
            for(Object val : lr.GetLogItems())
            {
                cell = row.createCell(colIx);
                cell.setCellValue(val.toString());
                colIx++;
            }
            
            
            
            
            
            startOdo += totalDistance + personalTravel;
            
            
            
            rowIx++;
        }
    }
    
    
    public void CommitWorkBook() 
    {
        try
        {
            
            String file = fileName + ".xls";        
            FileOutputStream out = new FileOutputStream(file);
            wb.write(out);
            out.close();
        }
        catch(Exception ex)
        {
            System.out.println("Exporter Exception : " + ex.getMessage());
        }
    }
    
    public ArrayList GetLogHeaders()
    {
        ArrayList retList = new ArrayList();
        
        retList.add("Date");
        retList.add("ODO Start Day");
        retList.add("ODO Work Start");
        retList.add("ODO Work End");
        retList.add("ODO End Day");
        retList.add("Uknown Personal Travel");
        retList.add("Distance Group");
        retList.add("Hops Group");
        retList.add("Dist & Hops Group");
        retList.add("Total Distance");
        retList.add("Deductable Distance");
        retList.add("Distance Ratio");
        retList.add("Hop Ratio");
        retList.add("PitStops");
        
        return retList;
    }
    
    
     public ArrayList GetTripHeaders()
    {
        ArrayList retList = new ArrayList();
        
        
        retList.add("Distance Group");
        retList.add("Hops Group");
        retList.add("Dist & Hops Group");
        retList.add("Total Distance");
        retList.add("Deductable");
        retList.add("Distance Ratio");
        retList.add("Hop Ratio");
        retList.add("PitStops");
        
        return retList;
    }
}
