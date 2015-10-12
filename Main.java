package endergolf.blockynights;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
	
	public void onEnable()  {
		getServer().getPluginManager().registerEvents(new Listeners(), this);
		getCommand("eg").setExecutor(new Commands(this,new GameCountdown(this, null)));
		getCommand("endergolf").setExecutor(new Commands(this,new GameCountdown(this, null)));
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
