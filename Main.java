package endergolf.blockynights;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
	
	public void onEnable()  {
		getServer().getPluginManager().registerEvents(new Listeners(new Game(null,null)), this);
		getCommand("eg").setExecutor(new Commands(this,new GameInit(this, null,new ScoreBoard(null,null),new Game(this,null)),new ScoreBoard(null,null),new Game(this,null)));
		getCommand("endergolf").setExecutor(new Commands(this,new GameInit(this, null,new ScoreBoard(null,null),new Game(this,null)),new ScoreBoard(null,null), new Game(this,null)));
		this.saveDefaultConfig();
	}
	
	public void onDisable() {
	}
	
	public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
}
