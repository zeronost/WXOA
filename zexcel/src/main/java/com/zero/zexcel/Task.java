package com.zero.zexcel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.zero.zexcel.util.XLSXCovertCSVReader;

public class Task extends Thread {
	
	private final File file;
	
	private final List<String> keywords;
	
	private final int min_col = 26;
	
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
			Collection<Object> data = XLSXCovertCSVReader.readExcel(file, min_col);
			if(data == null || data.isEmpty())
				return;
			Collection<Object> processed = new ArrayList<Object>();
			for(Object o : data){
				List<StringBuffer> match = processData(o);
				processed.add(match);
			}
			writeResult(file.getAbsolutePath() + ".zero.xlsx", processed, 50000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writeResult(String path, Collection<Object> data, int maxSize){
		XLSXCovertCSVReader.createExcelFile(path, data, maxSize);
	}
	
	@SuppressWarnings("unchecked")
	private List<StringBuffer> processData(Object o){
		if(o == null)
			return null;
		List<String[]> cur = (List<String[]>)o;
		List<StringBuffer> match = new ArrayList<StringBuffer>();
		for(String[] content : cur){
			matchKeyWords(content, match);
		}
		return match;
	}
	
	private void matchKeyWords(String[] content, List<StringBuffer> match){
		StringBuffer bf = compileString(content);
		if("Empty Row".equalsIgnoreCase(bf.toString())){
			match.add(bf);
			return;
		}
		StringBuffer matched = new StringBuffer();
		for(String keyword : keywords){
			match(bf, matched, keyword);
		}
		if(matched.length() == 0)
			matched.append("Not Match");
		match.add(matched);
	}
	
	private StringBuffer compileString(String[] data){
		StringBuffer bf = new StringBuffer();
		if(data == null)
			return bf.append("Empty Row");
		for(String s : data)
			bf.append(s);
		if("".equals(bf.toString().trim()))
			return bf.append("Empty Row");
		return bf;
	}
	
	private void match(StringBuffer rowVal,StringBuffer matched, String keyword){
		StringBuffer c = new StringBuffer();
		if(rowVal.toString().contains(keyword)){
			c = processString(rowVal, keyword);
		}
		matched.append(c);
	}
	
	private StringBuffer processString(StringBuffer s, String k){
		StringBuffer rs = new StringBuffer();
		if(s == null || k == null || !s.toString().contains(k))
			return rs;
		rs.append("Keyword:: ").append(k).append("\r\n");
		String[] p = s.toString().split(k);
		for(int i = 1; i < p.length; i ++){
			rs.append(i).append(": ").append(cutString(p[i-1], 30, false))
			.append(k).append(cutString(p[i], 30, true)).append("\r\n");
		}
		return rs;
	}
	
	private String cutString(String s, int offset, boolean ispre){
		if(s == null || s.length() <= offset)
			return s;
		if(ispre)
			return s.substring(0, offset);
		else
			return s.substring(s.length() - offset + 1, s.length());
	}
	
}
