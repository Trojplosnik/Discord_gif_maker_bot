package com.example.discord_gif_maker_bot.config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
public class BotConfig {

    @Value(value = "${bot.UserName}")
    String BotUserName;

    @Value(value = "${bot.Token}")
    String BotToken;
}
