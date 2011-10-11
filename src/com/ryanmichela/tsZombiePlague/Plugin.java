package com.ryanmichela.tsZombiePlague;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

	
	
	private ZombieDamage damageTracker;

	@Override
	public void onEnable() {
		// create the plugin directory if it does not exist
//		if(!getDataFolder().exists())
//		{
//			getDataFolder().mkdirs();
//		}

		getServer().getLogger().info("[Zombie Plague] Version " + getDescription().getVersion());
		
		// start the damage tracker
		damageTracker = new ZombieDamage(this);
		
		// register event listeners
		ZombieDamageListener zdl = new ZombieDamageListener(damageTracker);
		getServer().getPluginManager().registerEvent(Type.ENTITY_DAMAGE, zdl, Priority.Normal, this);
		
		PlayerDeathListener pdl = new PlayerDeathListener(damageTracker);
		getServer().getPluginManager().registerEvent(Type.ENTITY_DEATH, pdl, Priority.Normal, this);
		
		SugarEatListener cel = new SugarEatListener(damageTracker);
		getServer().getPluginManager().registerEvent(Type.PLAYER_INTERACT, cel, Priority.Normal, this);
	}
	
	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
	}

}
