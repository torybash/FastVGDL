package ontology.effects.binary;

import ontology.core.Sprite;
import ontology.effects.Interaction;
import parsing.core.Node;
import core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 04/11/13
 * Time: 15:56
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class WallStop extends Interaction
{
    private double friction;
    private int lastGameTime;
//    private ArrayList<VGDLSprite> spritesThisCycle;

    public WallStop()
    {
        lastGameTime = -1;
//        spritesThisCycle = new ArrayList<VGDLSprite>();
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game)
    {
        // Stop just in front of the wall, removing that velocity component, but possibly sliding along it.

        //Keep in the list, for the current cycle, the sprites that have triggered this event.
//        int currentGameTime = game.getGameTick();
//        if(currentGameTime > lastGameTime)
//        {
//            spritesThisCycle.clear();
//            lastGameTime = currentGameTime;
//        }
//
//        //the event gets triggered only once per time-step on each sprite.
//        if(spritesThisCycle.contains(sprite1))
//            return;
//
//        sprite1.setRect(sprite1.lastrect);
//        double centerXDiff = Math.abs(sprite1.rect.getCenterX() - sprite2.rect.getCenterX());
//        double centerYDiff = Math.abs(sprite1.rect.getCenterY() - sprite2.rect.getCenterY());
//
//        if(centerXDiff > centerYDiff)
//        {
//            sprite1.orientation = new Vector2d(0, sprite1.orientation.y * (1.0 - friction));
//        }else
//        {
//            sprite1.orientation = new Vector2d(sprite1.orientation.x * (1.0 - friction), 0);
//        }
//
//        sprite1.speed = sprite1.orientation.mag() * sprite1.speed;
//        sprite1.orientation.normalise();
    }
}
