package fastVGDL.core.game.results;

import fastVGDL.core.game.Game;
import fastVGDL.ontology.core.Sprite;
import fastVGDL.parsing.core.SpriteGroup;

public class GameResults {
	public boolean won;
	public int score;
	public int ticks;
	public int actions;
	
	public int interactions;
	public int numberSprites;
	
	public int numSpritesHasInteracted;
	public int numSpritesCreated;
	public int numSpritesKilled;
	public int numberWalls;
	
	public int visitedNodes;
	public int possibleSolutions;
	
	
	public GameResults(Game game){
		won = game.won;
		ticks = game.gametick;
		actions = game.numActions;
		interactions = game.numInteractions;
		numberSprites = game.numSprites;
		numSpritesHasInteracted = game.numSpritesHasInteracted;
		numSpritesKilled = game.numSpritesKilled;
		numSpritesCreated = game.numSpritesCreated;
		
		for (int i = 0; i < game.spriteGroups.length; i++) {
			SpriteGroup sg = game.spriteGroups[i];
			for (Integer key : sg.sprites.keySet()) {
				Sprite sp = sg.sprites.get(key);
				if (sp.name.equals("wall")) numberWalls++;
			}
		}
	}
	
	
	@Override
	public String toString() {
		String result = "{GameResults: won " + won + ", ticks: " + ticks + ", actions: " + actions + ", interactions: " + interactions + 
				", numberSprites: " + numberSprites + ", numSpritesHasInteracted: " + numSpritesHasInteracted + ", numberWalls: " + numberWalls +
				", numSpritesCreated: " + numSpritesCreated + ", numSpritesKilled: " + numSpritesKilled + "}";
		return result;
	}
}
