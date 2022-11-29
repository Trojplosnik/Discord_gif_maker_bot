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
import java.io.OutputStream;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] argv) {
        YoutubeDownloader downloader = new YoutubeDownloader();

        RequestVideoInfo request = new RequestVideoInfo("D91DpmvesDI");
        Response<VideoInfo> response = downloader.getVideoInfo(request);
        VideoInfo video = response.data();

        Format format = video.bestVideoFormat();

        OutputStream os = new ByteArrayOutputStream();
        RequestVideoStreamDownload request_1 = new RequestVideoStreamDownload(format, os);
        System.out.println(os);
    }
}
