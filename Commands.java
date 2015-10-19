package endergolf.blockynights;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	static public Map<Player, Location> players = new HashMap<Player, Location>();
	static public Map<String, Location> playername = new HashMap<String, Location>();
	static public Map<String, String> game = new HashMap<String, String>();
	
	Main main;
	GameInit gamecountdown;
	ScoreBoard scoreboard;
	Game games;
	public Commands(Main main, GameInit gamecountdown, ScoreBoard scoreboard,Game games) {
		this.main = main;
		this.gamecountdown = gamecountdown;
		this.scoreboard = scoreboard;
		this.games = games;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		// no arguments
		if (cmd.getName().equalsIgnoreCase("endergolf") || cmd.getName().equalsIgnoreCase("eg") && (sender instanceof Player) && (args.length == 0)) {
			sender.sendMessage("You need some arguments!");
		}
		// 1 argument
		// join - leave
		if (cmd.getName().equalsIgnoreCase("endergolf") || cmd.getName().equalsIgnoreCase("eg") && (sender instanceof Player) && (args.length == 1)) {
			if (args[0].equalsIgnoreCase("join")) {
				if (game.get("countdown") != null && game.get("countdown").equalsIgnoreCase("on")) {
					if (players.get(player) == null) {
					sender.sendMessage("You have joined a game");
					players.put(player,player.getLocation());
					playername.put(player.getDisplayName(),player.getLocation());
					scoreboard.updateBoard();
					gamecountdown.teleportLobby(player);
					} else { sender.sendMessage("You are already in the game"); }
				} else { sender.sendMessage("no signup currently running"); }
			}
			if (args[0].equalsIgnoreCase("leave")) {
				if (game.get("countdown") != null && game.get("countdown").equalsIgnoreCase("on")) {
					if (players.get(player) != null) {
					sender.sendMessage("You left the game");
					players.remove(player);
					playername.remove(player.getDisplayName());
					scoreboard.updateBoard();
					scoreboard.clearBoard(player);
					} else { sender.sendMessage("You are not in a game"); }
				} else { sender.sendMessage("no signup currently running"); }
			}
		}
		// admin part
		if (cmd.getName().equalsIgnoreCase("endergolf") || cmd.getName().equalsIgnoreCase("eg") && (sender instanceof Player) && (sender.hasPermission("endergolf.admin"))) {
			// start - stop
			if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
				if (game.get("status") == null || !game.get("status").equalsIgnoreCase("on")) {
					game.put("status","on");
					game.put("countdown", "on");
					sender.sendMessage("Game started!");
					gamecountdown.initGame(player);
					players.put(player,player.getLocation());
					playername.put(player.getDisplayName(),player.getLocation());
					gamecountdown.teleportLobby(player);
				} else { sender.sendMessage("Already a game running"); }
			}
			if (args.length == 1 && args[0].equalsIgnoreCase("stop")) {
				if (game.get("status") == null || game.get("status").equalsIgnoreCase("on")) {
					game.put("status","off");
					sender.sendMessage("Game stopped!");
					scoreboard.clearBoardAll();
					gamecountdown.resetPlayers();
				} else { sender.sendMessage("No game currently running"); }
			}
			// setup course
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
				if (args[1].equalsIgnoreCase("spawn")) {
					sender.sendMessage("spawn 1 set");
					int x = player.getLocation().getBlockX();
					int y = player.getLocation().getBlockY();
					int z = player.getLocation().getBlockZ();
					main.getConfig().set("Arena.spawn"+args[2]+".x",x);
					main.getConfig().set("Arena.spawn"+args[2]+".y",y);
					main.getConfig().set("Arena.spawn"+args[2]+".z",z);
					main.getConfig().set("Arena.spawn"+args[2]+".world",player.getWorld().getName());
					main.saveConfig();
				}
				if (args[1].equalsIgnoreCase("lobby")) {
					sender.sendMessage("lobby 1 set");
					int x = player.getLocation().getBlockX();
					int y = player.getLocation().getBlockY();
					int z = player.getLocation().getBlockZ();
					main.getConfig().set("Arena.lobby"+args[2]+".x",x);
					main.getConfig().set("Arena.lobby"+args[2]+".y",y);
					main.getConfig().set("Arena.lobby"+args[2]+".z",z);
					main.getConfig().set("Arena.lobby"+args[2]+".world",player.getWorld().getName());
					main.saveConfig();
				}
			}
		}
		return true;
	}
}
