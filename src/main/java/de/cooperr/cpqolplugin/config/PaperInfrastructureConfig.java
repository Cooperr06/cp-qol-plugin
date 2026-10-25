package de.cooperr.cpqolplugin.config;

import de.cooperr.cpqolplugin.CpQolPlugin;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "de.cooperr.cpqolplugin")
public class PaperInfrastructureConfig {

    @Bean
    public JavaPlugin javaPlugin() {
        return JavaPlugin.getPlugin(CpQolPlugin.class);
    }

    @Bean
    public Server server(JavaPlugin plugin) {
        return plugin.getServer();
    }

    @Bean
    public PluginManager pluginManager(Server server) {
        return server.getPluginManager();
    }

    @Bean
    public BukkitScheduler bukkitScheduler(Server server) {
        return server.getScheduler();
    }
}
