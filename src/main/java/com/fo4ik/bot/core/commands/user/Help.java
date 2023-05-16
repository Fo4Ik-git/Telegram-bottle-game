package com.fo4ik.bot.core.commands.user;

import com.fo4ik.bot.core.Msg;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Help {

    public static void showHelp(Update update){
        StringBuffer stringBuffer = new StringBuffer();
        for (HelpCommands help : HelpCommands.values()) {
            stringBuffer.append(help.getCommand() + " - " + help.getDescription() + "\n");
        }

        new Msg(stringBuffer.toString(), update);
    }

}
