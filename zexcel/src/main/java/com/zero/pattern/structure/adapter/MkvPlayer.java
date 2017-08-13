package com.zero.pattern.structure.adapter;

public class MkvPlayer implements AdvancedMediaPlayer{

	public void playMp4(String file) {
		// TODO Auto-generated method stub
	}

	public void playAvi(String file) {
		// TODO Auto-generated method stub
	}

	public void playMkv(String file) {
		System.out.println("MkvPlayer playing mkv file < " + file +" >");
	}

}
