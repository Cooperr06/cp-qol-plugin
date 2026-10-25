package de.cooperr.cpqolplugin.registrar;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListenerRegistrar implements SmartInitializingSingleton {

    private static final Logger log = LoggerFactory.getLogger(ListenerRegistrar.class);

    private final JavaPlugin plugin;
    private final PluginManager pluginManager;
    private final List<Listener> listeners;

    public ListenerRegistrar(
            JavaPlugin plugin,
            PluginManager pluginManager,
            List<Listener> listeners
    ) {
        this.plugin = plugin;
        this.pluginManager = pluginManager;
        this.listeners = listeners;
    }

    @Override
    public void afterSingletonsInstantiated() {
        for (var listener : listeners) {
            pluginManager.registerEvents(listener, plugin);
            log.debug("Registered listener class: {}", listener.getClass().getSimpleName());
        }
        log.info("Registered {} listeners", listeners.size());
    }
}
