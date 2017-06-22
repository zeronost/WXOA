package com.zero.zexcel.util;

public enum SplitMethod {

	NUM(0, "Char Count"), PUN(1, "Punctuation");

	private int key;
	private String description;
	private static final String PUNTURATION = "[。；？！【】.;?!]";

	private static final int DEFAULT_OFFSET = 10;

	private SplitMethod(int key, String desc) {
		this.key = key;
		this.description = desc;
	}

	public int getKey() {
		return this.key;
	}

	public String getDescription() {
		return this.description;
	}

	public StringBuilder process(StringBuilder s, String k, int... offsets) {
		if (s == null || k == null || !s.toString().contains(k))
			return null;
		if (this.isNum()) {
			int offset = (offsets == null || offsets[0] <= 0) ? DEFAULT_OFFSET
					: offsets[0];
			return numProcess(s, k, offset);
		} else {
			return punProcess(s, k);
		}
	}

	private StringBuilder numProcess(StringBuilder s, String k, int offset) {
		StringBuilder bf = new StringBuilder();
		String[] splits = s.toString().split(k);
		for (int i = 1; i < splits.length; i++) {
			bf.append(i).append(". ").append(cut(splits[i - 1], offset, false))
					.append(k).append(cut(splits[i], offset, true))
					.append("\r\n");
		}
		return bf;
	}

	private String cut(String s, int offset, boolean ispre) {
		if (s == null || s.length() <= offset)
			return s;
		if (ispre)
			return s.substring(0, offset);
		else
			return s.substring(s.length() - offset + 1, s.length());
	}

	private StringBuilder punProcess(StringBuilder s, String k) {
		StringBuilder bf = new StringBuilder();
		String[] splits = s.toString().split(k);
		for (int i = 1; i < splits.length; i++) {
			bf.append(i).append(". ").append(cutByPun(splits[i - 1], false))
					.append(k).append(cutByPun(splits[i], true)).append("\r\n");
		}
		return bf;
	}

	private String cutByPun(String s, boolean ispre) {
		String[] sp = s.split(PUNTURATION);
		if (sp.length == 0)
			return ispre ? s :"";
		if (sp.length == 1)
			return cut(s, DEFAULT_OFFSET, ispre);
		if (ispre)
			return sp[0] + s.charAt(sp[0].length());
		else
			return sp[sp.length - 1];
	}

	public boolean isNum() {
		return this.equals(SplitMethod.NUM);
	}

	public boolean isPun() {
		return this.equals(SplitMethod.PUN);
	}
	
	public String toString(){
		return this.getDescription();
	}
}
