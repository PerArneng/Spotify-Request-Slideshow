package com.scalebit.spreq;


import java.io.File;

public class ProgramArguments {

    private final File photoFolder;
    private final File logFile;
    private final File requesterFile;

    public ProgramArguments(File photoFolder, File logFile, File requesterFile) {
        this.photoFolder = photoFolder;
        this.logFile = logFile;
        this.requesterFile = requesterFile;
    }

    public File getPhotoFolder() {
        return photoFolder;
    }

    public File getLogFile() {
        return logFile;
    }

    public File getRequesterFile() {
        return requesterFile;
    }

    public static ProgramArguments parse(String[] args) {

        if (args.length != 3) {
            throw new IllegalArgumentException("missing arguments");
        }

        ProgramArguments arguments = new ProgramArguments(
                new File(args[0]), new File(args[1]), new File(args[2])
        );

        if (!arguments.getLogFile().exists()) {
            throw new IllegalArgumentException("spotify log file does not exist: " + arguments.getLogFile());
        }

        if (!arguments.getPhotoFolder().exists()) {
            throw new IllegalArgumentException("photo folder does not exist: " + arguments.getPhotoFolder());
        }

        if (!arguments.getRequesterFile().exists()) {
            throw new IllegalArgumentException("requester file does not exist" + arguments.getRequesterFile());
        }

        return arguments;
    }

}
