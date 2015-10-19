package endergolf.blockynights;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Game {
	static public Map<String, Integer> status = new HashMap<String, Integer>();
	static public Map<String, String> gameinfo = new HashMap<String, String>();
	
	Commands commands;
	Main main;
	public Game(Main main, Commands commands)  {
	
		this.main = main;
		this.commands = commands;
	}
	
	public void gameStart(String playername) {
	status.put("hole",1);
	status.put("par", 1);
	status.put("totalholes", main.getConfig().getConfigurationSection("Arena.start").getKeys(false).size());
	gameinfo.put("currentplayer",playername);
	System.out.print(status.get("totalholes"));
	}
	
	public void parChecker(Player player) {
		if (status.get("par") == 5) { System.out.print("new player");player.sendMessage("next player"); }
		else {
			status.put("par", status.get("par") +1);
			player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL,1));
			player.sendMessage("next shot");
		}
	}
}
