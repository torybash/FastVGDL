package fastVGDL.ontology.sprites;

import java.awt.Dimension;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.tools.Vector2i;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 17/10/13
 * Time: 12:45
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Flicker extends Sprite
{
    public int limit;

    public int age;

    public Flicker(){}

    public Flicker(Vector2i position, Dimension size)
    {
    	limit = 1;
    	age = 0;
      
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
		super.update(game);
		if(age > limit)
			game.killSprite(this);
      	age++;
	}

    protected void loadDefaults()
    {
//        super.loadDefaults();
//        limit = 1;
//        age = 0;
//        color = Types.RED;
    }

//    public void update(Game game)
//    {
//        super.update(game);
//
//        if(age > limit)
//            game.killSprite(this);
//        age++;
//
//    }
//
    public Sprite copy()
    {
        Flicker newSprite = new Flicker();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(Sprite target)
    {
        Flicker targetSprite = (Flicker) target;
        targetSprite.limit = this.limit;
        targetSprite.age = this.age;
        super.copyTo(targetSprite);
    }
}
