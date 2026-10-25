package de.cooperr.cpqolplugin.registrar;

import de.cooperr.cpqolplugin.command.PaperCommand;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandRegistrar implements SmartInitializingSingleton {

    private static final Logger log = LoggerFactory.getLogger(CommandRegistrar.class);

    private final JavaPlugin plugin;
    private final List<PaperCommand> commands;

    public CommandRegistrar(JavaPlugin plugin, List<PaperCommand> commands) {
        this.plugin = plugin;
        this.commands = commands;
    }

    @Override
    public void afterSingletonsInstantiated() {
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            var registrar = event.registrar();
            for (var command : commands) {
                command.register(registrar);
                log.debug("Registered command class: {}", command.getClass().getSimpleName());
            }
        });
        log.info("Registered {} commands", commands.size());
    }
}
