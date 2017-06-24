package com.zero.zexcel.processor.task;

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

import com.zero.zexcel.processor.api.Processor;
import com.zero.zexcel.processor.impl.KeywordMatchProcessor;


public class KeywordLoader extends SwingWorker<Object, Object> {
	
	private KeywordMatchProcessor processor;
	
	public KeywordLoader(Processor processor){
		super();
		this.processor = (KeywordMatchProcessor)processor;
	}

	@Override
	protected Object doInBackground() throws Exception {
		System.out.println("Loading keywords...");
		loadKeyWords();
		return processor;
	}
	
	@Override
	protected void done(){
		System.out.println("Keywords load complete");
		processor.FinishOneTask();
		processor.startKeywordMatcher();
	}
	
	private void loadKeyWords() throws Exception{
		InputStream is = new FileInputStream(processor.getKeyWordFile());
		Workbook wb = new XSSFWorkbook(is);
		is.close();
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
