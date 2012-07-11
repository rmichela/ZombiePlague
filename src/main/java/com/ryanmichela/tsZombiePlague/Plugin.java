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

import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

	
	
	private ZombieDamage damageTracker;

	@Override
	public void onEnable() {
		// create the plugin directory if it does not exist
		if(!getDataFolder().exists())
		{
            getDataFolder().mkdirs();
            saveDefaultConfig();
		}
		
		// start the damage tracker
		damageTracker = new ZombieDamage(this);
		
		// register event listeners
		ZombieDamageListener zdl = new ZombieDamageListener(damageTracker, this);
		getServer().getPluginManager().registerEvents(zdl, this);
		
		PlayerDeathListener pdl = new PlayerDeathListener(damageTracker);
		getServer().getPluginManager().registerEvents(pdl, this);
		
		SugarEatListener cel = new SugarEatListener(damageTracker);
		getServer().getPluginManager().registerEvents(cel, this);
	}
	
	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
	}

}
