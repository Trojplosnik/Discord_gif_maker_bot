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
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication {

    private static String[] percentConvertor(String height, String weight){
        int I_height, I_weight;
        try{
            I_height = Integer.parseInt(height);
            I_weight = Integer.parseInt(weight);
        } catch (NumberFormatException e) {
            return null;
        }
        if(I_height > 99 || I_height < 10 || I_weight > 99 || I_weight < 10)
        {
            return null;
        }
        I_height = (int) (I_height * 2.7);
        I_weight = (int) (I_weight * 4.8);
        return new String[]{Integer.toString(I_height), Integer.toString(I_weight)};
    }
    public static void main(String[] argv) {
        Scanner in = new Scanner(System.in);
        while (true){
        String height = in.nextLine();
        String weight = in.nextLine();
        String h = Objects.requireNonNull(percentConvertor(height, weight))[0];
        String w = Objects.requireNonNull(percentConvertor(height, weight))[1];
        System.out.print(h);
        System.out.print(w);
        System.out.print("\n");
        }
    }
}
