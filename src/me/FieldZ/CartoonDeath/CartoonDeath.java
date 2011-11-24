package me.FieldZ.CartoonDeath;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CartoonDeath extends JavaPlugin{
	
	Logger log = Logger.getLogger("Minecraft");
	
	FileConfiguration config;

	private final CartoonDeathEntityListener entityListener = new CartoonDeathEntityListener(this);

	@Override
	public void onDisable() {
		log.info("CartoonDeaths disabled");
	}

	@Override
	public void onEnable() {
		log.info("CartoonDeaths enabled");
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener , Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Normal, this);
		
		setupConfig();
		
	}
	
	public void setupConfig(){
		
		config = this.getConfig();
		
		config.addDefault("fire-sprinting-allowed", true);
		config.addDefault("lava-jump-allowed", true);
		config.addDefault("lightning-jump", true);
		config.addDefault("block-explosion-knock-back", true);
		config.addDefault("fall-explosion", true);
		config.addDefault("cactus-knock-back", true);
		config.addDefault("projectile-jump", true);
		
		config.options().copyDefaults(true);
        saveConfig();
		
	}

}
