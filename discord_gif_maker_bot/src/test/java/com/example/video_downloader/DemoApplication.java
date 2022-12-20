package com.example.video_downloader;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.request.RequestVideoStreamDownload;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] argv) {
//        String[] cropCommands = {"cmd", "/k", "start", "ffmpeg", "-i", "discord_gif_maker_bot/downloads/1671474157111.gif", "-vf", "crop=",
//                "240",":", "270", "discord_gif_maker_bot/downloads/1671474366804.gif"};
        String cropCommands = "cmd /k start "+"ffmpeg -i " + "discord_gif_maker_bot/downloads/1671474157111.gif" +  " -vf crop=" + "240" + ":" + "270" + " " + "discord_gif_maker_bot/downloads/1671474366804.gif";
        try{
            Process crop = Runtime.getRuntime().exec(cropCommands);
            crop.waitFor(10, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
