package com.example.discord_gif_maker_bot.controller;

import com.example.discord_gif_maker_bot.service.ConvertorToGif;
import com.example.discord_gif_maker_bot.service.GifEncoder;
import com.example.discord_gif_maker_bot.service.VideoDecoder;
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;


public class UploadController {


    private final ConvertorToGif converterService = new ConvertorToGif();


    private final GifEncoder gifEncoderService = new GifEncoder();


    private final VideoDecoder videoDecoderService = new VideoDecoder();

    public String upload(File videoFile,
                         int start,
                         int end,
                          int speed,
                         boolean repeat) throws IOException, FrameGrabber.Exception {
        String location = "C:\\Users\\A\\Pictures\\";
//        File videoFile = new File(location + "/" + System
//                .currentTimeMillis() + ".mp4");
//        file.transferTo(videoFile);


        Path output = Paths.get(location + System.currentTimeMillis() + ".gif");

        FFmpegFrameGrabber frameGrabber = videoDecoderService.read(videoFile);
        AnimatedGifEncoder gifEncoder = gifEncoderService.getGifEncoder(repeat,
                (float) frameGrabber.getFrameRate(), output);
        converterService.toAnimatedGif(frameGrabber, gifEncoder, start, end, speed);


        return output.toString();
    }
}
