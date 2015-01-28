package fastVGDL.ontology.core;

import fastVGDL.core.game.Game;

public abstract class Termination {

	public int limit;
	public boolean win;
	
	public abstract void initialize();
	public abstract boolean isDone(Game game);
}
