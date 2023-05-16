package com.fo4ik.bot.core.commands;

import com.fo4ik.bot.core.Msg;
import com.fo4ik.bot.core.commands.group.ConfigGroup;
import com.fo4ik.bot.core.commands.group.HelpGroup;
import com.fo4ik.bot.core.commands.group.StartGame;
import com.fo4ik.bot.core.commands.group.StartGroup;
import com.fo4ik.bot.core.commands.user.Help;
import com.fo4ik.bot.core.commands.user.NewTask;
import com.fo4ik.bot.core.commands.user.Start;
import com.fo4ik.entities.Task;
import com.fo4ik.entities.User;
import com.fo4ik.entities.service.DatabaseUtil;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

public class Commands {


    public static Map<String, String> commands = new HashMap<>();

    private String[] split;

    public void startUser(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            long id = message.getChatId();
            String text = message.getText();
            if (!commands.isEmpty()) {
                checkLastCommands(update);
            } else {

                split = text.split(" ");

                switch (split[0]) {
                    case "/start":
                        Start start = new Start(update);
                        break;
                    case "/help":
                        Help.showHelp(update);
                        break;
                    case "/newTask":
                        commands.put("/newTask", "new");
                        checkLastCommands(update);
                        break;
                    case "/adult":
                        adult(update, Boolean.parseBoolean(split[1]));
                        break;
                    case "/deleteTask": {
                        try {
                            DatabaseUtil.deleteTask(Long.parseLong(split[1]));
                            new Msg("Задача удалена", update);
                        } catch (Exception e) {
                            new Msg("Неверный формат команды", update);
                        }
                    } break;
                    case "/editTask": {
                        try {
                            Task task = DatabaseUtil.getTask(Long.parseLong(split[1]));
                            new Msg(task.getTask() + "\nFor Adult: " + task.isForAdult(), update);
                            commands.clear();
                            commands.put("/editTask", split[1]);
                            new Msg("Введите задачу", update);
                        } catch (Exception e) {
                            new Msg("Неверный формат команды", update);
                        }
                    } break;
                    default:
                        new Msg("I don't know this command, " + message.getFrom().getFirstName() + "!", update);
                        break;
                }
            }
        }
    }

    private void adult(Update update, boolean adult) {
        User user = DatabaseUtil.getUserById(update.getMessage().getFrom().getId());
        if (adult) {
            new Msg("Теперь ты совершеннолетний", update);
            user.setAdult(adult);
            DatabaseUtil.updateUser(user);

        } else {
            new Msg("Теперь ты несовершеннолетний", update);
            user.setAdult(adult);
            DatabaseUtil.updateUser(user);
        }
    }

    public void startGroup(Update update) {
        Message message = update.getMessage();
        long id = message.getChatId();
        String text = message.getText();

        split = text.split(" ");

        if (split[0].equals("/deleteTask")) {
            try {
                DatabaseUtil.deleteTask(Long.parseLong(split[1]));
                new Msg("Задача удалена", update);
            } catch (Exception e) {
                new Msg("Неверный формат команды", update);
            }
        } else {

            switch (message.getText()) {
                case "/start" -> {
                    StartGroup start = new StartGroup(update);
                }
                case "/startGame" -> {
                    StartGame startGame = new StartGame(update);
                }
                case "/players" -> {
                    ConfigGroup.printPlayers(update);
                }
                case "/help" -> {
                    HelpGroup helpGroup = new HelpGroup(update);
                }
                case "/nextPlayer" -> {
                    StartGame.nextPlayer(update);
                }
                case "/clearPlayers" -> {
                    ConfigGroup.clearPlayers(update);
                }
                case "/tasks" -> {
                    new Msg("Tasks: " + DatabaseUtil.tasks.size(), update);
                }
            }
        }
    }

    public void checkLastCommands(Update update) {
        for (Map.Entry<String, String> entry : commands.entrySet()) {
            String command = entry.getKey();
            String value = entry.getValue();

            if (!value.equals("old")) {
                switch (command) {
                    case "/editTask":
                    {
                        Task task = DatabaseUtil.getTask(Long.parseLong(commands.get("/editTask")));
                        task.setTask(update.getMessage().getText());
                        DatabaseUtil.updateTask(task);
                        new Msg("Задача изменена", update);

                    } break;
                    case "/newTask": {
                        new Msg("Введите задачу", update);
                        commands.put("/newTask", "old");
                        commands.put("/newTask task", "new");
                    }
                    break;
                    case "/newTask task": {
                        NewTask.Text(update.getMessage().getText(), update);
                        new Msg("Она для взрослых (18 +)?" +
                                "\nДа , Нет", update);
                        commands.put("/newTask task", "old");
                        commands.put("/newTask task adult", "new");
                    }
                    break;
                    case "/newTask task adult": {
                        if (update.getMessage().isUserMessage()) {
                            NewTask.isAdult(update.getMessage().getText(), update);
                        }
                    }
                }
            }
        }

    }

}
