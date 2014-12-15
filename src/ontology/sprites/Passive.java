package ontology.sprites;

import java.awt.Dimension;

import ontology.core.Sprite;
import tools.Vector2i;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 17/10/13
 * Time: 12:44
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Passive extends Sprite
{
    public Passive(){}

    public Passive(Vector2i position, Dimension size)
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


//    protected void loadDefaults()
//    {
//        super.loadDefaults();
//        color = Types.RED;
//    }
//
    public Sprite copy()
    {
        Passive newSprite = new Passive();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(Sprite target)
    {
        Passive targetSprite = (Passive) target;
        super.copyTo(targetSprite);
    }
}
