package com.scalebit.spreq.monitor;

public class PlayerEvent {

    private final String artist;
    private final String title;
    private final String requester;

    public PlayerEvent(String artist, String title, String requestor) {
        this.artist = artist;
        this.title = title;
        this.requester = requestor;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getRequester() {
        return requester;
    }
}
