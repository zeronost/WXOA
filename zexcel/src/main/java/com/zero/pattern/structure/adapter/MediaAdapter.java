package com.zero.pattern.structure.adapter;

public class MediaAdapter implements MediaPlayer{
	
	AdvancedMediaPlayer advancedMediaPlayer;
	
	
	public MediaAdapter(String type){
		if("mp4".equalsIgnoreCase(type)){
			advancedMediaPlayer = new Mp4Player();
		}
		else if("avi".equalsIgnoreCase(type)){
			advancedMediaPlayer = new AviPlayer();
		}
		else if("mkv".equalsIgnoreCase(type)){
			advancedMediaPlayer = new MkvPlayer();
		}
	}


	public void play(String type, String file) {
		if("mp4".equalsIgnoreCase(type)){
			advancedMediaPlayer.playMp4(file);
		}
		else if("avi".equalsIgnoreCase(type)){
			advancedMediaPlayer.playAvi(file);
		}
		else if("mkv".equalsIgnoreCase(type)){
			advancedMediaPlayer.playMkv(file);
		}
	}
}
