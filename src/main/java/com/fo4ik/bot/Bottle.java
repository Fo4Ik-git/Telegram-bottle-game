package com.fo4ik.bot;

import com.fo4ik.bot.core.Msg;
import com.fo4ik.bot.core.commands.Commands;
import com.fo4ik.entities.service.DatabaseUtil;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bottle extends TelegramLongPollingBot {


    //TODO connect to groups
    static {
        // Initialize the database
        DatabaseUtil.init();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            if (update.getMessage().isSuperGroupMessage() || update.getMessage().isGroupMessage()) {
                Commands commands = new Commands();
                commands.startGroup(update);
            }
        }
        if (update.getMessage().isUserMessage()) {
                Commands commands = new Commands();
                commands.startUser(update);
        }
    }

    @Override
    public String getBotUsername() {
        return "Funny Fuck Bottle";
    }

    @Override
    public String getBotToken() {
        return "6267717172:AAFe7rXOQinpFzXwAbtX9dA4EF1kG7keuYw";
    }
}
