package com.fo4ik.bot.core.commands.user;

import com.fo4ik.bot.Bottle;
import com.fo4ik.bot.core.Msg;
import com.fo4ik.entities.User;
import com.fo4ik.entities.service.DatabaseUtil;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Start extends Bottle {

    public Start(Update update) {
        start(update);
    }

    public void start(Update update) {
        User user = new User();
        user.setId(update.getMessage().getChatId());
        user.setName(update.getMessage().getFrom().getFirstName());
        user.setAdult(false);

        if (DatabaseUtil.getUserById(user.getId()) == null) {
            DatabaseUtil.saveUser(user);
            new Msg("Hello, " + update.getMessage().getFrom().getFirstName() + "!\n" +
                    "User write to db", update);
        } else new Msg("Вы уже зарегистрированы", update);
    }

}
