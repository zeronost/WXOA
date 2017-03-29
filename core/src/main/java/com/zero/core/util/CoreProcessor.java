package com.zero.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


public class CoreProcessor {
	
	private int hitcount = 0;
	
	private int filecounts = 0;
	
	private String ann ="@DDR";
	
	private String nest ="Collection<";
	
	private static final Logger logger = Logger.getLogger(CoreProcessor.class);
	
	public static void main(String[] args){
		CoreProcessor process = new CoreProcessor();
		try {
			Map<String, Object> result = process.analysis("D:/Project/WXOA_DEV/WXOA/core");
			logger.info("hits "+ process.hitcount +" filecount " + process.filecounts);
			logger.info(result);
		} catch (Exception e) {
			logger.error("error occured",e);
		}
	}
	
	private Map<String, Object> analysis(String rootPath) throws Exception{
		File file = new File(rootPath);
		if(!file.exists()){
			logger.error(" the path " + rootPath + " is not exists");
		}
		
		Map<String, Object> hitsMap = new HashMap<String,Object>();
		
		fileLoop(file, hitsMap);
		
		filecounts = hitsMap.size();
		
		return hitsMap;
	}
	
	private void fileLoop(File file, Map hitsMap) throws Exception{
		if(!file.exists() || file.getName().startsWith("."))
			return;
		if(file.isFile()){
			if(rosolveFileWithPatten(file, hits)){
				Collection<StringBuffer> hits = new ArrayList<StringBuffer>();
				hitsMap.put(file.getPath(), hits);
			}
			return;
		}
		
		for(File temp : file.listFiles()){
			fileLoop(temp, hitsMap);
		}
	}
	
	
	
	private boolean rosolveFileWithPatten(File file,Collection<StringBuffer> hits) throws Exception{
		if(!isJavaFile(file))
			return false;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while((line = reader.readLine()) != null){
			hit(line, reader, hits);
		}
		return !hits.isEmpty();
	}
	
	private boolean isJavaFile(File file){
		return null != file && file.isFile() && file.getName().endsWith(".java");
	}

	private boolean hit(String cur, BufferedReader reader,Collection<StringBuffer> hits) throws Exception{
		if(cur.contains(ann)){
			StringBuffer lines = new StringBuffer(cur);
			String cursor = null;
			while((cursor = reader.readLine()) != null){
				lines.append(cursor);
				if(cursor.contains(nest)){
					hits.add(lines);
					hitcount++;
					return true;
				}
				if(cursor.contains(";")){
					break;
				}
			}
		}
		return false;
	}
}
