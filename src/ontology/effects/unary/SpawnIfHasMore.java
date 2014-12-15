package ontology.effects.unary;

import ontology.core.Sprite;
import ontology.core.SpriteDefinition;
import ontology.effects.Interaction;
import parsing.core.Node;
import parsing.core.VGDLRegistry;
import core.game.Game;

/**
 * Created by Diego on 18/02/14.
 */
public class SpawnIfHasMore  extends Interaction {

    public String resource;
    public int resourceId;
    public int limit;
    public String stype;
    public int itype;

    public SpawnIfHasMore()
    {
        resourceId = -1;
//        resourceId = VGDLRegistry.GetInstance().getRegisteredSpriteValue(resource);
//        itype = VGDLRegistry.GetInstance().getRegisteredSpriteValue(stype);
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game)
    {
    	SpriteDefinition sd = VGDLRegistry.GetInstance().getRegisteredSpriteDefinition(stype);
//    	if(game.getRandomGenerator().nextDouble() >= prob) return;
    	
//        if(sprite1.getAmountResource(resourceId) >= limit)
        {
            game.addSprite(sd, sprite1.position.x, sprite1.position.y);
        }
    }
}
