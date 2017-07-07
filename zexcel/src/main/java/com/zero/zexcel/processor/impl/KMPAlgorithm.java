package com.zero.zexcel.processor.impl;
/**
 * @author bx91330
 * KMP algorithm demo
 */
public class KMPAlgorithm {

	static final int NA = -1;

	public static int index_kmp(CharSequence s, CharSequence t) {
		if (null == s || null == t || s.length() < t.length())
			return NA;
		if (s == t) {
			return 0;
		}
		int index = kmp(s, t);
		return index;
	}

	private static int kmp(CharSequence s, CharSequence t) {
		int i = 0, j = 0, x = s.length() - t.length();
		int[] next = getNext(t);
		while (i < s.length() && i <= x) {
			while (j < t.length()) {
				if (s.charAt(i + j) != t.charAt(j)) {
					j = 0;
					break;
				}
				j++;
			}
			if (j >= t.length()) {
				break;
			} else {
				i++;
				i += (j - next[j]);
			}
		}
		if (i > x)
			return NA;
		return i;
	}

	private static int[] getNext(CharSequence t) {
		int[] rt = new int[t.length()];
		rt[0] = 0;
		int k = 0;
		for (int i = 1; i < t.length(); i++) {
			while (k > 0 && t.charAt(i) != t.charAt(k))
				k = rt[k - 1];
			if (t.charAt(i) == t.charAt(k))
				k++;
			rt[i] = k;
		}
		return rt;
	}
	
	public static int index(CharSequence s, CharSequence t) {
		if (null == s || null == t || s.length() < t.length())
			return NA;
		if (s == t) {
			System.out.println("equals");
			return 0;
		}
		int index = normal(s, t);
		return index;
	}
	
	private static int normal(CharSequence s, CharSequence t) {
		int i = 0, j = 0, x = s.length() - t.length();
		while (i < s.length() && i <= x) {
			while (j < t.length()) {
				if (s.charAt(i + j) != t.charAt(j)) {
					j = 0;
					break;
				}
				j++;
			}
			if (j >= t.length()) {
				break;
			} else {
				i++;
			}
		}
		if (i > x)
			return NA;
		return i;
	}

	public static void main(String[] args) {
		String s = "ABCssadsadeasdsafsafeasdasdeafsafsafsadweasdadsadsadsadsadaswearasdsadsadweaeweqswadsadfsdafdeDEACDEFFxcFAAABCssadsadeasdsafsafeasdasdeafsafsafsadweasdadsadsadsadsadaswearasdsadsadweaeweqswadsadfsdafdeDEACDEFFxcFAA";
		String t = "sadsadaswearasdsadsadweaeweqswad";
		long c1 = System.currentTimeMillis();
		int index = index_kmp(s, t);
		long c2 = System.currentTimeMillis();
		System.out.println("================== " + index);
		System.out.println("****************** " + (c2 - c1));
		if (index > 0)
			System.out.println(s.substring(index, index + t.length()));
	}

}
