package com.zero.zexcel.processor.task;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import com.zero.zexcel.frame.AutoTimeSheetFrame;
import com.zero.zexcel.processor.impl.AutoTimeSheetProcessor;
import com.zero.zexcel.util.Encrypt;
import com.zero.zexcel.util.HttpHandler;
import com.zero.zexcel.util.TimeSheet;

public class AutoTimeSheetTask extends SwingWorker<Object, Object> {

	private AutoTimeSheetProcessor currentProcessor;

	public AutoTimeSheetTask(AutoTimeSheetProcessor processor) {
		currentProcessor = processor;
	}

	@SuppressWarnings("unused")
	@Override
	protected Object doInBackground() throws Exception {
		try {
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	private String getLoginUrl() throws Exception {
		/*String userName = currentProcessor.getCurrentFrame().getUserName();
		String password = currentProcessor.getCurrentFrame().getPassword();
		String encrypt = Encrypt.MD5(password, 2);
		return "http://192.168.2.16:28081/cvicdns/xcom/rbac/loginAction.do?password=" + encrypt + "&xxxxxx=" + password
				+ "&loginName=" + userName + "&rmbUser=on";*/
		return "http://192.168.2.16:28081/cvicdns/xcom/rbac/logon.do";
	}

	private String getWriteDateUrl(String date) {
		return "http://192.168.2.16:28081/cvicdns/rzgl/gzrz.do?operFlag=read&gzrq=" + date;
	}

	private String getAddUrl(List<TimeSheet> sheets) throws Exception {
		if (sheets == null || sheets.isEmpty())
			return null;
		StringBuilder bf = new StringBuilder();
		bf.append("http://192.168.2.16:28081/cvicdns/rzgl/gzrz.do?operFlag=add");
		for (TimeSheet sheet : sheets) {
			bf.append(sheet.getPropertyStr());
		}
		return Encrypt.encodeUrl(bf.toString());
	}

	private String getUpdateUrl(List<TimeSheet> sheets) throws Exception {
		if (sheets == null || sheets.isEmpty())
			return null;
		StringBuilder bf = new StringBuilder();
		bf.append("http://192.168.2.16:28081/cvicdns/rzgl/gzrz.do?operFlag=update");
		for (TimeSheet sheet : sheets) {
			bf.append(sheet.getPropertyStr());
		}
		return Encrypt.encodeUrl(bf.toString());
	}

	private String getDeleteUrl(String rzxh) throws Exception {
		StringBuilder bf = new StringBuilder();
		bf.append("http://192.168.2.16:28081/cvicdns/rzgl/gzrz.do?operFlag=update2&rzxh=").append(rzxh);
		return Encrypt.encodeUrl(bf.toString());
	}
	
	public static void main(String[] args){
		try {
			new AutoTimeSheetTask(new AutoTimeSheetProcessor(new AutoTimeSheetFrame())).doInBackground();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
