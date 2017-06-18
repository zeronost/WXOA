package com.zero.zexcel;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CoreProcessor {
	
	private String path;
	
	private String patten;
	
	private String key_mark="关键词";
	
	private File keyWordFile;
	
	private List<File> fileList = new ArrayList<File>();
	
	private List<String> keywords = new ArrayList<String>();
	
	public void process(String path, String patten) throws Exception{
		this.path = path;
		this.patten = patten;
		initFileList();
		initKeyWordFile();
		if(!needProcess())
			return;
		run();
	}
	
	private void run() throws Exception{
		System.out.println("Find " + fileList.size() + " source files");
		System.out.println("Find key word file " + keyWordFile.getName());
		System.out.println("Start processing...");
		analisis();
	}
	
	private void initFileList() throws Exception{
		if(path == null)
			return;
		File root = new File(path);
		if(root == null || !root.isDirectory())
			throw new Exception(path + " is not a correct path");
		File[] files = root.listFiles();
		if(null == files || files.length == 0){
			return;
		}
		for(File f:files){
			if(isExcel(f)){
				fileList.add(f);
			}
		}
		if(fileList.isEmpty()){
			return;
		}
	}
	
	private void initKeyWordFile(){
		if(patten == null)
			return;
		File keyFile = new File(patten);
		if(!isExcel(keyFile)){
			return;
		}
		keyWordFile = keyFile;
	}
	
	private void analisis(){
		try {
			loadKeyWords();
			for(File file : fileList){
				SwingUtilities.invokeLater(new Task(file, keywords));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadKeyWords() throws Exception{
		InputStream is = new FileInputStream(keyWordFile);
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
				if(key_mark.equals(c.getStringCellValue())){
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
	
	private boolean isExcel(File file){
		if(file == null || file.isDirectory())
			return false;
		if(file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx"))
			return true;
		return false;
	}
	
	private boolean needProcess(){
		if(null == fileList || fileList.isEmpty() || null == keyWordFile)
			return false;
		return true;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPatten() {
		return patten;
	}

	public void setPatten(String patten) {
		this.patten = patten;
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	private void log(String info, Object obj){
		StringBuffer bf = new StringBuffer();
		bf.append(info);
		if(obj == null)
			bf.append(" NULL");
		else if(obj instanceof Collection){
			Iterator itr = ((Collection)obj).iterator();
			while(itr.hasNext())
				bf.append(itr.next().toString() + "\r\n");
		}else{
			bf.append(obj.toString());
		}
		System.out.println(bf.toString());
	}
}
