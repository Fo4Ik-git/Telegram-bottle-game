package com.fo4ik.bot.core;

import com.fo4ik.bot.Bottle;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Msg extends Bottle {

    public Msg(String msg, Update update) {
        sendMessage(msg, update);
    }

    public Msg(String msg, boolean isReply, Update update){

    }

    private void sendMessage(String message, Update update){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(message);

        try{
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.getMessage();
        }
    }
}
