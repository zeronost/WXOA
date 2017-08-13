package com.zero.zexcel.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;;

public class Encrypt {
	
	
	public static String MD5(String s, int offset) throws NoSuchAlgorithmException{
		if(null == s || s.isEmpty() || offset == 0)
			return s;
		for(int i = 0; i < offset; i++)
			s = DigestUtils.md5Hex(s).toUpperCase();
		return s;
	}
	
	public static String encodeUrl(String url) throws UnsupportedEncodingException{
		return URLEncoder.encode(url, null);
	}

}
