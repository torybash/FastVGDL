package ontology.termination;

import ontology.core.Termination;
import parsing.core.VGDLRegistry;
import core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 22/10/13
 * Time: 18:52
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class SpriteCounter extends Termination
{
    public String stype;
    public int itype;

    public void initialize()
    {
        itype = VGDLRegistry.GetInstance().getRegisteredSpriteId(stype);
    }

    public boolean isDone(Game game) {    	
        if(game.getNumSprites(itype) <= limit)
            return true;

        return false;
    }

}
