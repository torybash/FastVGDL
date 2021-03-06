package fastVGDL.ontology.termination;

import fastVGDL.ontology.core.Termination;
import fastVGDL.parsing.core.VGDLRegistry;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 22/10/13
 * Time: 18:54
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class MultiSpriteCounter extends Termination
{
    //TODO: Theoretically, we could have an array of types here... to be done.
    public String stype1, stype2;
    public int itype1=-1, itype2=-1;

    public void initialize()
    {
    	if(stype1 != null) itype1 = VGDLRegistry.GetInstance().getRegisteredSpriteId(stype1);
    	if(stype2 != null) itype2 = VGDLRegistry.GetInstance().getRegisteredSpriteId(stype2);
    }

    @Override
    public boolean isDone(Game game)
    {
//        boolean ended = super.isFinished(game);
//        if(ended)
//            return true;
//
         int countAcum = 0;

        if(itype1 != -1) countAcum += game.getNumSprites(itype1);
        if(itype2 != -1) countAcum += game.getNumSprites(itype2);

        if(countAcum == limit)
            return true;

        return false;
    }
}
