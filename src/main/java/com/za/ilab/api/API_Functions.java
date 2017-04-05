package com.za.ilab.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.*;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class API_Functions {

	private static final jxl.CellType Null = null;
	int col,Column_Count,Row_Count;
	int colnNum=0;
	Sheet sheet1;
	Workbook wb=null;
	public FileInputStream fis=null;
	public FileOutputStream fos=null;
	public ExtentReports Logger;
	//public XSSFWorkbook workbook=null;
	//public XSSFSheet sheet = null;
	//public XSSFRow row=null;
	//public XSSFCell cell=null;
	String Filename;
	String SheetName="Sheet1";
	String SearchValue, NewValue;
	
	//This is my second commit
	//This is my third commit
	//This is my fourth commit
    ArrayList<String> lines = new ArrayList<String>();
   
	public void Utility(String Filename){
		File fp = new File(Filename);
		try{
			wb=Workbook.getWorkbook(fp);
		}catch (BiffException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();	
		}
		sheet1 = wb.getSheet(SheetName);
		Row_Count=sheet1.getRows();
		Column_Count=sheet1.getColumns();
	}
	public int getCoulNumber(String strCoulmn){
		for(colnNum=0; colnNum<this.sheet1.getColumns(); colnNum++){
			if(this.sheet1.getCell(colnNum,0).getContents().equals(strCoulmn)){
				break;
			}
		}
		return colnNum;
	}
	public int getRowNumber(String strRowData){
		int rowNum;
		for(rowNum=1; rowNum<this.sheet1.getRows();rowNum++){
			if(this.sheet1.getCell(0,rowNum).getContents().toString().equals(strRowData))
				break;
		}
		return rowNum;
	}
	public String getCellData(int iRow, int iColumn){
		return this.sheet1.getCell(iColumn,iRow).getContents().toString();
	}
	public String getCellData(String strColumn, int iRow){
		return this.sheet1.getCell(this.getCoulNumber(strColumn),iRow).getContents().toString();
	}
	public int rowCount(String sPath) throws Exception{
	/*	int xRows;
		File myxl = new File(sPath);
		FileInputStream myStream = new FileInputStream(myxl);		
		HSSFWorkbook myWB = new HSSFWorkbook(myStream);
		HSSFSheet mySheet = myWB.getSheetAt(0);
		xRows = mySheet.getLastRowNum();
		return xRows;*/
		
		return sheet1.getRows();
	}
	public int columnCount(){
		return sheet1.getColumns();
	}
	public String WebServiceContent(String RequestPath,String RequestUrl, ExtentReports logger)
	{
		String response;
		try {
			URL javaRequest = new URL(RequestUrl);
			HttpURLConnection myconnect = (HttpURLConnection) javaRequest.openConnection();
			myconnect.setRequestProperty("Content-type", "application/soap+xml;charset=UTF-8");
			myconnect.setDoOutput(true);
			myconnect.setDoInput(true);
			StringBuffer requestContent = new StringBuffer();
			BufferedReader bufferedReader = new BufferedReader(new FileReader(RequestPath));
			String sLine = null;
			while((sLine = bufferedReader.readLine()) != null)
			{		           
				requestContent.append(sLine);		 
				
			}
					
			OutputStream requestStream = myconnect.getOutputStream();
			requestStream.write(requestContent.toString().getBytes());
			
			InputStream resStream = myconnect.getInputStream();
			StringBuilder sb = new StringBuilder();
			int ch = resStream.read();
			while(ch != -1)
			{
				sb.append((char)ch);
				ch = resStream.read();
			}
			response = sb.toString();
	//		logger.log(LogStatus.INFO, response);
			return response ;
			
		} 
		 catch (IOException e)
		 {			
			return e.getMessage();	
		 }		
		
	}
	
	public void XmlUpdater(String[] ColumnValue, String[] SearchValue,String xmlPath) 
	{
		File f1, f2=null;
        FileReader fr=null;
        BufferedReader br=null;
        FileWriter fw=null;
        BufferedWriter out=null;
        String line = null;
        try {
        	f1 = new File(xmlPath);
            fr = new FileReader(f1);
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) 
            {
            	for(int i=0; i<SearchValue.length; i++)
            	{
            		 if (line.contains(SearchValue[i])) 
            		 {
            			line = line.replace(SearchValue[i], ColumnValue[i]);
                      	 
            		 }                        
            		
            	}
            	lines.add(line);          	               
            }
            f2=new File(".//XmlTestDataOutput/NewFile.xml"); 
            fw = new FileWriter(f2,true);
            out = new BufferedWriter(fw);
            for (String s : lines){
                out.write(s);
            	out.flush();}
                
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try{
            lines.clear();
            fr.close();
            br.close();
            out.close();
         
           
            }catch(IOException ioe)

            {
                ioe.printStackTrace();
            }
        }

    }
	public void FileDelete(String vPath)
	{
		File objDelete=new File(vPath);
		if(objDelete.exists())
		{
			objDelete.delete();
		}
		
	}
	
}


