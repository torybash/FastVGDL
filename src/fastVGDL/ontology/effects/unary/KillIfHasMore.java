package fastVGDL.ontology.effects.unary;

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
public class KillIfHasMore extends Interaction
{
    public String resource;
    public int resourceId;
    public int limit;

    public KillIfHasMore()
    {
        is_kill_effect = true;
        resourceId = -1;
//        resourceId = VGDLRegistry.GetInstance().getRegisteredSpriteValue(resource);
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game)
    {
//        if(sprite1.getAmountResource(resourceId) >= limit)
//            game.killSprite(sprite1);
    }
}
