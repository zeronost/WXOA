package com.zero.zexcel;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class KeywordLoader extends SwingWorker<Object, Object> {
	
	private CoreProcessor processor;
	
	public KeywordLoader(CoreProcessor processor){
		super();
		this.processor = processor;
	}

	@Override
	protected Object doInBackground() throws Exception {
		loadKeyWords();
		return processor;
	}
	
	@Override
	protected void done(){
		processor.startSubTask();
	}
	
	private void loadKeyWords() throws Exception{
		InputStream is = new FileInputStream(processor.getKeyWordFile());
		Workbook wb = new XSSFWorkbook(is);
		int sheet_count = wb.getNumberOfSheets();
		for(int i = 0; i < sheet_count; i++){
			Sheet sheet = wb.getSheetAt(i);
			Point keyPoint = getKeyPoint(sheet);
			fillKeyWords(sheet, keyPoint);
		}
		wb.close();
	}
	
	private Point getKeyPoint(Sheet sheet){
		Point keyPoint = null;
		for(int i = 0; i<sheet.getPhysicalNumberOfRows(); i ++){
			Row r = sheet.getRow(i);
			for(int j = 0; j < r.getPhysicalNumberOfCells(); j++){
				Cell c = r.getCell(j);
				if(processor.getKeyMark().equals(c.getStringCellValue())){
					keyPoint = new Point(i,j);
					break;
				}
			}
			if(null != keyPoint)
				break;
		}
		return keyPoint;
	}
	
	private void fillKeyWords(Sheet sheet, Point keyPoint){
		if(sheet == null || keyPoint == null)
			return;
		List<String> keywords = processor.getKeywords();
		for(int i = keyPoint.x + 1; i < sheet.getPhysicalNumberOfRows(); i++){
			Cell cell = sheet.getRow(i).getCell(keyPoint.y);
			if(null == cell)
				continue;
			String val = cell.getStringCellValue();
			if(null == val || "".equals(val.trim()))
				continue;
			if(!keywords.contains(val)){
				keywords.add(val);
			}
		}
	}
}
