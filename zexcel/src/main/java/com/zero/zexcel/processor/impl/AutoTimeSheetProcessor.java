package com.zero.zexcel.processor.impl;

import java.io.File;

import javax.swing.SwingWorker;

import com.zero.zexcel.frame.AutoTimeSheetFrame;
import com.zero.zexcel.processor.task.AutoTimeSheetTask;

public class AutoTimeSheetProcessor extends AbstractProcessor {
	
	final AutoTimeSheetFrame frame;
	
	private File template = null;
	
	public AutoTimeSheetProcessor(AutoTimeSheetFrame frame){
		this.frame = frame;
	}
	
	@Override
	public void process() throws Exception {
		SwingWorker<Object,Object> task = new AutoTimeSheetTask(this);
		task.execute();
	}
	
	private void initTemplate(){
		
	}
	
	private void startTemplateLoader(){
		
	}
	
	private void startAutoTimeSheetTask(){
		
	}
	
	public AutoTimeSheetFrame getCurrentFrame(){
		return this.frame;
	}
	
}
