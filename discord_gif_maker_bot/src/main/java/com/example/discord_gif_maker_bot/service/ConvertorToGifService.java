package com.example.discord_gif_maker_bot.service;


import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class ConvertorToGifService {
    private static final int SIZE_LIMIT = 52420000;
    String location = "discord_gif_maker_bot/downloads/";
    public File toAnimatedGif(File inputFile, int start, int duration) {
        String inputFilePath = inputFile.getPath();
        String durationStr;
        if (duration > 60 || duration == 0)
            durationStr = "60";
        else
            durationStr = Integer.toString(duration);
        String startStr = Integer.toString(start);
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
            return null;
        }
        long gifSize = outputFile.length();
        if(gifSize == 0)
        {
            return null;
        }
        if(gifSize < SIZE_LIMIT){
            return new File(outputFilePath);
        }

        return null;
    }
}