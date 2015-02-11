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
public class CloneSprite extends Interaction {

    public CloneSprite()
    {
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game) {
    	super.hasInteracted(sprite1, sprite2);
//        int itype = sprite1.getType();
//        Vector2d pos = sprite1.getPosition();
//        game.addSprite(itype, pos);
    }
}
