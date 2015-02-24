package fastVGDL.ontology.effects.binary;

import fastVGDL.core.game.Game;
import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.ontology.sprites.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 04/11/13
 * Time: 13:25
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class CollectResource extends Interaction
{

    public CollectResource()
    {
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game) {

    	super.hasInteracted(sprite1, sprite2);
        if(sprite1.isResource)
        {
            Resource r = (Resource) sprite1;

            int numResources = sprite2.getAmountResource(r.resource_type);
            if(numResources + r.value <= game.getResourceLimit(r.resource_type))
            {
                sprite2.modifyResource(r.resource_type, r.value);
            }
        }
    }
}
