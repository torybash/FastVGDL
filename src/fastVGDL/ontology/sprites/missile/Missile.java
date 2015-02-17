package fastVGDL.ontology.sprites.missile;

import java.awt.Dimension;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.tools.Vector2i;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 21/10/13
 * Time: 17:35
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Missile extends Sprite
{
    public Missile(){}

    public Missile(Vector2i position, Dimension size)
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
//        speed = 1;
//        is_oriented = true;
    }

	public void update(Game game) {
				
		super.update(game);
		Vector2i moveVector = new Vector2i();
		
		switch (orientation) {
		case 0:
			moveVector = new Vector2i(0, 1);
			break;
		case 1:
			moveVector = new Vector2i(1, 0);
			break;
		case 2:
			moveVector = new Vector2i(0, -1);
			break;
		case 3:
			moveVector = new Vector2i(-1, 0);
			break;

		default:
			break;
		}
		
		move(moveVector, game);
		
	}

    public Sprite copy()
    {
        Missile newSprite = new Missile();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(Sprite target)
    {
        Missile targetSprite = (Missile) target;
        super.copyTo(targetSprite);
    }
}
