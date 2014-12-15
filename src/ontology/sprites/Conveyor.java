package ontology.sprites;

import java.awt.Dimension;

import ontology.core.Sprite;
import tools.Vector2i;
import core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 21/10/13
 * Time: 18:04
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Conveyor extends Sprite
{
    public Conveyor(){}

    public Conveyor(Vector2i position, Dimension size)
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
    
	public void update(Game game) {
		
		
	}

    protected void loadDefaults()
    {
//        super.loadDefaults();
//        is_static = true;
//        color = Types.BLUE;
//        strength = 1;
//        draw_arrow = true;
//        is_oriented = true;
    }


    public Sprite copy()
    {
        Conveyor newSprite = new Conveyor();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(Sprite target)
    {
        Conveyor targetSprite = (Conveyor) target;
        super.copyTo(targetSprite);
    }
}
