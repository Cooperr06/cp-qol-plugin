package de.cooperr.cpqolplugin.spring.bootstrap;

import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContextBootstrapper {

    private final JavaPlugin plugin;

    private AnnotationConfigApplicationContext context;

    public SpringContextBootstrapper(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize(Class<?>... configurationClasses) {
        var pluginClassLoader = plugin.getClass().getClassLoader();

        var currentThread = Thread.currentThread();
        var originalContextClassLoader = currentThread.getContextClassLoader();
        currentThread.setContextClassLoader(pluginClassLoader);

        try {
            context = new AnnotationConfigApplicationContext();
            context.setClassLoader(pluginClassLoader);

            context.register(configurationClasses);
            context.refresh();
        } finally {
            currentThread.setContextClassLoader(originalContextClassLoader);
        }
    }

    public void shutdown() {
        if (context != null && context.isActive()) {
            context.close();
        }
    }
}
