package me.FieldZ.CartoonDeath;



import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.util.Vector;

public class CartoonDeathEntityListener extends EntityListener{
	
	CartoonDeath plugin;
	
	
	public CartoonDeathEntityListener(CartoonDeath instance){
		plugin = instance;
	}
	
	public void onEntityDamage(EntityDamageEvent ev){
		if(ev.getEntity() instanceof Player){
			final Player player = (Player) ev.getEntity();
			Location damageLoc = player.getLocation();
			World world = player.getWorld();
			if(ev.getCause().equals(DamageCause.FIRE_TICK) && plugin.config.getBoolean("fire-sprinting-allowed") == true){
				player.setSprinting(true);
			}
			if(ev.getCause().equals(DamageCause.LAVA) && plugin.config.getBoolean("lava-jump-allowed") == true){
				Vector direction = damageLoc.getDirection().multiply(-2);
				Vector vec = new Vector(direction.getX(), 1.25D, direction.getZ());
				player.setVelocity(vec);
				player.setFireTicks(100);
			}
			if(ev.getCause().equals(DamageCause.LIGHTNING) && plugin.config.getBoolean("lightning-jump") == true){
				Vector direction = damageLoc.getDirection().multiply(-1.1);
				Vector vec = new Vector(direction.getX(), 0.6D, direction.getZ());
				player.setVelocity(vec);
				if(!player.isDead()){
				plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
						public void run() {
							player.damage(20);
						}
					}, 10L);
				}
			}
			if(ev.getCause().equals(DamageCause.ENTITY_EXPLOSION) && plugin.getConfig().getBoolean("block-explosion-knock-back") == true) {
				player.setLastDamage(0);
				Vector direction = damageLoc.getDirection().multiply(-10);
				Vector vec = new Vector(direction.getX(), 0.5D, direction.getZ());
				player.setVelocity(vec);
				if(!player.isDead()){
					plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
						public void run(){
							player.damage(20);
						}
					}, 30L);
				}
			}
			if(ev.getCause().equals(DamageCause.FALL) && ev.getDamage() > 5 && plugin.config.getBoolean("fall-explosion") == true){
				world.createExplosion(damageLoc, 0);
			}
			if(ev.getCause().equals(DamageCause.CONTACT) && plugin.config.getBoolean("cactus-knock-back") == true){
				Vector direction = damageLoc.getDirection().multiply(-1.25);
				Vector vec = new Vector(direction.getX(), 0.5D, direction.getZ());
				player.setVelocity(vec);
				if(!player.isDead()){
					player.damage(3);
				}
			}
			if(ev.getCause().equals(DamageCause.PROJECTILE) && plugin.config.getBoolean("projectile-jump") == true){
				Vector vec = new Vector(0D, 0.5D, 0D);
				player.setVelocity(vec);
			}
		}
	}
	

}
