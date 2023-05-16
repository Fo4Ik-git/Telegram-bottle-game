package com.fo4ik.bot.core.commands.user;

import com.fo4ik.bot.core.Msg;
import com.fo4ik.bot.core.commands.Commands;
import com.fo4ik.entities.Task;
import com.fo4ik.entities.service.DatabaseUtil;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class NewTask {

    private Update update;

   public static Task task = new Task();

    public static void Text(String text, Update update) {
        task.setTask(text);
    }

    public static void isAdult(String text, Update update) {
        String lowercaseText = text.toLowerCase().replaceAll("\\s+", "");
        if (lowercaseText.equals("да")) {
            task.setForAdult(true);
        } else if (lowercaseText.equals("нет")) {
            task.setForAdult(false);
        } else {
            new Msg("Введите Да или Нет", update);
            Commands commands = new Commands();
            Update update1 = new Update();
            commands.checkLastCommands(update1);
        }
        Commands.commands.clear();
        DatabaseUtil.saveTask(task);
        new Msg("Задача добавлена", update);
    }

}


