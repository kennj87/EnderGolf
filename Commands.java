package endergolf.blockynights;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands extends Listeners implements CommandExecutor {
	public Map<Player, Location> playersingame = new HashMap<Player, Location>();
	public Map<Player, Location> players = new HashMap<Player, Location>();
	public Map<String, String> game = new HashMap<String, String>();
	
	Main main;
	GameCountdown gamecountdown;
	public Commands(Main main, GameCountdown gamecountdown) {
		this.main = main;
		this.gamecountdown = gamecountdown;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		// no arguments
		if (cmd.getName().equalsIgnoreCase("endergolf") || cmd.getName().equalsIgnoreCase("eg") && (sender instanceof Player) && (args.length == 0)) {
			sender.sendMessage("You need some arguments!");
		}
		// 1 argument
		if (cmd.getName().equalsIgnoreCase("endergolf") || cmd.getName().equalsIgnoreCase("eg") && (sender instanceof Player) && (args.length == 1)) {
			if (args[0].equalsIgnoreCase("join")) {
				if (game.get("status") != null && game.get("status").equalsIgnoreCase("on")) {
					if (players.get(player) != null && players.get(player) != player) {
					sender.sendMessage("You have joined a game");
					players.put(player,player.getLocation());
					} else { sender.sendMessage("You are already in the game"); }
				} else { sender.sendMessage("no game currently"); }
			}
		}
		// admin part
		if (cmd.getName().equalsIgnoreCase("endergolf") || cmd.getName().equalsIgnoreCase("eg") && (sender instanceof Player) && (sender.hasPermission("endergolf.admin"))) {
			if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
				if (game.get("status") == null || !game.get("status").equalsIgnoreCase("on")) {
					game.put("status","on");
					sender.sendMessage("Game started!");
				} else { sender.sendMessage("Already a game running"); }
			}
			if (args.length == 1 && args[0].equalsIgnoreCase("stop")) {
				if (game.get("status") == null || game.get("status").equalsIgnoreCase("on")) {
					game.put("status","off");
					sender.sendMessage("Game stopped!");
				} else { sender.sendMessage("No game currently running"); }
			}
			if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
				if (args[1].equalsIgnoreCase("start")) {
					sender.sendMessage("start 1 set");
					int x = player.getLocation().getBlockX();
					int y = player.getLocation().getBlockY();
					int z = player.getLocation().getBlockZ();
					main.getConfig().set("Arena.start.hole"+args[2]+".x",x);
					main.getConfig().set("Arena.start.hole"+args[2]+".y",y);
					main.getConfig().set("Arena.start.hole"+args[2]+".z",z);
					main.getConfig().set("Arena.start.hole"+args[2]+".world",player.getWorld().getName());
					main.saveConfig();
				}
				if (args[1].equalsIgnoreCase("stop")) {
					sender.sendMessage("stop 1 set");
					int x = player.getLocation().getBlockX();
					int y = player.getLocation().getBlockY();
					int z = player.getLocation().getBlockZ();
					main.getConfig().set("Arena.stop.hole"+args[2]+".x",x);
					main.getConfig().set("Arena.stop.hole"+args[2]+".y",y);
					main.getConfig().set("Arena.stop.hole"+args[2]+".z",z);
					main.getConfig().set("Arena.stop.hole"+args[2]+".world",player.getWorld().getName());
					main.saveConfig();
				}
			}
		}
		return true;
	}
}
