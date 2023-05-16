package com.fo4ik.bot.core.commands.group;

import com.fo4ik.bot.core.commands.user.HelpCommands;

public enum HelpGroupCommands {

    START("/start", "Старт бота"),
    NEW_TASK("/players", "Показывает всех игроков"),
    CLEAR_PLAYERS("/clearPlayers", "Очищает список игроков"),
    DELETE_TASK("/deleteTask 'id'", "Удаляет задачу"),
    HELP("/help", "Показывает список команд");

    private final String command;
    private final String description;

    HelpGroupCommands(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public static String getHelpMessage() {
        StringBuilder sb = new StringBuilder();
        for (HelpGroupCommands help : values()) {
            sb.append(help.getCommand()).append(" - ").append(help.getDescription()).append(System.lineSeparator());
        }
        return sb.toString();
    }

}
