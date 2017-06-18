package com.zero.zexcel;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

public class Console extends PrintStream {
	
	private JTextComponent text;
	
	private StringBuffer sb = new StringBuffer();

	public Console(OutputStream out, JTextComponent text) {
		super(out);
		this.text = text;
	}
	
	@Override
	public void write(byte[] buff, int off, int len){
		final String message = new String(buff, off, len);
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				if(sb.length() > 10240)
					sb.setLength(0);
				sb.append(message.trim()+"\r\n");
				text.setText(sb.toString());
			}
		});
	}
}
