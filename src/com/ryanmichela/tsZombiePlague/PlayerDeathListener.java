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
