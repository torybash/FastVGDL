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
public class TurnAround extends Interaction
{
    public TurnAround()
    {
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game)
    {
    	super.hasInteracted(sprite1, sprite2);
//        sprite1.setRect(sprite1.lastrect);
//        sprite1.lastmove = sprite1.cooldown;
//        sprite1.physics.activeMovement(sprite1, Types.DOWN, sprite1.speed);
//        sprite1.lastmove = sprite1.cooldown;
//        sprite1.physics.activeMovement(sprite1, Types.DOWN, sprite1.speed);
//        game.reverseDirection(sprite1);
//        game._updateCollisionDict(sprite1);
    }
}
