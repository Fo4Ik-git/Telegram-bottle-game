package com.fo4ik;

import com.fo4ik.bot.Bottle;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    public static User botUser;
    public static void main(String[] args) {
        try{
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bottle());
            TelegramLongPollingBot bot = new Bottle();
            botUser = bot.getMe();
        } catch (TelegramApiException e){
            System.out.println(e.getMessage());
        }
    }
}