package com.fo4ik.bot.core.commands.group;

import com.fo4ik.bot.core.Msg;
import com.fo4ik.entities.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ConfigGroup {

    public static void printPlayers(Update update){
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : StartGroup.groupMembers) {
            boolean isAdult = user.isAdult();
            stringBuilder.append(user.getName()).append("  18+ : " + isAdult).append("\n");
        }

        new Msg(stringBuilder.toString(), update);
    }

    public static void clearPlayers(Update update){
        StartGroup.groupMembers.clear();
        new Msg("Список игроков очищен", update);
    }
}
