package fastVGDL.core.game;

import java.nio.MappedByteBuffer;

import fastVGDL.ontology.Types.ACTIONS;
import fastVGDL.ontology.core.Sprite;
import fastVGDL.parsing.core.SpriteGroup;
import fastVGDL.parsing.core.VGDLRegistry;
import java.util.Iterator;

public class ForwardModel extends Game{

	public ForwardModel(Game game){
		spritesDefinitions = game.spritesDefinitions;
		terms = game.terms;	
		interactions = game.interactions;
		
		amountOfSpriteIDs = game.amountOfSpriteIDs;
		isEnded = game.isEnded;
		
		gametick = game.gametick;
						
		blockSize = game.blockSize;
		levelSize = game.levelSize;
		
		numSprites = game.numSprites;
		
		resources_limits = game.resources_limits;
		
//		avatarSprite = game.avatarSprite.copy();
		
		spriteGroups = new SpriteGroup[game.spriteGroups.length];
		for (int i = 0; i < spriteGroups.length; i++) {
			SpriteGroup sg = game.spriteGroups[i].copy();
			spriteGroups[i] = sg;
			if (sg.isAvatar && sg.sprites.size() > 0){
				for (Sprite sp :  sg.sprites.values()) {
					avatarSprite = sp;
					break;
				}
				
			}
		}
		
		r = game.r;
//                spriteGroups = new SpriteGroup[game.spriteGroups.length];
//        num_sprites = 0;

//        for(int i = 0; i < spriteGroups.length; ++i)
//        {
////            bucketList[i] = new Bucket();
//            spriteGroups[i] = new SpriteGroup(i);
//
//            Iterator<Sprite> spriteIt = game.spriteGroups[i].getSpriteIterator();
//            if(spriteIt != null) while(spriteIt.hasNext())
//            {
//                Sprite sp = spriteIt.next();
//                Sprite spCopy = sp.copy();
//                spriteGroups[i].sprites.put(spCopy.groupId, spCopy);
////                spriteGroups[i].addSprite(spCopy.id, spCopy);
////                checkSpriteFeatures(spCopy, i);
////                updateObservation(spCopy);
////			if (game.spriteGroups[i].isAvatar && game.spriteGroups[i].sprites.size() > 0){
//////				for (Sprite sp :  spriteGroups[i].sprites.values()) {
////					avatarSprite = sp;
////					break;
//////				}
////				
////			}
//            }
//            System.out.println("avatarSprite: " + avatarSprite);
//            
//            System.out.println("spriteGroups[i].isAvatar: " + game.spriteGroups[i].isAvatar);
////            int nSprites = spriteGroups[i].numSprites();
////            num_sprites += nSprites;
//        }
		
		visuals = false;
	}

	public void advance(ACTIONS act) {
//		System.out.println("-------ADVANCING WITH ACTION: " + act);
        if(!isEnded)
        {
        	gametick++;
        	
            spriteUpdates(act);
            eventHandling();
            clearAll();
            terminationHandling();
            checkTimeout();
            
            
//            printLevel();
//            updateAllObservations();
            
            
//            //PRINT LEVEL
//            char[][] map = new char[levelSize.x][];
//            for (int i = 0; i < map.length; i++) {
//				map[i] = new char[levelSize.y];
//			}
//            for (int i = 0; i < spriteGroups.length; i++) {
//				SpriteGroup sg = spriteGroups[i];
//				
//				for (Integer key : sg.sprites.keySet()) {
//					Sprite sp = sg.sprites.get(key);
//					map[sp.position.x][sp.position.y] = ("" + sp.id).charAt(0);
//				}
//			}
//            
//            System.out.println("LEVEL:");
//            for (int y = 0; y < levelSize.y; y++) {
//            	String s = "";
//                for (int x = 0; x < levelSize.x; x++) {
//    				s += map[x][y];
//    			}
//                System.out.println(s);
//			}
//            System.out.println();
        }
	}

	private void spriteUpdates(ACTIONS act) {
		avatarSprite.performActiveMovement(act, this);
		if (act != ACTIONS.ACTION_NIL) numActions++;
		
		for (int i = 0; i < spriteGroups.length; i++) {
			SpriteGroup sg = spriteGroups[i];
			if (sg.isAvatar) continue;
			for (Integer key : sg.sprites.keySet()) {
				Sprite sp = sg.sprites.get(key);
				sp.update(this);
			}
		}
	}
	
	
	public ForwardModel copy(){
		ForwardModel result = new ForwardModel(this);
		return result;
	}
}
