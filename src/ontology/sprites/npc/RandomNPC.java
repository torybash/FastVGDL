package ontology.sprites.npc;

import java.awt.Dimension;

import ontology.core.Sprite;
import tools.Vector2i;
import core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 21/10/13
 * Time: 18:08
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class RandomNPC extends Sprite
{
    public RandomNPC(){}

    public RandomNPC(Vector2i position, Dimension size)
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
//    protected void loadDefaults()
//    {
//        super.loadDefaults();
//        speed = 1;
//        is_npc = true;
//        is_stochastic = true;
//    }
//
//    public void update(Game game)
//    {
//        super.updatePassive();
//        Vector2d act = (Vector2d) Utils.choice(Types.BASEDIRS, game.getRandomGenerator());
//        this.physics.activeMovement(this, act, this.speed);
//    }
//
    public Sprite copy()
    {
        RandomNPC newSprite = new RandomNPC();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(Sprite target)
    {
        RandomNPC targetSprite = (RandomNPC) target;
        super.copyTo(targetSprite);
    }
}
