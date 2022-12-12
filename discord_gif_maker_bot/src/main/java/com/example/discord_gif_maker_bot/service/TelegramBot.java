package com.example.discord_gif_maker_bot.service;

import com.example.discord_gif_maker_bot.config.BotConfig;
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
                    if (url.indexOf("https://www.youtube.com/watch?v=") == 0 || url.indexOf("https://youtu.be/") == 0 || url.indexOf("https://m.youtube.com/watch?v=") == 0 ){
                        sendMsg(userMessage, "Correct link");
                        IdExtractService idExtractService = new IdExtractService();
                        String videoID = idExtractService.extractVideoIdFromUrl(url);
                        File video = VideoDownloaderService.VideoDownloader(videoID);
                        ConvertorToGif convertorToGif = new ConvertorToGif();
                        File gif = convertorToGif.toAnimatedGif(video, 0, 0, true);
                        InputFile animegif = new InputFile(gif);
                        sendAnimation(userMessage, animegif);
                        video.delete();
                        if(!gif.getName().equals("Error_Gif.gif"))
                            gif.delete();
                    } else {
                        sendMsg(userMessage, "Wrong link");
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