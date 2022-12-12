package com.example.discord_gif_maker_bot.service;


import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
public class ConvertorToGif{
    private static final int SMALL_SIZE = 8388600;
    private static final int NORMAL_SIZE = 52420000;
    int sizeLimit = 0;
    File errorGif = new File("discord_gif_maker_bot/downloads/Error_Gif.gif");
    public File toAnimatedGif(File inputFile, int start, int duration, boolean smallSize) {
        String inputFilePath = inputFile.getPath();
        String durationStr;
        if (duration > 60 || duration == 0)
            durationStr = "60";
        else
            durationStr = Integer.toString(duration);
        if(smallSize){
             sizeLimit = SMALL_SIZE;
        }
        else {
            sizeLimit = NORMAL_SIZE;
        }
        String startStr = Integer.toString(start);
        String location = "discord_gif_maker_bot/downloads/";
        String outputFilePath = location + System.currentTimeMillis() + ".gif";
        String ffmpegFlags = "fps=16,scale=480:-1:flags=lanczos,split[s0][s1];[s0]palettegen=" +
                "max_colors=128[p];[s1][p]paletteuse=dither=bayer";
        String[] conversionCommands = {"cmd", "/k", "start", "ffmpeg", "-ss", startStr, "-t", durationStr, "-i",
                inputFilePath, "-filter_complex", ffmpegFlags, outputFilePath };
        try {
            Process conversion = Runtime.getRuntime().exec(conversionCommands);
            conversion.waitFor(100, TimeUnit.SECONDS);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        File outputFile = new File(outputFilePath);
        if(!outputFile.exists())
        {
            return errorGif;
        }
        long gifSize = outputFile.length();
        if(gifSize == 0)
        {
            return errorGif;
        }
        if(gifSize < sizeLimit){
            return new File(outputFilePath);
        }




        long sizeCorrection = gifSize/sizeLimit + 1;
        String sizeCorrStr = Long.toString(sizeCorrection);
        System.out.print(sizeCorrStr);
        String outputSmallFilePath = location + System.currentTimeMillis() + ".gif";
        String[] sizeCorrCommands = {"cmd", "/k", "start", "ffmpeg", "-i", outputFilePath, "-vf", "\"scale=iw/", sizeCorrStr, ":ih/", sizeCorrStr, "\"", outputSmallFilePath};
        try {
            Process compression = Runtime.getRuntime().exec(sizeCorrCommands);
            compression.waitFor(30, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        outputFile.delete();
        File outputSmallFile = new File(outputSmallFilePath);
        if(outputSmallFile.length() < sizeLimit){
            return outputSmallFile;
        }

        return errorGif;
    }
}