package com.example.discord_gif_maker_bot.service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CropGif {
    String location = "discord_gif_maker_bot/downloads/";

    public File crop(File inputFile, String height, String weight){
        if(!inputFile.exists())
        {
            return null;
        }
        String inputFilePath = inputFile.getPath();
        String outputFilePath = location + System.currentTimeMillis() + ".gif";
//        String[] cropCommands = {"cmd", "/k", "start", "ffmpeg", "-i", inputFilePath, "-vf", "crop=",
//                weight,":", height, outputFilePath };
        String cropCommands = "cmd /k start "+"ffmpeg -i " + inputFilePath +  " -vf crop=" + weight + ":" + height + " " + outputFilePath;
        try{
            Process crop = Runtime.getRuntime().exec(cropCommands);
            crop.waitFor(30, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        File outputFile = new File(outputFilePath);
        if(outputFile.exists())
        {
            return outputFile;
        }
        return inputFile;
    }
}
