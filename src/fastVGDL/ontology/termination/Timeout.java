package fastVGDL.ontology.termination;

import fastVGDL.ontology.core.Termination;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 22/10/13
 * Time: 18:48
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Timeout extends Termination
{
	@Override
	public void initialize() {
		
	}
	
	
    @Override
    public boolean isDone(Game game)
    {
//        boolean ended = super.isFinished(game);
//        if(ended)
//            return true;
//
//        if(game.getGameTick() >= limit)
//            return true;

        return false;
    }


}
