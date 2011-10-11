package com.ryanmichela.tsZombiePlague;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.entity.Player;


public class ZombieDamage {
	
	private HashMap<String, Integer> damageCounters = new HashMap<String, Integer>();
	private Plugin plugin;
	
	public ZombieDamage(final Plugin plugin)
	{
		this.plugin = plugin;
			
		// Schedule the zombie damage tasks
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new DamageApplier() { @Override public Boolean ShouldApply(int damage) { return damage == 1; }}, 0, (plugin.getConfig().getInt("damageCycleSeconds") * 20));
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new DamageApplier() { @Override public Boolean ShouldApply(int damage) { return damage == 2; }}, 0, (plugin.getConfig().getInt("damageCycleSeconds") * 20) / 2);
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new DamageApplier() { @Override public Boolean ShouldApply(int damage) { return damage == 3; }}, 0, (plugin.getConfig().getInt("damageCycleSeconds") * 20) / 3);
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new DamageApplier() { @Override public Boolean ShouldApply(int damage) { return damage >= 4; }}, 0, (plugin.getConfig().getInt("damageCycleSeconds") * 20) / 4);
	}
	
	public void AddDamage(Player p)
	{
		String name = p.getName();
		if(!damageCounters.containsKey(name))
		{
			damageCounters.put(name, 0);
		}
		
		int damage = damageCounters.get(name);
		if(damage < plugin.getConfig().getInt("maxDamageTokens"))
		{
			damage++;
			damageCounters.put(name, damage);
		}		
		
		p.sendMessage(plugin.getConfig().getString("biteString"));
	}
	
	public void reduceDamage(Player p)
	{
		String name = p.getName();
		if(damageCounters.containsKey(name))
		{
			int damage = damageCounters.get(name);
			if(damage > 0)
			{
				damage--;
				damageCounters.put(name, damage);
				
				if(damage == 0)
				{
					p.sendMessage(plugin.getConfig().getString("cureString"));
				}
				else
				{
					p.sendMessage(plugin.getConfig().getString("healString"));
				}
			}
		}
	}
	
	public void ClearDamage(Player p)
	{
		String name = p.getName();
		damageCounters.put(name, 0);
	}
	
	public Boolean isPlayerInfected(Player p)
	{
		if(damageCounters.containsKey(p.getName()))
		{
			return damageCounters.get(p.getName()) > 0;
		}
		else
		{
			return false;
		}
	}
	
	private abstract class DamageApplier implements Runnable
	{
		@Override
		public void run() 
		{
			for(Entry<String, Integer> kvp : damageCounters.entrySet())
			{
				if(ShouldApply(kvp.getValue()))
				{
					Player player = plugin.getServer().getPlayer(kvp.getKey());
					player.damage(plugin.getConfig().getInt("damagePerTick"));
					player.sendMessage(plugin.getConfig().getString("harmString"));
				}
			}
		}	
		
		public abstract Boolean ShouldApply(int damage);
	}
}
