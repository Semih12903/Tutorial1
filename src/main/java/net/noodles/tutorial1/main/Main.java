package net.noodles.tutorial1.main;

import me.tigerhix.lib.scoreboard.ScoreboardLib;
import net.noodles.tutorial1.main.NPC.NPCManager;
import net.noodles.tutorial1.main.commands.TutorialCommand;
import net.noodles.tutorial1.main.commands.FlyCommand;
import net.noodles.tutorial1.main.events.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class Main extends JavaPlugin {


    private static NPCManager npcManager;
    private static Main Landmines;
    public static Main plugin;
    public ArrayList<Block> mines;


    public Main() {
        this.mines = new ArrayList<Block>();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Logger.log(Logger.LogLevel.OUTLINE, "*********************");
        Logger.log(Logger.LogLevel.INFO, "Plugin is loading...");
        Logger.log(Logger.LogLevel.INFO, "Commands are loading...");
        registerCommands();
        Logger.log(Logger.LogLevel.INFO, "Commands are loaded!");
        Logger.log(Logger.LogLevel.INFO, "Events are loading...");
        registerEvents();
        Logger.log(Logger.LogLevel.INFO, "Events are loaded!");
        Logger.log(Logger.LogLevel.INFO, "Managers are loading...");
        ScoreboardLib.setPluginInstance(this);
        this.npcManager = new NPCManager(this);
        Main.Landmines = this;
        plugin = this;
        Logger.log(Logger.LogLevel.INFO, "Managers are loaded!");
        Logger.log(Logger.LogLevel.INFO, "Config are loading...");
        createFiles();
        Logger.log(Logger.LogLevel.INFO, "Config is loaded!");
        Logger.log(Logger.LogLevel.SUCCESS, "The plugin has loaded correctly!");
        Logger.log(Logger.LogLevel.OUTLINE, "*********************");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    public void registerCommands() {
        getCommand("tutorial").setExecutor(new TutorialCommand());
        getCommand("fly").setExecutor(new FlyCommand());

    }

    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new Events(), this);
        pm.registerEvents(new JoinScoreboardEvent(), this);
        pm.registerEvents(new LandMines(), this);
        pm.registerEvents(new VIPLogin(), this);
        pm.registerEvents(new MobHeads(), this);
        pm.registerEvents(new HorseChange(), this);

    }

    public static Main getLandmines() {
        return Main.Landmines;
    }

    private File configf;
    private FileConfiguration config;

    private void createFiles() {
        configf = new File(getDataFolder(), "config.yml");

        if(!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        config = new YamlConfiguration();

        try {
            config.load(configf);

        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }


    }





}
