package com.scalebit.spreq;

import com.scalebit.spreq.monitor.PlayerEvent;
import com.scalebit.spreq.monitor.PlayerEventListener;
import com.scalebit.spreq.monitor.PlayerMonitor;
import com.scalebit.spreq.monitor.spotify.SpotifyLogFileMonitor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Main  {


    public static void main(String[] args) {

        PlayerMonitor monitor = new SpotifyLogFileMonitor();
        Map<String,String> properties = new HashMap<String, String>();
        properties.put("fileName", "scripts/spotify.log");
        monitor.start(properties, new PlayerEventListener() {
            @Override
            public void onEvent(PlayerEvent event) {
                System.out.println(event);
            }
        });

    }
}
