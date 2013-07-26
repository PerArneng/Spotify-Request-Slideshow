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
            }

            @Override
            public void fileNotFound() {
            }

            @Override
            public void fileRotated() {
            }

            @Override
            public void handle(String line) {
                PlayerEvent event = parse(line);
                listener.onEvent(event);
            }

            @Override
            public void handle(Exception e) {
            }
        });

        this.worker = new Thread(tailer);
        this.worker.setName("Tailer(" + fileName + ")");
        this.worker.start();
    }

    public static PlayerEvent parse(String line) {
        System.out.println(line);
        String artist = "unknown";
        String title = "unknown";
        if (line.startsWith("playing") || line.startsWith("paused")) {
            String[] parts = line.split(";");
            title = parts[2];
            artist = parts[3];

        }
        return new PlayerEvent(artist, title);
    }
}
