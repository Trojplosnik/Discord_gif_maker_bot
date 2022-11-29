package com.example.discord_gif_maker_bot.service;

import com.example.discord_gif_maker_bot.config.BotConfig;
import com.example.discord_gif_maker_bot.controller.UploadController;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config){
        this.config = config;
    }



    public void sendMsg(@NotNull Message userMessage, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userMessage.getChatId().toString());
        sendMessage.setReplyToMessageId(userMessage.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendAnimation(@NotNull Message userMessage, InputFile animation){
        SendAnimation sendAnimation = new SendAnimation();
        sendAnimation.setChatId(userMessage.getChatId().toString());
        sendAnimation.setReplyToMessageId(userMessage.getMessageId());
        sendAnimation.setAnimation(animation);
        try {
            execute(sendAnimation);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {


        Message userMessage = update.getMessage();
        if (update.hasMessage() && userMessage.hasText()) {
            switch (userMessage.getText()) {
                case "/start" -> sendMsg(userMessage, "Hello, " + userMessage.getChat().getUserName() + '!');
                case "/haruhi" -> sendMsg(userMessage, "https://www.youtube.com/watch?v=IhhHtKg3S20&list=WL&index=79");
                case "/help" -> sendMsg(userMessage, "I need help too(((");
                default -> {
                    String url = userMessage.getText();
                    if (url.indexOf("https://www.youtube.com/watch?v=") == 0 || url.indexOf("https://youtu.be/") == 0) {
                        sendMsg(userMessage, "Correct link");
                        IdExtractService idExtractService = new IdExtractService();
                        String videoID = idExtractService.extractVideoIdFromUrl(url);
                        var video = VideoDownloaderService.VideoDownloader(videoID);

//                        System.out.println(video.getPath());
//                        File gif = new File(video.getPath());
//                        UploadController uploadController = new UploadController();
//                        String gifPuth;
//                        try {
//                            gifPuth = uploadController.upload(gif, 0,10, 1, true);
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                        File gif2 = new File(gifPuth);
//                        InputFile animegif = new InputFile(gif2);
//                        sendAnimation(userMessage, animegif);
                    } else {
                        sendMsg(userMessage, "Wrong link");
                        UploadController uploadController = new UploadController();
                        String gifPuth;
//                        File gif = new File("C:\\Tasks\\Java\\Discord_gif_maker_bot\\" +
//                                "discord_gif_maker_bot\\downloads" +
//                                "uWQI-ZMV_Gsz.gif");
//                        try {
//                            gifPuth = uploadController.upload(gif, 0,10, 1, true);
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                        File gif2 = new File(gifPuth);
//                        InputFile animegif = new InputFile(gif2);
//                        sendAnimation(userMessage, animegif);
//                        File gif = new File("C:\\Tasks\\Java\\Discord_gif_maker_bot\\" +
//                                "discord_gif_maker_bot\\downloads" +
//                                "REJ.gif");
//                        InputFile animegif = new InputFile(gif);
//                        sendAnimation(userMessage, animegif);
                    }
                }
            }
        }
    }


    @Override
    public String getBotUsername() {
        return config.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }
}