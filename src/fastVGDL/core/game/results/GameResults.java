package fastVGDL.core.game.results;

import fastVGDL.core.game.Game;

public class GameResults {
	public boolean won;
	public int score;
	public int ticks;
	public int actions;
	
	public int interactions;
	
	public int visitedNodes;
	public int possibleSolutions;
	
	
	public GameResults(Game game){
		won = game.won;
		ticks = game.gametick;
		actions = game.numActions;
		interactions = game.numInteractions;
		
	}
	
	
	@Override
	public String toString() {
		String result = "{GameResults: won " + won + ", ticks: " + ticks + ", actions: " + actions + ", interactions: " + interactions + "}";
		return result;
	}
}
