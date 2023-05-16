package com.fo4ik.bot.core.commands.group;

import com.fo4ik.Main;
import com.fo4ik.bot.Bottle;
import com.fo4ik.bot.core.Msg;
import com.fo4ik.entities.User;
import com.fo4ik.entities.service.DatabaseUtil;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatAdministratorCustomTitle;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class StartGroup extends Bottle {

    public static List<User> groupMembers = new ArrayList<>();


    public StartGroup(Update update) {
        start(update);
    }

    public void start(Update update) {
        if (isBotAdmin(update)) {
            List<User> users = getAllGroupMembers(update);
            addKeyboard(update);
            new Msg("Привет, я бот для игры в бутылочку, для того чтобы начать игру введи команду /startGame", update);
        } else {
            new Msg("Дай мне права администратора и сделай администраторами всех других участников группы", update);
        }
    }




    public List<User> getAllGroupMembers(Update update) {
        try {
            GetChatAdministrators getChatAdministrators = new GetChatAdministrators();
            getChatAdministrators.setChatId(update.getMessage().getChatId().toString());
            List<ChatMember> administrators = execute(getChatAdministrators);

            // Перебор всех администраторов
            for (ChatMember chatMember : administrators) {
                if (chatMember.getStatus().equals("administrator") || chatMember.getStatus().equals("creator")) {
                    if (!chatMember.getUser().getIsBot()) {
                        groupMembers.add(checkUserFromDB(chatMember.getUser()));
                    }
                }
            }
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
        return groupMembers;
    }

    private User checkUserFromDB(org.telegram.telegrambots.meta.api.objects.User userTG) {
        if (DatabaseUtil.getUserById(userTG.getId()) == null) {
            User user = new User();
            user.setId(userTG.getId());
            user.setName(userTG.getFirstName());
            user.setAdult(false);
            DatabaseUtil.saveUser(user);
            return user;
        } else {
            return DatabaseUtil.getUserById(userTG.getId());
        }
    }

    private void addKeyboard(Update update) {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("/startGame"));
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

    public boolean isBotAdmin(Update update) {
        boolean isAdmin = false;
        Chat chatInfo = new Chat();
        chatInfo.setId(update.getMessage().getChatId());
        try {
            GetChatAdministrators getChatAdministrators = new GetChatAdministrators();
            getChatAdministrators.setChatId(chatInfo.getId().toString());
            List<ChatMember> administrators = execute(getChatAdministrators);

            for (ChatMember administrator : administrators) {
                if (administrator.getUser().getId().equals(Main.botUser.getId())) {
                    isAdmin = true;
                }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return isAdmin;
    }
}
