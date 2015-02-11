package fastVGDL.ontology.effects.unary;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.parsing.core.Node;
import fastVGDL.core.game.Game;

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
    	super.hasInteracted(sprite1, sprite2);
    	
        game.killSprite(sprite1);
    }
}
