package fastVGDL.ontology.effects.binary;

import java.util.Collection;

import fastVGDL.core.game.Game;
import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.ontology.sprites.producer.Portal;
import fastVGDL.parsing.core.VGDLRegistry;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 04/11/13
 * Time: 15:56
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class TeleportToExit extends Interaction
{

    public TeleportToExit()
    {
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game)
    {
    	super.hasInteracted(sprite1, sprite2);
//        int destinationId = VGDLFactory.GetInstance().requestFieldValueInt(sprite2, "itype");
    	
    	//Get stype of sprite 2:
    	Portal portal = (Portal) sprite2;
    	String spType = portal.stype;

    	int itype = VGDLRegistry.GetInstance().getRegisteredSpriteId(spType);
    	
    	//Get all sprites of stype:
    	Collection<Sprite> sprites = game.spriteGroups[itype].sprites.values();
    	if (sprites.size() > 0){
//    		Sprite destination = (Sprite) sprites.toArray()[game.getRandomGenerator().nextInt(sprites.size())];
    		Sprite destination = (Sprite) sprites.toArray()[0]; //<--- always taking the first -- random in puzzles is no good
    		
    		sprite1.position = destination.position.copy();
    		
    		sprite1.lastPosition = destination.position.copy();
    	}

//    	VGDLRegistry.GetInstance().getRegisteredSpriteId(stype)
//
//        Collection<VGDLSprite> sprites = game.getSprites(destinationId).values();
//
//        if (sprites.size() > 0){
//	        VGDLSprite destination = (VGDLSprite) Utils.choice(sprites.toArray(), game.getRandomGenerator());
//	
//	        sprite1.setRect(destination.rect);
//	
//	        sprite1.lastmove = 0;
//        }
    }
}
