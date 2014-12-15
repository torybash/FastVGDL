package ontology.effects.binary;

import ontology.core.Sprite;
import ontology.effects.Interaction;
import parsing.core.Node;
import core.game.Game;

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

//        if(sprite1.is_resource)
//        {
//            Resource r = (Resource) sprite1;
//
//            int numResources = sprite2.getAmountResource(r.resource_type);
//            if(numResources + r.value <= game.getResourceLimit(r.resource_type))
//            {
//                sprite2.modifyResource(r.resource_type, r.value);
//            }
//        }
    }
}
