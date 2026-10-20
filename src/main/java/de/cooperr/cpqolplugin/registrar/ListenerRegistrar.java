package de.cooperr.cpqolplugin.registrar;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ListenerRegistrar implements SmartInitializingSingleton {

    private final Logger logger;

    private final JavaPlugin plugin;
    private final PluginManager pluginManager;
    private final List<Listener> listeners;

    public ListenerRegistrar(
            Logger logger,
            JavaPlugin plugin,
            PluginManager pluginManager,
            List<Listener> listeners
    ) {
        this.logger = logger;

        this.plugin = plugin;
        this.pluginManager = pluginManager;
        this.listeners = listeners;
    }

    @Override
    public void afterSingletonsInstantiated() {
        for (var listener : listeners) {
            pluginManager.registerEvents(listener, plugin);
            logger.log(Level.FINE, "Registered listener class: {0}", listener.getClass().getSimpleName());
        }
        logger.log(Level.INFO, "Registered {0} listeners", listeners.size());
    }
}
