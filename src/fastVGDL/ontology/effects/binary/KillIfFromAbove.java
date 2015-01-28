package fastVGDL.ontology.effects.binary;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.parsing.core.Node;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 04/11/13
 * Time: 15:57
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class KillIfFromAbove extends Interaction
{

    public KillIfFromAbove()
    {
        is_kill_effect = true;
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game)
    {
//        //Kills the sprite, only if the other one is higher and moving down.
//        boolean otherHigher = sprite1.lastrect.getMinY() > sprite2.lastrect.getMinY();
//        boolean goingDown = sprite2.rect.getMinY() > sprite2.lastrect.getMinY();
//
//        if (otherHigher && goingDown){
//            game.killSprite(sprite1);
//        }
    }
}
