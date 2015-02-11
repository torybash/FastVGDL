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
public class WrapAround extends Interaction {

    public double offset;

    public WrapAround()
    {
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game) {
    	super.hasInteracted(sprite1, sprite2);
//        if(sprite1.orientation.x > 0)
//        {
//            sprite1.rect.x = (int) (offset * sprite1.rect.width);
//        }
//        else if(sprite1.orientation.x < 0)
//        {
//            sprite1.rect.x = (int) (game.getScreenSize().width - sprite1.rect.width * (1+offset));
//        }
//        else if(sprite1.orientation.y > 0)
//        {
//            sprite1.rect.y = (int) (offset * sprite1.rect.height);
//        }
//        else if(sprite1.orientation.y < 0)
//        {
//            sprite1.rect.y = (int) (game.getScreenSize().height- sprite1.rect.height * (1+offset));
//        }
//
//        sprite1.lastmove = 0;
    }
}
