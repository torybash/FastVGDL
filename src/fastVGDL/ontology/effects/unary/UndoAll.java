package fastVGDL.ontology.effects.unary;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.parsing.core.SpriteGroup;
import fastVGDL.tools.Vector2i;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 23/10/13
 * Time: 15:23
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class UndoAll extends Interaction
{
    public UndoAll()
    {
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game)
    {
    	super.hasInteracted(sprite1, sprite2);
//    	System.out.println("undoAll!");
    	for (int i = 0; i < game.spriteGroups.length; i++) {
			SpriteGroup sg = game.spriteGroups[i];
			
			for (Integer groupKey: sg.sprites.keySet()) {
				Sprite sp = sg.sprites.get(groupKey);
				sp.position.x = sp.lastPosition.x;
				sp.position.y = sp.lastPosition.y;
				
				
			}
		}

//        int[] gameSpriteOrder = game.getSpriteOrder();
//        int spriteOrderCount = gameSpriteOrder.length;
//        for(int i = 0; i < spriteOrderCount; ++i)
//        {
//            int spriteTypeInt = gameSpriteOrder[i];
//
//            Iterator<VGDLSprite> spriteIt = game.getSpriteGroup(spriteTypeInt);
//            if(spriteIt != null) while(spriteIt.hasNext())
//            {
//                VGDLSprite sp = spriteIt.next();
//                sp.setRect(sp.lastrect);
//            }
//        }
    }
}
