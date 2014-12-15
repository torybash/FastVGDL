package ontology.effects.unary;

import ontology.core.Sprite;
import ontology.effects.Interaction;
import parsing.core.Node;
import core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 23/10/13
 * Time: 15:21
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class KillSprite extends Interaction {

    public KillSprite()
    {
        is_kill_effect = true;
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game) {
        game.killSprite(sprite1);
    }
}
