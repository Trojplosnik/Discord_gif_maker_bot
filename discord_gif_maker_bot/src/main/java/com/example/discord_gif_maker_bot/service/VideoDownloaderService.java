package com.example.discord_gif_maker_bot.service;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.request.RequestVideoStreamDownload;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoDownloaderService {
    public static byte[] VideoDownloader(String videoID) {
        YoutubeDownloader downloader = new YoutubeDownloader();

        RequestVideoInfo request = new RequestVideoInfo("D91DpmvesDI");
        Response<VideoInfo> response = downloader.getVideoInfo(request);
        VideoInfo video = response.data();

        Format format = video.bestVideoFormat();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        RequestVideoStreamDownload request_1 = new RequestVideoStreamDownload(format, os);
        var data = os.toByteArray();
        return data;
    }
}
