package fastVGDL.ontology.effects;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.parsing.core.Node;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 23/10/13
 * Time: 15:20
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public abstract class Interaction
{
	public int id1 = -1;
	public int id2 = -1;
	
    public boolean is_kill_effect = false;

    public boolean is_stochastic = false;

    public int scoreChange = 0;
    
    public abstract void execute(Sprite sprite1, Sprite sprite2, Game game);

}
