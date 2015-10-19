package endergolf.blockynights;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GameInit {
	public Map<String, Integer> countdown = new HashMap<String, Integer>();
	
	Main main;
	Commands commands;
	ScoreBoard scoreboard;
	Game game;
	public GameInit(Main main, Commands commands,ScoreBoard scoreboard,Game game) {
		this.main = main;
		this.commands = commands;
		this.scoreboard = scoreboard;
		this.game = game;
	}
	
	public void initGame(Player player) {
		Bukkit.getServer().broadcastMessage("A Game of EnderGolf has started! /eg join - to join the game");
		countdown.put("Start",30);
		countDown(30);
		scoreboard.joinBoard(player);
	}
	
	public void prepareGame() {
		Bukkit.getServer().broadcastMessage("Prepping game");
		Commands.game.put("countdown", "off");
		scoreboard.clearBoardAll();
		Object[] players = Commands.playername.keySet().toArray();	
		int playernumber = players.length;
		for (int i=0;i<playernumber;i++) {
			System.out.print(i);
			if (i == 0) { startGameFirstPlayer(players[i].toString());  }
			else { startGame(players[i].toString()); }
		}
	}
	public void startGameFirstPlayer(String p) {
			Player player =  Bukkit.getServer().getPlayer(p);
			player.setWalkSpeed(0);
			player.setFlySpeed(0);
			player.setAllowFlight(true);
			player.setFlying(true);
			player.setGameMode(GameMode.SURVIVAL);
			double x = main.getConfig().getDouble("Arena.start.hole1.x");
			double y = main.getConfig().getDouble("Arena.start.hole1.y");
			double z = main.getConfig().getDouble("Arena.start.hole1.z");
			World world = Bukkit.getServer().getWorld(main.getConfig().getString("Arena.start.hole1.world"));
			Location loc = new Location(world, x+0.5, y+1, z+0.5);
			player.teleport(loc);
			player.getInventory().clear();
			player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL,1));
			game.gameStart(p);
	}
	
	public void startGame(String p) {
		Player player =  Bukkit.getServer().getPlayer(p);
		player.setGameMode(GameMode.SPECTATOR);
		double x = main.getConfig().getDouble("Arena.start.hole1.x");
		double y = main.getConfig().getDouble("Arena.start.hole1.y");
		double z = main.getConfig().getDouble("Arena.start.hole1.z");
		World world = Bukkit.getServer().getWorld(main.getConfig().getString("Arena.start.hole1.world"));
		Location loc = new Location(world, x+0.5, y+10, z+0.5);
		player.teleport(loc);
		player.sendMessage("You are spectating!");
		player.getInventory().clear();
	}
	
	public void countDown(int time) {
		if (Commands.game.get("countdown") == null && Commands.game.get("countdown").equalsIgnoreCase("off")) { return; }
		int runtime = 0;
		int counttime = 0;
		if (countdown.get("Start") == 1) { counttime = 1; countdown.put("Start", 0); }
		if (countdown.get("Start") >= 2 && countdown.get("Start") <= 5) { runtime = 1*20; counttime = 1; countdown.put("Start",countdown.get("Start")-1); }
		if (time == 5) { runtime = 10*20; counttime = 1; countdown.put("Start",5); } 
		if (time == 20) { runtime = 10*20; counttime = 5; countdown.put("Start",10); } 
		if (time == 30) { runtime = 10*20; counttime = 20; countdown.put("Start",20); }
		final int finaltime = counttime;
		if (runtime == 0 && counttime == 0) { return; }
		new BukkitRunnable() {
	        @Override
	        public void run() {
	        	if (Commands.game.get("status") == null || Commands.game.get("status").equalsIgnoreCase("off")) { return; }
	        	if (countdown.get("Start") != 0) {
	        	Bukkit.getServer().broadcastMessage("Game starts in "+countdown.get("Start")+" seconds");
	        	countDown(finaltime);
	        	} else { Bukkit.getServer().broadcastMessage("Game starts now!"); prepareGame(); }
	        }
		}.runTaskLater(main, runtime);
	}
	public void teleportLobby(Player player) {
		double x = main.getConfig().getDouble("Arena.lobby1.x");
		double y = main.getConfig().getDouble("Arena.lobby1.y");
		double z = main.getConfig().getDouble("Arena.lobby1.z");
		World world = Bukkit.getServer().getWorld(main.getConfig().getString("Arena.lobby1.world"));
		Location loc = new Location(world, x+0.5, y, z+0.5);
		player.teleport(loc);
	}
	public void resetPlayers() {
		for (Player p : Commands.players.keySet()) {
			p.setWalkSpeed(0.2F);
			p.setFlySpeed(0.2F);
			p.setAllowFlight(false);
			p.setFlying(false);
			p.setGameMode(GameMode.SURVIVAL);
		}
		Commands.game.clear();
		Commands.players.clear();
		Commands.playername.clear();
		Game.gameinfo.clear();
		Game.status.clear();
	}
}
