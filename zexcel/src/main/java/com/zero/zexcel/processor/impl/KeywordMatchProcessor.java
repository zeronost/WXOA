package com.zero.zexcel.processor.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import com.zero.zexcel.frame.KeywordMatchFrame;
import com.zero.zexcel.processor.task.KeywordLoader;
import com.zero.zexcel.processor.task.KeywordMatcher;

public class KeywordMatchProcessor extends AbstractProcessor{
	
	static final Logger logger = Logger.getLogger(KeywordMatchProcessor.class);
	
	private String path;
	
	private String patten;
	
	private String keyMark = "关键词";
	
	private File keyWordFile;
	
	private List<File> fileList = new ArrayList<File>();
	
	private List<String> keywords = new ArrayList<String>();
	
	private int processed = 0;
	
	private File resultFolder = null;
	
	private KeywordMatchFrame frame;
	
	public KeywordMatchProcessor(KeywordMatchFrame frame, String path, String patten){
		this.frame = frame;
		this.path = path;
		this.patten = patten;
	}
	
	public void process() throws Exception{
		initFileList();
		initKeyWordFile();
		if(!needProcess()){
			System.out.println("No data source file or keyword file finded, process stoped.");
			frame.enableProcess();
			return;
		}
		createResultFolder();
		frame.initProgressBar(0, fileList.size() + 1);
		startKeywordLoader();
	}
	
	private void createResultFolder(){
		resultFolder = new File(fileList.get(0).getParent()+"\\OUT_"+new SimpleDateFormat("YYYYMMddhhmmss").format(new Date()));
		resultFolder.mkdir();
	}
	
	private void initFileList() throws Exception{
		if(path == null)
			return;
		File root = new File(path);
		if(root == null || !root.isDirectory())
			return;
		File[] files = root.listFiles();
		if(null == files || files.length == 0){
			return;
		}
		for(File f:files){
			if(isExcel(f)){
				fileList.add(f);
			}
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
	
	private void startKeywordLoader(){
		SwingWorker<Object,Object> task = new KeywordLoader(this);
		task.execute();
	}
	
	public void startKeywordMatcher(){
		try {
			for(File file : fileList){
				SwingWorker<Object,Object> subtask = new KeywordMatcher<Object, Object>(file, keywords, this);
				subtask.execute();
			}
			System.out.println("All tasks have been started and running, Please wait... ");
		} catch (Exception e) {
			logger.error("startKeywordMatcher error", e);
		}
	}
	
	public synchronized void FinishOneTask(){
		processed ++;
		this.frame.setProgressValue(processed);
	}
	
	public boolean isComplete(){
		return processed == fileList.size() + 1;
	}
	
	public void onComplete(){
		System.out.println("Process complete");
		frame.enableProcess();
	}
	
	private boolean isExcel(File file){
		if(file == null || file.isDirectory())
			return false;
		if(file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx"))
			return true;
		return false;
	}
	
	private boolean needProcess(){
		if(null == fileList || fileList.isEmpty() || null == keyWordFile){
			return false;
		}
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
	
	public File getKeyWordFile() {
		return keyWordFile;
	}

	public String getKeyMark() {
		return keyMark;
	}
	
	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	
	public KeywordMatchFrame getFrame(){
		return this.frame;
	}
	
	public File getResultFolder(){
		return this.resultFolder;
	}
}
