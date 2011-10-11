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

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

public class PlayerDeathListener extends EntityListener {

	private ZombieDamage damageTracker;
	
	public PlayerDeathListener(ZombieDamage damageTracker)
	{
		this.damageTracker = damageTracker;
	}
	
	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			if(damageTracker.isPlayerInfected(player))
			{
				player.getWorld().spawnCreature(player.getLocation(), CreatureType.ZOMBIE);
			}
			damageTracker.ClearDamage(player);			
		}
	}

}
