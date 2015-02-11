package com.jaspertutorial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRCsvDataSource;

/**
 * Jasper report tutorial
 * Modified code from http://www.tutorialspoint.com/jasper_reports/jasper_view_and_print_reports.htm
 * @author Andrea
 *
 */
public class JasperReportFill {
        
   public static void main(String[] args) {
       
       //jrxml compiled file (. jasper extension) is used as sourceFile here
       //this is your Jasper report template created in the Jasper Studio
       String sourceFileName = "Blank.jasper";
       
       //JRCsvDataSource is used to import .csv files as data source
       JRCsvDataSource csvSource = null;
       
       try {
           //some files requite special encoding in order to imported(i.e UTF-8 or UTF-16
           csvSource = new JRCsvDataSource(new File("path to file being imported"), "UTF-16");
           
           //first roll of csv file is treated as column header
           csvSource.setUseFirstRowAsHeader(true);
           csvSource.setFieldDelimiter('|');
           csvSource.setRecordDelimiter("\r\n");
       } 
       catch (FileNotFoundException e1) {
           e1.printStackTrace();
       } 
       catch (IOException e) {
           e.printStackTrace();
       }
       
       // begin--unnecessary line
       printColumnNames(csvSource);
       // end--unnecessary line
       
       exportReport(sourceFileName, csvSource);
       
   }
   
   /**
    * Exports report into PDF file
    * @param sourceFileName
    * @param csvSource
    */
   private static void exportReport(String sourceFileName, JRCsvDataSource csvSource) {
       //at this moment, it is unclear why the parameters map is necessary in order to fill the report
       Map<String, Object> parameters = new HashMap<String, Object>();
       JasperPrint print = null;
       try {
           print = JasperFillManager.fillReport(
                   sourceFileName,
                   parameters,
                   csvSource);
           OutputStream output = new FileOutputStream(new File("path and name of the pdf"));
           JasperExportManager.exportReportToPdfStream(print, output);
       } catch (JRException e) {
           e.printStackTrace();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
   }
   
   /**
    * unnecessary method (used for debugging only)
    * @param csvSource
    */
   private static void printColumnNames(JRCsvDataSource csvSource) {
       // begin--unnecessary lines (used for testing)
       Map<String, Integer> columnNames = csvSource.getColumnNames();
       Set<String> keySet = columnNames.keySet();
       String[] arrayColumns = keySet.toArray(new String[keySet.size()]);
       for (int i = 0; i < arrayColumns.length; i++) {
           System.out.println(arrayColumns[i]);
       }
   }
}