package com.fo4ik.bot.core.commands.group;

import com.fo4ik.bot.core.Msg;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpGroup {

    public HelpGroup(Update update) {
        new Msg("Для начала напиши /start, так ты добавишь всех пользователей как игроков. Вот тебе список всех комманд: " +
                HelpGroupCommands.getHelpMessage(), update);
    }
}
