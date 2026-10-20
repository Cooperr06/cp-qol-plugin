package de.cooperr.cpqolplugin.registrar;

import de.cooperr.cpqolplugin.command.PaperCommand;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CommandRegistrar implements SmartInitializingSingleton {

    private final Logger logger;

    private final JavaPlugin plugin;
    private final List<PaperCommand> commands;

    public CommandRegistrar(Logger logger, JavaPlugin plugin, List<PaperCommand> commands) {
        this.logger = logger;

        this.plugin = plugin;
        this.commands = commands;
    }

    @Override
    public void afterSingletonsInstantiated() {
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            var registrar = event.registrar();
            for (var command : commands) {
                command.register(registrar);
                logger.log(Level.FINE, "Registered command class: {0}", command.getClass().getSimpleName());
            }
        });
        logger.log(Level.INFO, "Registered {0} commands", commands.size());
    }
}
