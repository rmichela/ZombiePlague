//    Copyright (C) 2011  Ryan Michela
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.ryanmichela.tsZombiePlague;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

	
	
	private ZombieDamage damageTracker;

	@Override
	public void onEnable() {
		// create the plugin directory if it does not exist
		if(!getDataFolder().exists())
		{
			try
			{
				getDataFolder().mkdirs();
				((YamlConfiguration)getConfig().getDefaults()).save(getDataFolder().getPath() + "/config.yml");
			}
			catch(IOException ex)
			{
				getServer().getLogger().log(Level.SEVERE, "[Zombie Plague] Failed to initialize configuration! Falling back to defaults.", ex);
			}
		}

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
