package com.zero.zexcel.util;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class Console extends PrintStream {
	
	private JProgressBar text;

	public Console(OutputStream out, JProgressBar text) {
		super(out);
		this.text = text;
	}
	
	@Override
	public void write(byte[] buff, int off, int len){
		final String message = new String(buff, off, len);
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				if(message.trim().isEmpty())
					return;
				text.setString(message.trim()+"\r\n");
			}
		});
	}
}
