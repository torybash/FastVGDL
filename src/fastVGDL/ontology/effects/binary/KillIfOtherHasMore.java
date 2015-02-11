package fastVGDL.ontology.effects.binary;

import fastVGDL.core.game.Game;
import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.parsing.core.VGDLRegistry;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 04/11/13
 * Time: 15:57
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class KillIfOtherHasMore extends Interaction
{
    public String resource;
    public int resourceId;
    public int limit;

    public KillIfOtherHasMore()
    {
        is_kill_effect = true;
        resourceId = -1;
//        resourceId = VGDLRegistry.GetInstance().getRegisteredSpriteValue(resource);
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game)
    {
    	super.hasInteracted(sprite1, sprite2);
        //If 'sprite2' has more than a limit of the resource type given, sprite dies.
    	
    	 resourceId = VGDLRegistry.GetInstance().getRegisteredSpriteId(resource);
    	
        if(sprite2.getAmountResource(resourceId) >= limit)
            game.killSprite(sprite1);
    }
}
