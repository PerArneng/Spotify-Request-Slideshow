package com.scalebit.spreq;

import com.scalebit.spreq.monitor.PlayerMonitor;
import com.scalebit.spreq.monitor.spotify.SpotifyLogFileMonitor;
import com.scalebit.spreq.requests.PropertyBasedRequestDb;
import com.scalebit.spreq.requests.RequestDb;
import com.scalebit.spreq.slideshow.FolderPhotoProvider;
import com.scalebit.spreq.slideshow.PhotoProvider;
import com.scalebit.spreq.ui.MainFrame;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Main  {

    private static Logger LOG = Logger.getLogger(Main.class.getName());

    static RequestDb wishDb = null;
    static PlayerMonitor monitor;


    public static void main(String[] args) {

        try {
            wishDb = new PropertyBasedRequestDb("sampledata/wishlist.properties");
        } catch (IOException e) {
            LOG.severe(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }

        monitor = new SpotifyLogFileMonitor(wishDb);

        LOG.info("Default Charset=" + Charset.defaultCharset());
        LOG.info("file.encoding=" + System.getProperty("file.encoding"));
        LOG.info("Default Charset=" + Charset.defaultCharset());
        LOG.info("Default Charset in Use=" + getDefaultCharSet());

        Map<String,String> properties = new HashMap<String, String>();
        properties.put("fileName", "scripts/spotify.log");

        PhotoProvider photoProvider = new FolderPhotoProvider(new File("photos"));

        MainFrame mainFrame = new MainFrame("Spreq", photoProvider);
        monitor.start(properties, mainFrame);

        mainFrame.setVisible(true);

        LOG.info("end of app");

    }

    private static String getDefaultCharSet() {
        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
        String enc = writer.getEncoding();
        return enc;
    }
}
