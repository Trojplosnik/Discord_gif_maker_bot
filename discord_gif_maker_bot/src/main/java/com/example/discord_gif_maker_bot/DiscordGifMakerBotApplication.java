package com.example.discord_gif_maker_bot;

import com.example.discord_gif_maker_bot.service.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class DiscordGifMakerBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscordGifMakerBotApplication.class, args);

//		try {
//			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//			botsApi.registerBot(new TelegramBot());
//		} catch (TelegramApiException e) {
//			e.printStackTrace();
//		}
	}
}
