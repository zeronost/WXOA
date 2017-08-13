package com.zero.zexcel.frame;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import com.zero.zexcel.processor.impl.AutoTimeSheetProcessor;

public class AutoTimeSheetFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	
	public String getUserName(){
		return "882673";
	}
	
	public static String getPassword(){
		return "li_chen585236";
	}
	
	public String getStartDate(){
		return "2017-06-19";
	}
	
	public String getEndDate(){
		return "2017-06-23";
	}
	
	public String getTemplate(){
		return "";
	}
	
	public void initProgressBar(int min, int max){
		
	}
	
	public JProgressBar getProgressBar(){
		return null;
	}
	
	
	public static void main(String[] args){
		try {
			new AutoTimeSheetProcessor(new AutoTimeSheetFrame()).process();
			System.out.println("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
