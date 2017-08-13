package com.zero.pattern.structure.adapter;

public class AudioPlayer implements MediaPlayer {
	
	MediaAdapter mediaAdapter;
	
	public void play(String type, String file) {
		if("mp3".equalsIgnoreCase(type)){
			System.out.println("AudioPlayer playing mp3 file < " + file +" >");
		}
		else if("mp4|avi|mkv".contains(type)){
			mediaAdapter = new MediaAdapter(type);
			mediaAdapter.play(type, file);
		}
		else{
			System.out.println("Invalid media type");
		}
	}

}
