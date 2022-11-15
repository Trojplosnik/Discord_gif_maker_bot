package com.example.video_downloader;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public void setVideoFormats(List<VideoFormat> videoFormats) { videoFormats.forEach(it -> {
        System.out.println(it.videoQuality() + " : " + it.url());
    });
    }

    public static void main(String[] args) {
        YoutubeDownloader downloader = new YoutubeDownloader();

        String videoId = "RdYsLC0GELo";

        RequestVideoInfo request = new RequestVideoInfo(videoId);
        Response<VideoInfo> response = downloader.getVideoInfo(request);
        VideoInfo video = response.data();

//        List<VideoFormat> videoFormats = video.videoFormats();

        File outputDir = new File("C:\\Users\\shiln\\IdeaProjects\\Discord_gif_maker_bot\\discord_gif_maker_bot\\downloads");
        Format format = video.bestVideoFormat();

        RequestVideoFileDownload request_video_download = new RequestVideoFileDownload(format)
                // optional params
                .saveTo(outputDir) // by default "videos" directory
                .renameTo("video_silence") // by default file name will be same as video title on youtube
                .overwriteIfExists(true); // if false and file with such name already exits sufix will be added video(1).mp4
        Response<File> response_video_download = downloader.downloadVideoFile(request_video_download);
        File data = response_video_download.data();
    }
}
