package com.scalebit.spreq.monitor;

public class PlayerEvent {

    private final String artist;
    private final String title;

    public PlayerEvent(String artist, String title) {
        this.artist = artist;
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }
}
