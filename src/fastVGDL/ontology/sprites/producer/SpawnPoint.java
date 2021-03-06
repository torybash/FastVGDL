package fastVGDL.ontology.sprites.producer;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.parsing.core.VGDLRegistry;
import fastVGDL.core.game.Game;


/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 21/10/13
 * Time: 18:24
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class SpawnPoint extends SpriteProducer
{
    public double prob;
    public int total;
    public int counter;
    public String stype;
    public int itype;
    
    public int cooldown = 1;

    public SpawnPoint(){}

//    public SpawnPoint(Vector2i position, Dimension size)
//    {
////        //Init the sprite
////        this.init(position, size);
////
////        //Specific class default parameter values.
////        loadDefaults();
////
////        //Parse the arguments.
////        this.parseParameters(cnt);
//    }

//    protected void loadDefaults()
//    {
//        super.loadDefaults();
//        prob = 1.0;
//        total = 0;
//        color = Types.BLACK;
//        cooldown = 1;
//        is_static = true;
//    }
//
//    public void postProcess()
//    {
//        super.postProcess();
//        is_stochastic = (prob > 0 && prob < 1);
//        counter = 0;
//        itype = VGDLRegistry.GetInstance().getRegisteredSpriteValue(stype);
//    }

    public void update(Game game)
    {
//        System.out.println("Total: " + total + " , counter: " + counter);

//        if((game.gametick % cooldown == 0) && game.getRandomGenerator().nextFloat() < prob)
        if((game.gametick % cooldown == 0))
        {
            game.addSprite(VGDLRegistry.GetInstance().getRegisteredSpriteDefinition(stype), position.x, position.y);
            counter++;
        }

        super.update(game);

        if(total > 0 && counter >= total)
        {
        	System.out.println("SPAWN POINT DYING");
            game.killSprite(this);
        }
    }

//    public VGDLSprite copy()
//    {
//        SpawnPoint newSprite = new SpawnPoint();
//        this.copyTo(newSprite);
//        return newSprite;
//    }
//
//    public void copyTo(VGDLSprite target)
//    {
//        SpawnPoint targetSprite = (SpawnPoint) target;
//        targetSprite.prob = this.prob;
//        targetSprite.total = this.total;
//        targetSprite.counter = this.counter;
//        targetSprite.stype = this.stype;
//        targetSprite.itype = this.itype;
//        super.copyTo(targetSprite);
//    }
    
    public Sprite copy()
    {
    	SpawnPoint newSprite = new SpawnPoint();
        this.copyTo(newSprite);
        
        newSprite.stype = stype;
        
        return newSprite;
    }

    public void copyTo(Sprite target)
    {
    	SpawnPoint targetSprite = (SpawnPoint) target;
        super.copyTo(targetSprite);
    }
}
