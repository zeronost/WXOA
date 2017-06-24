package com.zero.zexcel.app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import com.zero.zexcel.frame.KeywordMatchFrame;

public class KeywordMatchApp {

	static JFrame frame;
	
	static final Logger logger = Logger.getLogger(KeywordMatchApp.class);

	public static void main(String[] args) {
		run();
	}
	
	private static void run(){
		logger.info("KeywordMatchApp starting");
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				initFrame();
			}
		});
	}

	private static void initFrame() {
		if (frame == null) {
			try {
				frame = KeywordMatchFrame.CreateGUI();
			} catch (UnsupportedLookAndFeelException e) {
				logger.error("Init KeywordMatchFrame gui error", e);
			}
			
			logger.info("KeywordMatchApp started success");
		}
	}
}
