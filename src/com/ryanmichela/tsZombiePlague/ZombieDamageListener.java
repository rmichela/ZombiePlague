package com.ryanmichela.tsZombiePlague;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

public class ZombieDamageListener extends EntityListener {

	private ZombieDamage damageTracker;
	
	public ZombieDamageListener(ZombieDamage damageTracker)
	{
		this.damageTracker = damageTracker;
	}
	
	@Override
	public void onEntityDamage(EntityDamageEvent event) {
		if(event instanceof EntityDamageByEntityEvent)
		{
			Entity damager = ((EntityDamageByEntityEvent)event).getDamager();
			Entity damagee = ((EntityDamageByEntityEvent)event).getEntity();
			if(damager instanceof Zombie && damagee instanceof Player)
			{
				// A player has been damaged by a zombie
				damageTracker.AddDamage((Player)damagee);
			}
		}
	}

	

}
