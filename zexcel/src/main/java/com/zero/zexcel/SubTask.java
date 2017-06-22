package com.zero.zexcel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingWorker;

import com.zero.zexcel.util.SplitMethod;
import com.zero.zexcel.util.XLSXCovertCSVReader;

public class SubTask<T, V> extends SwingWorker<T, V> {

	private final File file;

	private final List<String> keywords;

	private final int min_col = 26;
	
	private String result;
	
	private final int offset;
	
	private final SplitMethod method;
	
	private CoreProcessor currentProcessor;
	

	public SubTask(File file, List<String> keywords, CoreProcessor processor) {
		super();
		this.file = file;
		this.keywords = keywords;
		this.currentProcessor = processor;
		this.offset = processor.getFrame().getOffset();
		this.method = processor.getFrame().getSplitMethod();
	}

	@Override
	protected T doInBackground() throws Exception {
		try {
			if(null == file || null == keywords || keywords.isEmpty())
				return null;
			Collection<Object> data = XLSXCovertCSVReader.readExcel(file, min_col);
			if(data == null || data.isEmpty())
				return null;
			Collection<Object> processed = new ArrayList<Object>();
			for(Object o : data){
				List<Map<String,StringBuffer>> match = processData(o);
				processed.add(match);
			}
			result = file.getAbsolutePath() + ".zero.xlsx";
			writeResult(result, processed, 50000);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	private List<Map<String,StringBuffer>> processData(Object o){
		if(o == null)
			return null;
		List<String[]> cur = (List<String[]>)o;
		List<Map<String,StringBuffer>> match = new ArrayList<Map<String,StringBuffer>>();
		for(String[] content : cur){
			matchKeyWords(content, match);
		}
		return match;
	}
	
	private void matchKeyWords(String[] content, List<Map<String, StringBuffer>> match){
		StringBuffer bf = compileString(content);
		Map<String, StringBuffer> matched = new HashMap<String,StringBuffer>();
		for(String keyword : keywords){
			match(bf, matched, keyword);
		}
		match.add(matched);
	}
	
	private StringBuffer compileString(String[] data){
		StringBuffer bf = new StringBuffer();
		if(data == null)
			return bf;
		for(String s : data)
			bf.append(s);
		return bf;
	}
	
	private void match(StringBuffer rowVal, Map<String,StringBuffer> matched, String keyword){
		StringBuffer c = processString(rowVal, keyword);
		if(c != null)
			matched.put(keyword, c);
	}
	
	private StringBuffer processString(StringBuffer s, String k){
		return method.process(s, k, offset);
	}
}
