package com.scalebit.spreq.slideshow;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

public class FolderPhotoProvider implements PhotoProvider {

    private final File folder;

    public FolderPhotoProvider(File folder) {
        this.folder = folder;
    }

    @Override
    public List<File> getPhotoFileList() {

        return Arrays.asList(this.folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".jpg") ||
                       pathname.getName().endsWith(".jpeg");
            }
        }));
    }
}
