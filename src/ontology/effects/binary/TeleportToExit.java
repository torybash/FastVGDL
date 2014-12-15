package ontology.effects.binary;

import ontology.core.Sprite;
import ontology.effects.Interaction;
import parsing.core.Node;
import core.game.Game;

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
//        int destinationId = VGDLFactory.GetInstance().requestFieldValueInt(sprite2, "itype");
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
