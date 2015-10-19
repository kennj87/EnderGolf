package endergolf.blockynights;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreBoard {

	Commands commands;
	GameInit gameinit;
	
	public ScoreBoard(Commands commands, GameInit gameinit) {
		this.commands = commands;
		this.gameinit = gameinit;
	}
	
	public void joinBoard(Player player) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("test", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("§lJoined list");
		Score score = objective.getScore("§2" + "----------");
		score.setScore(100);
		Score score1 = objective.getScore("§b" + player.getDisplayName());
		score1.setScore(1);
		Score score2 = objective.getScore("§3" + "- Open 2 -");
		score2.setScore(2);
		Score score3 = objective.getScore("§3" + "- Open 3 -");
		score3.setScore(3);
		Score score4 = objective.getScore("§3" + "- Open 4 -");
		score4.setScore(4);
		Score score5 = objective.getScore("§3" + "- Open 5 -");
		score5.setScore(5);
		Score score6 = objective.getScore("§3" + "- Open 6 -");
		score6.setScore(6);
		Score score7 = objective.getScore("§3" + "- Open 7 -");
		score7.setScore(7);
		Score score8 = objective.getScore("§3" + "- Open 8 -");
		score8.setScore(8);
		Score score9 = objective.getScore("§3" + "- Open 9 -");
		score9.setScore(9);
		Score score10 = objective.getScore("§3" + "- Open 10 -");
		score10.setScore(10);
		
		player.setScoreboard(board);
	}
	

	public void updateBoard() {
		Object[] players = Commands.playername.keySet().toArray();		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("test", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("§lJoined list");
		Score score = objective.getScore("§2" + "----------");
		score.setScore(100);
		
		for (int i = 0; i < 11; i++) {
			int e = i +1;
			if (players.length > i) {
				Score play = objective.getScore("§b" + players[i]);
				play.setScore(i);
			}
			else {
				Score play = objective.getScore("§3" + "- Open "+e+" -");
				play.setScore(i);
			}
		}
		for (Player p : Commands.players.keySet()) {
		    p.setScoreboard(board);
		}
	}
	
	public void clearBoard(Player player) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard emptyboard = manager.getNewScoreboard();
		player.setScoreboard(emptyboard);
	}
	public void clearBoardAll() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard emptyboard = manager.getNewScoreboard();
		for (Player p : Commands.players.keySet()) {
		    p.setScoreboard(emptyboard);
		}
	}
}
