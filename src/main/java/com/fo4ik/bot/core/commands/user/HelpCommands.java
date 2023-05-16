package com.fo4ik.bot.core.commands.user;

public enum HelpCommands {
    START("/start", "Старт бота"),
    NEW_TASK("/newTask", "Создает новую задачу"),
    ADULT("/adult true/false", "Устанавливает режим для взрослых"),
    EDIT_TASK("/editTask 'id'", "Позволяет редактировать задачу"),
    DELETE_TASK("/deleteTask 'id'", "Удаляет задачу"),
    HELP("/help", "Показывает список команд");

    private final String command;
    private final String description;

    HelpCommands(String command, String description) {
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
        for (HelpCommands help : values()) {
            sb.append(help.getCommand()).append(" - ").append(help.getDescription()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
