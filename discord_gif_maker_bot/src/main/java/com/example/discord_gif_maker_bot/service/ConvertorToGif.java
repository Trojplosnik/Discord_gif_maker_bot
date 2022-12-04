package com.example.discord_gif_maker_bot.service;


import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
public class ConvertorToGif{
    int maxSize = 8388600;
    int maxSize2 = 50388600;
    int sizeLimit = 104857600;
    File errorGif = new File("downloads/Error_Gif.gif");
    public File toAnimatedGif(File inputFile, int start, int duration) {
        String inputFilePath = inputFile.getPath();
        String durationStr;
        if (duration > 60)
            durationStr = "60";
        else
            durationStr = Integer.toString(duration);
        String startStr = Integer.toString(start);
        String location = "downloads/";
        String outputFilePath = location + System.currentTimeMillis() + ".gif";
        String ffmpegFlags = "fps=24,scale=480:-1:flags=lanczos,split[s0][s1];[s0]palettegen=" +
                "max_colors=255[p];[s1][p]paletteuse=dither=bayer";
        String[] conversionCommands = {"cmd", "/k", "start", "ffmpeg", "-ss", startStr, "-t", durationStr, "-i",
                inputFilePath, "-filter_complex", ffmpegFlags, outputFilePath };
        try {
            Process conversion = Runtime.getRuntime().exec(conversionCommands);
            conversion.waitFor(60, TimeUnit.SECONDS);
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
        if(gifSize < maxSize2){
            return new File(outputFilePath);
        }
        if(gifSize > sizeLimit){
            return errorGif;
        }
//        String[] betterConversionCommands = {"cmd", "/k", "start","ffmpeg", "-ss", startStr, "-t", durationStr, "-y", "-i",  inputFilePath, "-filter_complex", "scale=480:-1:flags=lanczos,split[s0][s1];[s0]palettegen=max_colors=255[p];[s1][p]paletteuse=dither=bayer", outputFilePath };
//        try {
//            Process betterConversion = Runtime.getRuntime().exec(betterConversionCommands);
//            betterConversion.waitFor(60, TimeUnit.SECONDS);
//        } catch (IOException | InterruptedException e) {
//
//            throw new RuntimeException(e);
//        }
//        long newGifSize = outputFile.length();
//        System.out.print(" ");
//        System.out.print(gifSize);
//        System.out.print(newGifSize);
//        System.out.print(" ");
//        if(newGifSize < maxSize2){
//            return new File(outputFilePath);
//        }



        long sizeCorrection = gifSize/maxSize + 1;
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
        return new File(outputSmallFilePath);
//        return errorGif;
    }
}