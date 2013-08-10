package com.scalebit.spreq.monitor.spotify;


import com.scalebit.spreq.monitor.PlayerEvent;
import com.scalebit.spreq.monitor.PlayerEventListener;
import com.scalebit.spreq.monitor.PlayerMonitor;
import com.scalebit.spreq.requests.RequestDb;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

public class SpotifyLogFileMonitor implements PlayerMonitor {


    public static final int FILE_MONITOR_DELAY = 500; // milliseconds
    private static Logger LOG = Logger.getLogger(SpotifyLogFileMonitor.class.getName());
    private Thread worker;
    private final RequestDb requestDb;

    public SpotifyLogFileMonitor(RequestDb requestDb) {
        this.requestDb = requestDb;
    }

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
                LOG.fine("init: " + tailer);
            }

            @Override
            public void fileNotFound() {
                LOG.fine("file not found");
            }

            @Override
            public void fileRotated() {
                LOG.fine("file rotated");
            }

            @Override
            public void handle(String line) {
                LOG.fine("handle");
                PlayerEvent event = parse(line, requestDb);
                listener.onEvent(event);
            }

            @Override
            public void handle(Exception e) {
                LOG.fine("exception: " + e);
                e.printStackTrace();
            }
        }, FILE_MONITOR_DELAY);

        this.worker = new Thread(tailer);
        this.worker.setName("Tailer(" + fileName + ")");
        this.worker.start();
    }

    public static PlayerEvent parse(String line, RequestDb wishDb) {
        LOG.fine("parsing line: '" + line + "'");
        String artist = "unknown";
        String title = "unknown";
        String requester = "";
        if (line.startsWith("playing") || line.startsWith("paused")) {
            String[] parts = line.split("\\|");
            if (parts.length == 5) {
                title = parts[2];
                artist = parts[3];
                requester = wishDb.getRequester(parts[1].split(":")[2]);
            } else {
                LOG.info("err: length is " + parts.length);
            }

        }
        return new PlayerEvent(artist, title, requester);
    }
}
