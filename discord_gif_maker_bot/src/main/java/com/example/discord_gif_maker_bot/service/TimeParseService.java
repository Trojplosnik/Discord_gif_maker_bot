package com.example.discord_gif_maker_bot.service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;

public class TimeParseService {

    public static int parseTime(String strtime){
//        int time;

//        LocalTime localTime = LocalTime.parse(strtime, DateTimeFormatter.ofPattern("mm:ss"));
//        int minutes = localTime.get(ChronoField.MINUTE_OF_HOUR);
//        int seconds = localTime.get(ChronoField.SECOND_OF_MINUTE);
//        time = minutes * 60 + seconds;
        return (int) parseDuration(strtime).toSeconds();
    }

    public static Duration parseDuration(String durStr) {
        String isoString = durStr.replaceFirst("^(\\d{1,2}):(\\d{2})$", "PT$1M$2S");
        return Duration.parse(isoString);
    }
}

