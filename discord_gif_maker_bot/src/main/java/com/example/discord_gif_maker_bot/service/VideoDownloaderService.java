package com.example.discord_gif_maker_bot.service;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoDownloaderService {
    public static File VideoDownloader(String videoID) {
        YoutubeDownloader downloader = new YoutubeDownloader();

        RequestVideoInfo request = new RequestVideoInfo(videoID);
        Response<VideoInfo> response = downloader.getVideoInfo(request);
        VideoInfo video = response.data();

//        List<VideoFormat> videoFormats = video.videoFormats();

        File outputDir = new File("discord_gif_maker_bot/downloads");
        Format format = video.bestVideoFormat();

        RequestVideoFileDownload request_video_download = new RequestVideoFileDownload(format)
                // optional params
                .saveTo(outputDir) // by default "videos" directory
                .renameTo(videoID) // by default file name will be same as video title on youtube
                .overwriteIfExists(true); // if false and file with such name already exits sufix will be added video(1).mp4
        Response<File> response_video_download = downloader.downloadVideoFile(request_video_download);
        File data = response_video_download.data();
        return data;
    }
}
