package endergolf.blockynights;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;

public class Listeners implements Listener {
	public Map<Player, Location> pearlstartlocation = new HashMap<Player, Location>();
	private static DecimalFormat df = new DecimalFormat(".000");
	
	Game games;
	public Listeners(Game games) {
		this.games = games;
	}
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
	    Projectile proj = event.getEntity();	    
        if (Game.gameinfo.get("currentplayer") != null) {
		    if (proj instanceof EnderPearl && Game.gameinfo.get("currentplayer").equalsIgnoreCase(((Player) proj.getShooter()).getDisplayName())) {
		        EnderPearl pearl = (EnderPearl)proj;
		        ProjectileSource source = pearl.getShooter();
		        if (source instanceof Player) {
		            Player player = (Player)source;
		            pearl.setPassenger(player);
		            pearlstartlocation.put(player,player.getLocation());
		        }
		    }
	    }
	}
	
	@EventHandler
	public void onProjectileHitEvent(ProjectileHitEvent event) {
	    Projectile proj = event.getEntity();
	    if (proj instanceof EnderPearl) {
	        EnderPearl pearl = (EnderPearl)proj;
	        ProjectileSource source = pearl.getShooter();
	        if (Game.gameinfo.get("currentplayer") != null) {
		        if (source instanceof Player && Game.gameinfo.get("currentplayer").equalsIgnoreCase(((Player) source).getDisplayName())) {
		        	Player player = (Player)source;
		        	Double distance = pearlstartlocation.get(player).distance(proj.getLocation());
		        	player.sendMessage("Distance: "+ df.format(distance)+" Meters!");
		        	int x = proj.getLocation().getBlockX();
		        	int y = proj.getLocation().getBlockY()-2;
		        	int z = proj.getLocation().getBlockZ();
		        	World world = proj.getWorld(); 
		        	int yy = safeTeleport(x,z,y,world);
		        	Double locy = (double) yy;
		        	Location loc = new Location(world,x,locy,z);
		        	player.teleport(loc);
		        	games.parChecker(player);
		        }
	        }
	    }
	}
	
	public int safeTeleport(int x,int z,int y, World world) {
		while (y < 255) {
			Location location = new Location(world,x,y,z);
			Block block1 = location.getBlock();
			Block block2 = location.add(0, 1, 0).getBlock();
			Block block3 = location.add(0, 2, 0).getBlock();
			if (block1.getType() != Material.AIR && block2.getType() == Material.AIR && block3.getType() == Material.AIR) { return y+1; }
			y++;
		}
		return y;
	}
}
