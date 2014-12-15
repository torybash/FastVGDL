package ontology.effects.unary;

import ontology.core.Sprite;
import ontology.effects.Interaction;
import parsing.core.Node;
import core.game.Game;

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
