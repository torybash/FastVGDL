package fastVGDL.ontology.effects.unary;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.core.SpriteDefinition;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.parsing.core.Node;
import fastVGDL.parsing.core.VGDLRegistry;
import fastVGDL.core.game.Game;

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
    	super.hasInteracted(sprite1, sprite2);
    	SpriteDefinition sd = VGDLRegistry.GetInstance().getRegisteredSpriteDefinition(stype);
    	
    	resourceId =  VGDLRegistry.GetInstance().getRegisteredSpriteId(resource);
//    	if(game.getRandomGenerator().nextDouble() >= prob) return;
    	
        if(sprite1.getAmountResource(resourceId) >= limit)
        {
            game.addSprite(sd, sprite1.position.x, sprite1.position.y);
        }
    }
}
