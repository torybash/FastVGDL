package fastVGDL.ontology.effects.unary;

import javax.swing.text.Position;

import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.core.SpriteDefinition;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.parsing.core.VGDLRegistry;
import fastVGDL.core.game.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 23/10/13
 * Time: 15:21
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class TransformTo extends Interaction {

    //TODO: Theoretically, we could have an array of types here... to be done.
    public String stype;
    public int itype;
    

    public TransformTo()
    {
        is_kill_effect = true;
//        itype = VGDLRegistry.GetInstance().getRegisteredSpriteValue(stype);
    }

    @Override
    public void execute(Sprite sprite1, Sprite sprite2, Game game)
    {
    	super.hasInteracted(sprite1, sprite2);
    	SpriteDefinition sd = VGDLRegistry.GetInstance().getRegisteredSpriteDefinition(stype);
    	
    	Sprite transformedSprite = game.addSprite(sd, sprite1.position.x, sprite1.position.y);
    	
    	transformedSprite.lastPosition.x = sprite1.lastPosition.x;
    	transformedSprite.lastPosition.y = sprite1.lastPosition.y;
    	
    	if (sprite1.isAvatar){
    		transformedSprite.isAvatar = true;
    		game.avatarSprite = transformedSprite;
    	}
    	
    	game.killSprite(sprite1);
    	
//        VGDLSprite newSprite = game.addSprite(itype, sprite1.getPosition());
//        if(newSprite != null)
//        {
//            //Orientation
//            if(newSprite.is_oriented && sprite1.is_oriented)
//            {
//                newSprite.orientation = sprite1.orientation;
//            }
//
//            //Last position of the avatar.
//            newSprite.lastrect =  new Rectangle(sprite1.lastrect.x, sprite1.lastrect.y,
//                                                sprite1.lastrect.width, sprite1.lastrect.height);
//
//            //Copy resources
//            if(sprite1.resources.size() > 0)
//            {
//                Set<Map.Entry<Integer, Integer>> entries = sprite1.resources.entrySet();
//                for(Map.Entry<Integer, Integer> entry : entries)
//                {
//                    int resType = entry.getKey();
//                    int resValue = entry.getValue();
//                    newSprite.modifyResource(resType, resValue);
//                }
//            }
//
//
//            //Avatar handling.
//            if(sprite1.is_avatar)
//            {
//                try{
//                    game.setAvatar((MovingAvatar) newSprite);
//                    game.getAvatar().player = ((MovingAvatar) sprite1).player;
//                }catch (ClassCastException e) {}
//            }
//
//            game.killSprite(sprite1);
//        }
    }
}
