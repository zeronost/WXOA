package com.zero.zexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Task extends Thread {
	
	private final File file;
	
	private final List<String> keywords;
	
	private Map<Integer, StringBuffer> out = new HashMap<Integer, StringBuffer>();
	
	private Map<Sheet,Object> rs = new HashMap<Sheet,Object>();

	public Task(File file, List<String> keywords) {
		super();
		this.file = file;
		this.keywords = keywords;
	}

	@Override
	public void run() {
		try {
			if(null == file || null == keywords || keywords.isEmpty())
				return;
			InputStream is = new FileInputStream(file);
			Workbook wb = new SXSSFWorkbook();
			for(int i = 0; i < wb.getNumberOfSheets(); i++){
				Sheet sheet = wb.getSheetAt(i);
				matchKeywords(sheet);
				if(!out.isEmpty()){
					rs.put(sheet, out);
					out = new HashMap<Integer, StringBuffer>();
				}
			}
			if(!rs.isEmpty())
				write(wb);
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void write(Workbook wb){
		if(rs.isEmpty())
			return;
		for(Sheet sheet : rs.keySet()){
			Map<Integer,StringBuffer> c = (Map<Integer, StringBuffer>) rs.get(sheet);
			for(int i : c.keySet()){
				Row row = sheet.getRow(i);
				Cell o = row.createCell(row.getPhysicalNumberOfCells()+1);
				o.setCellValue(c.get(i).toString());
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(file); 
			wb.write(fout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void matchKeywords(Sheet sheet){
		for(int i = 0; i < sheet.getPhysicalNumberOfRows(); i ++){
			Row r = sheet.getRow(i);
			processRow(r);
		}
	}
	
	private void processRow(Row row){
		if(row == null)
			return;
		StringBuffer rowVal = compileCells(row);
		if(rowVal == null)
			return;
		for(String word : keywords){
			match(row.getRowNum(), rowVal, word);
		}
	}
	
	private StringBuffer compileCells(Row row){
		if(row == null)
			return null;
		StringBuffer rt = new StringBuffer();
		for(int i = 0; i < row.getPhysicalNumberOfCells(); i++){
			processCell(row.getCell(i), rt);
		}
		return rt;
	}
	
	private void processCell(Cell cell, StringBuffer rs){
		String v = getCellValue(cell);
		if(v == null || v.isEmpty())
			return;
		rs.append(v);
	}
	
	private String getCellValue(Cell cell){
		if(cell == null)
			return null;
		String rt = null;
		try{
			rt = cell.getStringCellValue();
		}catch(Exception e){
			try{
				rt = String.valueOf(cell.getNumericCellValue());
			}catch(Exception e1){
			}
		}
		return rt;
	}
	
	private void match(int key,StringBuffer rowVal, String keyword){
		StringBuffer p = out.get(key) == null ? new StringBuffer() : out.get(key);
		StringBuffer c = new StringBuffer();
		Matcher m = Pattern.compile("(.*?"+keyword+".*?)。").matcher(rowVal);
		while(m.find()){
			c.append(m.group(1) + "\r\n");
		}
		if(c.length() > 0){
			out.put(key, p.append("\r\n").append(keyword).append(" :: \r\n ").append(c));
		}
	}
	
}
