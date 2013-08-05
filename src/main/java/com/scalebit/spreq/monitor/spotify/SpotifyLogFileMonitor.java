package com.scalebit.spreq.monitor.spotify;


import com.scalebit.spreq.monitor.PlayerEvent;
import com.scalebit.spreq.monitor.PlayerEventListener;
import com.scalebit.spreq.monitor.PlayerMonitor;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;

import java.io.File;
import java.util.Map;

public class SpotifyLogFileMonitor implements PlayerMonitor {

    private Thread worker;

    @Override
    public void start(Map<String, String> params, final PlayerEventListener listener) {

        String fileName = params.get("fileName");
        if (fileName == null) {
            throw new IllegalArgumentException("fileName property not found");
        }
        File file = new File(fileName);

        if (!file.exists()) {
            throw new IllegalArgumentException("'" + fileName + "' does not exist");
        }

        Tailer tailer = Tailer.create(file, new TailerListener() {
            @Override
            public void init(Tailer tailer) {
                System.out.println("init: " + tailer);
            }

            @Override
            public void fileNotFound() {
                System.out.println("file not found");
            }

            @Override
            public void fileRotated() {
                System.out.println("file rotated");
            }

            @Override
            public void handle(String line) {
                System.out.println("handle");
                PlayerEvent event = parse(line);
                listener.onEvent(event);
            }

            @Override
            public void handle(Exception e) {
                System.out.println("exception: " + e);
                e.printStackTrace();
            }
        });

        this.worker = new Thread(tailer);
        this.worker.setName("Tailer(" + fileName + ")");
        this.worker.start();
    }

    public static PlayerEvent parse(String line) {
        System.out.println("parsing line: '" + line + "'");
        String artist = "unknown";
        String title = "unknown";
        if (line.startsWith("playing") || line.startsWith("paused")) {
            String[] parts = line.split("\\|");
            if (parts.length == 5) {
                title = parts[2];
                artist = parts[3];
            } else {
                System.out.println("err: length is " + parts.length);
            }

        }
        return new PlayerEvent(artist, title);
    }
}
