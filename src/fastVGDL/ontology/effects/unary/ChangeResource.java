package fastVGDL.ontology.effects.unary;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.parsing.core.Node;
import fastVGDL.parsing.core.VGDLRegistry;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 04/11/13
 * Time: 13:25
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class ChangeResource extends Interaction
{
    public String resource;
    public int resourceId;
    public int value = 1;

    public ChangeResource()
    {
//        value=1;
        resourceId = -1;
       
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game) {
    	super.hasInteracted(sprite1, sprite2);
    	
    	 resourceId = VGDLRegistry.GetInstance().getRegisteredSpriteId(resource);
    	
        int numResources = sprite1.getAmountResource(resourceId);
        if(numResources + value <= game.getResourceLimit(resourceId))
        {
            sprite1.modifyResource(resourceId, value);
        }
    }
}
