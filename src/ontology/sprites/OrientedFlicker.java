package ontology.sprites;

import java.awt.Dimension;

import tools.Vector2i;
import core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 21/10/13
 * Time: 16:24
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class OrientedFlicker extends Flicker{

    public OrientedFlicker(){}

    public OrientedFlicker(Vector2i position, Dimension size)
    {
//        //Init the sprite
//        this.init(position, size);
//
//        //Specific class default parameter values.
//        loadDefaults();
//
//        //Parse the arguments.
//        this.parseParameters(cnt);
    }

    protected void loadDefaults()
    {
//        super.loadDefaults();
//        draw_arrow = true;
//        speed = 0;
//        is_oriented = true;
    }

    public void update(Game game)
    {
        super.update(game);
    }

//    public VGDLSprite copy()
//    {
//        OrientedFlicker newSprite = new OrientedFlicker();
//        this.copyTo(newSprite);
//        return newSprite;
//    }
//
//    public void copyTo(VGDLSprite target)
//    {
//        OrientedFlicker targetSprite = (OrientedFlicker) target;
//        super.copyTo(targetSprite);
//    }
}
