package com.ryanmichela.tsZombiePlague;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class SugarEatListener extends PlayerListener {

	private ZombieDamage damageTracker;
	
	public SugarEatListener(ZombieDamage damageTracker)
	{
		this.damageTracker = damageTracker;
	}	
	
	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasBlock() && event.getClickedBlock().getType() == Material.CAKE_BLOCK)
		{
			damageTracker.reduceDamage(event.getPlayer());
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_AIR  && event.getItem().getType() == Material.COOKIE)
		{
			System.out.println("Ate a cookie");
			damageTracker.reduceDamage(event.getPlayer());
		}
	}

}
