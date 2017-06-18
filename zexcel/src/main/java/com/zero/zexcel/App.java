package com.zero.zexcel;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Hello world!
 *
 */
public class App {

	static JFrame frame;

	public static void main(String[] args) {
		run();
	}
	
	private static void run(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				initFrame();
			}
		});
	}

	private static void initFrame() {
		if (frame == null) {
			try {
				frame = MainFrame.CreateGUI();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}
}
