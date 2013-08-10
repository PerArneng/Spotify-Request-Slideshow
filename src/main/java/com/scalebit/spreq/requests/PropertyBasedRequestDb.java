package com.scalebit.spreq.requests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertyBasedRequestDb implements RequestDb {

    private final Properties db;

    public PropertyBasedRequestDb(String propFile) throws IOException {
        this.db = new Properties();
        this.db.load(new FileInputStream(propFile));
    }

    @Override
    public String getRequester(String songId) {
        return this.db.getProperty(songId);
    }
}
