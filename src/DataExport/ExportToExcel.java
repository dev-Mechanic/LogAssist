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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
    
    
    public void ExportLogBook(String ds,ArrayList<LogRecord> logBook)
    {
       
        Sheet sheet = wb.createSheet(ds);
        
        Row row;
        Cell cell;
        int rowIx=1,colIx=1;
        row = sheet.createRow(0);
        
        
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
            for(Object val : lr.GetLogItems())
            {
                cell = row.createCell(colIx);
                cell.setCellValue(val.toString());
                colIx++;
            }
            
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
