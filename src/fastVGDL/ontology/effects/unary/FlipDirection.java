package fastVGDL.ontology.effects.unary;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.parsing.core.Node;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 03/12/13
 * Time: 16:17
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class FlipDirection extends Interaction
{
    public FlipDirection()
    {
        is_stochastic = true;
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game)
    {
//        sprite1.orientation = (Vector2d) Utils.choice(Types.BASEDIRS, game.getRandomGenerator());
    }
}
