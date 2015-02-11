package fastVGDL.ontology.sprites.producer;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 21/10/13
 * Time: 18:07
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class SpriteProducer extends Sprite
{
    public SpriteProducer(){}

//    public SpriteProducer(Vector2i position, Dimension size, SpriteContent cnt)
//    {
//        //Init the sprite
//        this.init(position, size);
//
//        //Specific class default parameter values.
//        loadDefaults();
//
//        //Parse the arguments.
//        this.parseParameters(cnt);
//    }

//	public void update(Game game) {
//		
//		
//	}
//    protected void loadDefaults()
//    {
//        super.loadDefaults();
//    }
//
//
    public Sprite copy()
    {
        SpriteProducer newSprite = new SpriteProducer();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(Sprite target)
    {
        SpriteProducer targetSprite = (SpriteProducer) target;
        super.copyTo(targetSprite);
    }

}
