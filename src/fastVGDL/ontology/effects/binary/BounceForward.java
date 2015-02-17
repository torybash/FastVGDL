package fastVGDL.ontology.effects.binary;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.tools.Vector2i;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 04/11/13
 * Time: 15:56
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class BounceForward extends Interaction
{

    public BounceForward()
    {
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game)
    {
    	super.hasInteracted(sprite1, sprite2);
    	
    	Vector2i dir = sprite2.getDirection();
    	
    	sprite1.move(dir, game);
//        Vector2d dir = new Vector2d(sprite2.lastDirection());
//        dir.normalise();
//
//        sprite1.physics.activeMovement(sprite1, dir, sprite2.speed);
//        game._updateCollisionDict(sprite1);
    }
}
