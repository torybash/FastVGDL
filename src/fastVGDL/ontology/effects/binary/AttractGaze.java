package fastVGDL.ontology.effects.binary;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.parsing.core.Node;
import fastVGDL.tools.Vector2i;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 04/11/13
 * Time: 15:56
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class AttractGaze extends Interaction
{
//    public double prob;

    public AttractGaze()
    {
    }

    @Override
    public void execute(Sprite sprite1, Sprite  sprite2, Game game)
    {    	
    	


      
    	//Get orientation of sprite 2:
    	Vector2i v = sprite2.getDirection();
    	int sp2orientation = -1;
    	if (v.y == 1) sp2orientation = 0;
    	else if (v.y == -1) sp2orientation = 2;
    	else if (v.x == 1) sp2orientation = 1;
    	else if (v.x == -1) sp2orientation = 3;
      
    	
      sprite1.orientation = sp2orientation;
      
    	
//        if(sprite1.is_oriented && sprite2.is_oriented)
//        {
//        	
//            if(game.getRandomGenerator().nextDouble() < prob)
//                sprite1.orientation = sprite2.orientation.copy();
//            
//        }
    }
}
