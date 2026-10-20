package de.cooperr.cpqolplugin.command;

import io.papermc.paper.command.brigadier.Commands;

public interface PaperCommand {

    void register(Commands registrar);
}
