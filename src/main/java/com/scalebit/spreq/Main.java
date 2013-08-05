package com.scalebit.spreq;

import com.scalebit.spreq.monitor.PlayerEvent;
import com.scalebit.spreq.monitor.PlayerEventListener;
import com.scalebit.spreq.monitor.PlayerMonitor;
import com.scalebit.spreq.monitor.spotify.SpotifyLogFileMonitor;
import com.scalebit.spreq.ui.MainFrame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class Main  {


    static PlayerMonitor monitor = new SpotifyLogFileMonitor();


    public static void main(String[] args) {

        System.out.println("Default Charset=" + Charset.defaultCharset());
        System.out.println("file.encoding=" + System.getProperty("file.encoding"));
        System.out.println("Default Charset=" + Charset.defaultCharset());
        System.out.println("Default Charset in Use=" + getDefaultCharSet());

        Map<String,String> properties = new HashMap<String, String>();
        properties.put("fileName", "scripts/spotify.log");

        MainFrame mainFrame = new MainFrame("Spreq");
        monitor.start(properties, mainFrame);

        mainFrame.setVisible(true);

        GraphicsDevice myDevice =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        try {
            //    myDevice.setFullScreenWindow(mainFrame);
        } finally {
            //myDevice.setFullScreenWindow(null);
        }

         System.out.println("end of app");

    }

    private static String getDefaultCharSet() {
        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
        String enc = writer.getEncoding();
        return enc;
    }
}
