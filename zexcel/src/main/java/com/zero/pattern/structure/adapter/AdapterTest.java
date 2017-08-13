package com.zero.pattern.structure.adapter;

public class AdapterTest {
	
	public static void main(String[] args){
		testAdapter();
	}
	
	private static void testAdapter(){
		MediaPlayer player = new AudioPlayer();
		
		player.play("mp3", "It's my life.mp3");
		player.play("mp4", "Super nature s11.mp4");
		player.play("avi", "战狼2.avi");
		player.play("mkv", "Game of Thones s7.mkv");
		player.play("rmvb", "House of cards.rmvb");
	}
	
}
