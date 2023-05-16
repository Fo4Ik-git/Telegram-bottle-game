package com.fo4ik.bot.core.commands.group;

import com.fo4ik.bot.Bottle;
import com.fo4ik.bot.core.Msg;
import com.fo4ik.entities.Task;
import com.fo4ik.entities.User;
import com.fo4ik.entities.service.DatabaseUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class StartGame extends Bottle {

    public StartGame(Update update) {
        new Msg("Игра началась", update);
        DatabaseUtil.setArrayTasks();
        System.out.println(DatabaseUtil.tasks.size());
        addKeyboard(update);
    }

    private void addKeyboard(Update update) {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("/nextPlayer"));
        keyboardRow.add(new KeyboardButton("/help"));
        keyboardRow.add(new KeyboardButton("/players"));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(keyboardRow);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText("Выберите кнопку:");
        message.setReplyMarkup(replyKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void nextPlayer(Update update){
        if(StartGroup.groupMembers.size() == 0) {
            new Msg("Напиши /start", update);
        } else {
            List<User> users = StartGroup.groupMembers;
            User user = users.get(getRandomNumber(users.size()));
            new Msg("Следующий игрок: " + user.getName(), update);
            new Msg("Задание: " + getRandomTask(user), update);
        }
    }

    private static int getRandomNumber(int max){
        return (int) (Math.random() * (max + 1) + 0);
    }

    private static String getRandomTask(User user){
        return DatabaseUtil.getRandomTaskForAdult(user.isAdult());
    }


}
