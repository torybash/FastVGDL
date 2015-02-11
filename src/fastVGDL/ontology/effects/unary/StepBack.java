package fastVGDL.ontology.effects.unary;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.parsing.core.Node;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 23/10/13
 * Time: 15:23
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class StepBack extends Interaction
{
    public StepBack()
    {
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game)
    {
    	super.hasInteracted(sprite1, sprite2);
//    	System.out.println(sprite1.name + " should step back! pos: " + sprite1.position + " , last pos: "+ sprite1.lastPosition);
    	sprite1.position.x = sprite1.lastPosition.x;
    	sprite1.position.y = sprite1.lastPosition.y;
        
    }
}
