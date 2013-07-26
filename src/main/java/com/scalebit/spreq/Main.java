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
import java.util.HashMap;
import java.util.Map;

public class Main  {


    static PlayerMonitor monitor = new SpotifyLogFileMonitor();


    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame("Spreq");
        mainFrame.setVisible(true);

        GraphicsDevice myDevice =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        try {
            //    myDevice.setFullScreenWindow(mainFrame);
        } finally {
            //myDevice.setFullScreenWindow(null);
        }

        Map<String,String> properties = new HashMap<String, String>();
        properties.put("fileName", "scripts/spotify.log");
        monitor.start(properties, mainFrame);

    }
}
