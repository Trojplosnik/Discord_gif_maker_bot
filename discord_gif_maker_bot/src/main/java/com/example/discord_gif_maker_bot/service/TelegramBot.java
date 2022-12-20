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
import java.util.Arrays;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config){
        this.config = config;
    }
    final File errorGif = new File("discord_gif_maker_bot/downloads/Error_Gif.gif");

    private String[] percentConvertor(String height, String weight){
        int I_height, I_weight;
        try{
            I_height = Integer.parseInt(height);
            I_weight = Integer.parseInt(weight);
        } catch (NumberFormatException e) {
            return null;
        }
        if(I_height > 100 || I_height < 10 || I_weight > 100 || I_weight < 10)
        {
            return null;
        }
        I_height = (int) (I_height * 2.7);
        I_weight = (int) (I_weight * 4.8);
        return new String[]{Integer.toString(I_height), Integer.toString(I_weight)};
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
            System.out.print(userMessage.getChat().getUserName());
            switch (userMessage.getText()) {
                case "/start" -> sendMsg(userMessage, "Hello, " + userMessage.getChat().getUserName() + '!');
                case "/haruhi" -> sendMsg(userMessage, "https://www.youtube.com/watch?v=IhhHtKg3S20&list=WL&index=79");
                case "/help" -> sendMsg(userMessage, "I need help too(((");
                default -> {
                    String url = userMessage.getText();
                    List<String> items = Arrays.asList(url.split(" "));
                    if (url.indexOf("https://www.youtube.com/watch?v=") == 0 || url.indexOf("https://youtu.be/") == 0 || url.indexOf("https://m.youtube.com/watch?v=") == 0 ){
                        sendMsg(userMessage, "Correct link, work in progress");
                        IdExtractService idExtractService = new IdExtractService();
                        String videoID = idExtractService.extractVideoIdFromUrl(items.get(0));
                        File video = VideoDownloaderService.VideoDownloader(videoID);
                        ConvertorToGifService convertorToGif = new ConvertorToGifService();
                        File gif = null;
                        File cropGif = null;
                        if (items.size() == 1) {
                            gif = convertorToGif.toAnimatedGif(video, 0, 30);
                            cropGif = gif;
                        } else if (items.size() == 3) {
                            int starttime = TimeParseService.parseTime(items.get(1));
                            int endtime = TimeParseService.parseTime(items.get(2));
                            int durtime = endtime - starttime;

                            if (durtime > 30) {
                                sendMsg(userMessage, "duration more than 30 sec, we made 30sec gif");
                                gif = convertorToGif.toAnimatedGif(video, starttime, 30);
                                cropGif = gif;
                            } else {
                                gif = convertorToGif.toAnimatedGif(video, starttime, durtime);
                                cropGif = gif;
                            }
                        } else if (items.size() == 2) {
                            int starttime = TimeParseService.parseTime(items.get(1));
                            gif = convertorToGif.toAnimatedGif(video, starttime, 30);

                            cropGif = gif;
                        } else if (items.size() == 5) {
                            int starttime = TimeParseService.parseTime(items.get(1));
                            int endtime = TimeParseService.parseTime(items.get(2));
                            int durtime = endtime - starttime;
                            String height = items.get(3);
                            String weight = items.get(4);
                            gif = convertorToGif.toAnimatedGif(video, starttime, durtime);
                            String[] it = percentConvertor(height, weight);
//                            cropGif = new CropGif().crop(gif, height, weight);
                            if (it != null){
                                System.out.print(it[0]);
                                System.out.print(it[1]);
                                cropGif = new CropGif().crop(gif, it[0], it[1]);
                            } else {
                                cropGif = gif;
                            }
                        }
                        InputFile animegif = null;
                        if(cropGif == null){
                            animegif = new InputFile(errorGif);
                        }
                        else {
                            animegif = new InputFile(cropGif);
                        }
                        sendAnimation(userMessage, animegif);
                        video.delete();
                        cropGif.delete();
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