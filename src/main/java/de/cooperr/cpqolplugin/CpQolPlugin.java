package de.cooperr.cpqolplugin;

import de.cooperr.cpqolplugin.spring.bootstrap.SpringContextBootstrapper;
import de.cooperr.cpqolplugin.spring.config.PaperInfrastructureConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class CpQolPlugin extends JavaPlugin {

    private SpringContextBootstrapper springBootstrapper;

    @Override
    public void onEnable() {
        springBootstrapper = new SpringContextBootstrapper(this);
        springBootstrapper.initialize(PaperInfrastructureConfig.class);
    }

    @Override
    public void onDisable() {
        if (springBootstrapper != null) {
            springBootstrapper.shutdown();
        }
    }
}
