package fastVGDL.ontology.effects.binary;

import java.util.ArrayList;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.tools.Vector2i;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 04/11/13
 * Time: 15:56
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class PullWithIt extends Interaction
{
    private int lastGameTime;

    private ArrayList<Sprite> spritesThisCycle;

    public PullWithIt()
    {
        lastGameTime = -1;
        spritesThisCycle = new ArrayList<Sprite>();
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game)
    {
    	
        
//        //And go on.
//        Rectangle r = sprite1.lastrect;
//        Vector2d v = sprite2.lastDirection();
//        v.normalise();
//
//        int gridsize = 1;
//        if(sprite1.physicstype_id == Types.PHYSICS_GRID)
//        {
//            GridPhysics gp = (GridPhysics)(sprite1.physics);
//            gridsize = gp.gridsize.width;
//        }else if(sprite1.physicstype_id == Types.PHYSICS_CONT)
//        {
//            GridPhysics gp = (ContinuousPhysics)(sprite1.physics);
//            gridsize = gp.gridsize.width;
//        }
//
//        sprite1._updatePos(v, (int) (sprite2.speed*gridsize));
//        if(sprite1.physicstype_id == Types.PHYSICS_CONT)
//        {
//            sprite1.speed = sprite2.speed;
//            sprite1.orientation = sprite2.orientation;
//        }
//
//        sprite1.lastrect = new Rectangle(r);
    }
}
