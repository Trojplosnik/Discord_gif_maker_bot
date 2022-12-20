package com.example.discord_gif_maker_bot.service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class JackalService {
    private static final int SIZE_LIMIT = 8388600;
    String location = "discord_gif_maker_bot/downloads/";
    File jackaling(File inputFile){
        long gifSize = inputFile.length();
        if (gifSize < SIZE_LIMIT){
            return inputFile;
        }
        long sizeCorrection = gifSize/SIZE_LIMIT + 1;
        String sizeCorrStr = Long.toString(sizeCorrection);
        System.out.print(sizeCorrStr);
        String outputFilePath = location + System.currentTimeMillis() + ".gif";
        String[] sizeCorrCommands = {"cmd", "/k", "start", "ffmpeg", "-i", inputFile.getPath(), "-vf", "\"scale=iw/", sizeCorrStr, ":ih/", sizeCorrStr, "\"", outputFilePath};
        try {
            Process compression = Runtime.getRuntime().exec(sizeCorrCommands);
            compression.waitFor(30, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        File outputFile = new File(outputFilePath);
        if(outputFile.length() < SIZE_LIMIT){
            return outputFile;
        }

        return null;
    }
}
