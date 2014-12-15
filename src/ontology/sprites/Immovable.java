package ontology.sprites;

import java.awt.Dimension;

import ontology.core.Sprite;
import tools.Vector2i;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 17/10/13
 * Time: 12:39
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Immovable extends Sprite
{
    public Immovable(){}

    public Immovable(Vector2i position, Dimension size)
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
    
   

//    public void postProcess() {
//        super.postProcess();
//    }
//
//
//
//    protected void loadDefaults()
//    {
//        super.loadDefaults();
//        color = Types.GRAY;
//        is_static = true;
//    }
//
//
    public Sprite copy()
    {
        Immovable newSprite = new Immovable();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(Sprite target)
    {
        Immovable targetSprite = (Immovable) target;
        super.copyTo(targetSprite);
    }
}
