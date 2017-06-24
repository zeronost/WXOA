package com.zero.zexcel.processor.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import com.zero.zexcel.processor.api.Processor;
import com.zero.zexcel.processor.impl.KeywordMatchProcessor;
import com.zero.zexcel.util.SplitMethod;
import com.zero.zexcel.util.XLSXCovertCSVReader;

public class KeywordMatcher<T, V> extends SwingWorker<T, V> {
	
	static final Logger logger = Logger.getLogger(KeywordMatcher.class);

	private final File file;

	private final List<String> keywords;

	private final int min_col = 26;
	
	private String result;
	
	private final int offset;
	
	private final SplitMethod method;
	
	private KeywordMatchProcessor currentProcessor;
	

	public KeywordMatcher(File file, List<String> keywords, Processor processor) {
		super();
		this.file = file;
		this.keywords = keywords;
		this.currentProcessor = (KeywordMatchProcessor)processor;
		this.offset = currentProcessor.getFrame().getOffset();
		this.method = currentProcessor.getFrame().getSplitMethod();
		this.result = currentProcessor.getResultFolder().getAbsolutePath();
		logger.info(getCurrentDescription() + " init complete" );
	}

	@Override
	protected T doInBackground() throws Exception {
		logger.info(getCurrentDescription()+ " start running" );
		Long b =  System.currentTimeMillis();
		try {
			if(null == file || null == keywords || keywords.isEmpty())
				return null;
			Collection<Object> data = XLSXCovertCSVReader.readExcel(file, min_col);
			if(data == null || data.isEmpty())
				return null;
			Collection<Object> processed = new ArrayList<Object>();
			for(Object o : data){
				List<Map<String,StringBuilder>> match = processData(o);
				processed.add(match);
			}
			result = result+"\\" + file.getName() + ".zero.xlsx";
			writeResult(result, processed, 50000);
		} catch (Exception e) {
			logger.error(getCurrentDescription() + " error ", e);
		}
		Long e = System.currentTimeMillis();
		logger.info(getCurrentDescription()+ " complete, cost time " + (e - b) );
		return null;
	}
	
	@Override
	protected void done(){
		System.out.println("Task process file::  " + file.getName() + " complete!");
		currentProcessor.FinishOneTask();
		if(currentProcessor.isComplete())
			currentProcessor.onComplete();
	}
	
	private void writeResult(String path, Collection<Object> data, int maxSize){
		XLSXCovertCSVReader.createExcelFile(path, data, keywords, maxSize);
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String,StringBuilder>> processData(Object o){
		if(o == null)
			return null;
		List<String[]> cur = (List<String[]>)o;
		List<Map<String,StringBuilder>> match = new ArrayList<Map<String,StringBuilder>>();
		for(String[] content : cur){
			matchKeyWords(content, match);
		}
		return match;
	}
	
	private void matchKeyWords(String[] content, List<Map<String, StringBuilder>> match){
		StringBuilder bf = compileString(content);
		Map<String, StringBuilder> matched = new HashMap<String,StringBuilder>();
		for(String keyword : keywords){
			match(bf, matched, keyword);
		}
		if(!matched.isEmpty())
			match.add(matched);
		else
			match.add(null);
	}
	
	private StringBuilder compileString(String[] data){
		StringBuilder bf = new StringBuilder();
		if(data == null)
			return bf;
		for(String s : data)
			bf.append(s==null?"":s);
		return bf;
	}
	
	private void match(StringBuilder rowVal, Map<String,StringBuilder> matched, String keyword){
		StringBuilder c = processString(rowVal, keyword);
		if(c != null)
			matched.put(keyword, c);
	}
	
	private StringBuilder processString(StringBuilder s, String k){
		return method.process(s, k, offset);
	}
	
	private String getCurrentDescription(){
		return "KeywordMatcher#" + Thread.currentThread().getId()+" for file  " +this.file.getAbsolutePath();
	}
}
