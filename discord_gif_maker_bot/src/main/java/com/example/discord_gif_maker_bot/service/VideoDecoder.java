package com.example.discord_gif_maker_bot.service;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class VideoDecoder {

    public FFmpegFrameGrabber read(File video) throws FrameGrabber.Exception {
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(video);
        frameGrabber.start();
        return frameGrabber;
    }
}
