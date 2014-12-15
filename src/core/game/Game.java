package core.game;

import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;

import ontology.Types.ACTIONS;
import ontology.core.Sprite;
import ontology.core.SpriteDefinition;
import ontology.core.Termination;
import ontology.effects.Interaction;
import parsing.core.SpriteGroup;
import tools.JEasyFrame;
import tools.KeyInput;
import tools.Vector2i;
import core.player.AbstractPlayer;

public class Game {

	String gameTitle = "";
	
	ArrayList<SpriteDefinition> spritesDefinitions = new ArrayList<SpriteDefinition>();
	ArrayList<Termination> terms = new ArrayList<Termination>();
	ArrayList<Interaction> interactions = null;
	
	public SpriteGroup[] spriteGroups;
	
	public Sprite avatarSprite;
	
	int amountOfSpriteIDs = 0;
	public boolean isEnded = false;
	
	public int gametick = 0;
	
	public AbstractPlayer player = null;
	
	public static KeyInput ki;
	
	int blockSize = -1;
	public Vector2i levelSize = null;
	
	
	ArrayList<Sprite> killList = new ArrayList<Sprite>();
	
	
	int numSprites = 0;
		
	final int STANDARD_DELAY = 40;
	final int MAX_GAME_TICKS = 20000;
	
	public boolean won = false;
	
	boolean visuals = false;
	
	ArrayList<ACTIONS> availableActions = null;
	
	public void initialize(ArrayList<SpriteDefinition> spritesDefinitions, ArrayList<Interaction> interactions, 
			ArrayList<Termination> terms, int amountOfSpriteIDs) {
		this.spritesDefinitions = spritesDefinitions;
		this.interactions = interactions;
		this.amountOfSpriteIDs = amountOfSpriteIDs;
		this.terms = terms;
		
		spriteGroups = new SpriteGroup[spritesDefinitions.size()];
		
        for(int i = 0; i < spritesDefinitions.size(); ++i){
        	SpriteDefinition sd = spritesDefinitions.get(i);
        	spriteGroups[sd.id] = new SpriteGroup(sd.id, sd.childIds);
        	spriteGroups[sd.id].leafNode = sd.leafNode;
        	spriteGroups[sd.id].isAvatar = sd.isAvatar;
        }
        
        isEnded = false;
  	}

	
	public void playGameWithGraphics(AbstractPlayer player){
		visuals = true;
		
		this.player = player;
		
		ki = new KeyInput();
		
		VGDLViewer view = new VGDLViewer(this);
		JEasyFrame frame;
		frame = new JEasyFrame(view, "Java-VGDL");
		frame.addKeyListener(ki);
		
		loadSpriteImages();
		
		while(!isEnded){
          //Determine the time to adjust framerate.
          long then = System.currentTimeMillis();

          this.gameCycle(); //Execute a game cycle.

          //Get the remaining time to keep fps.
          long now = System.currentTimeMillis();
          int remaining = (int) Math.max(0, STANDARD_DELAY - (now-then));

          //Wait until the next cycle.
          waitStep(remaining);

          //Draw all sprites in the panel.
          view.paint();

          //Update the frame title to reflect current score and tick.
          frame.setTitle("FAST-VGDL. Tick:" + gametick);
		}
		
		System.out.println("Game ended. Win: " +  won);
	}
	
	private void loadSpriteImages() {
		for (int i = 0; i < spriteGroups.length; i++) {
			SpriteGroup sg = spriteGroups[i];
			
			for (Integer key : sg.sprites.keySet()) {
				Sprite sp = sg.sprites.get(key);
				sp.loadImage(sp.img);
			}
		}
		
	}


	public void playGame(AbstractPlayer player){
		this.player = player;
		
		while(!isEnded){
			this.gameCycle(); //Execute a game cycle.
		}
	}
	
	private void gameCycle() {
	    gametick++; //next game tick.
	
	    //Update our state observation (forward model) with the information of the current game state.
//	    fwdModel.update(this);
	
	    //Execute a game cycle:
	    this.spriteUpdates();                    //update for all entities.
	    this.eventHandling();           //handle events such collisions.
	    this.clearAll();                //clear all additional data, including dead sprites.
	    this.terminationHandling();     //check for game termination.
	    this.checkTimeout();            //Check for end of game by time steps.
	}









	private void spriteUpdates() {
		for (int i = 0; i < spriteGroups.length; i++) {
			SpriteGroup sg = spriteGroups[i];
			
			for (Integer key : sg.sprites.keySet()) {
				Sprite sp = sg.sprites.get(key);
				sp.update(this);
			}
		}
		
	}


	protected void clearAll() {
        for(Sprite sprite : killList)
        {
            int spriteType = sprite.id;
            this.spriteGroups[spriteType].removeSprite(sprite.groupId);
//            if(fwdModel != null)
//                fwdModel.removeSpriteObservation(sprite);

//            if(sprite.is_avatar && sprite == this.avatar)
//                this.avatar = null;

            numSprites--;

        }
        killList.clear();

        if (visuals) ki.reset();
	}
	
	protected void terminationHandling() {
		for (Termination t : terms) {
			if (t.isDone(this)){
				isEnded = true;
				won = t.win;
				break;
			}
		}
		
	}
	
	protected void checkTimeout() {
		if (gametick > MAX_GAME_TICKS){
			isEnded = true;
		}
	}
	
	protected void printLevel(){
		char[][] map = new char[levelSize.x][];
        for (int i = 0; i < map.length; i++) {
			map[i] = new char[levelSize.y];
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = "-".charAt(0);
			}
		}
        for (int i = 0; i < spriteGroups.length; i++) {
			SpriteGroup sg = spriteGroups[i];
			
			for (Integer key : sg.sprites.keySet()) {
				Sprite sp = sg.sprites.get(key);
				map[sp.position.x][sp.position.y] = ("" + sp.id).charAt(0);
			}
		}
        
        System.out.println("LEVEL (" + this.getClass().getSimpleName() + ")");
        for (int y = 0; y < levelSize.y; y++) {
        	String s = "";
            for (int x = 0; x < levelSize.x; x++) {
				s += map[x][y];
			}
            System.out.println(s);
		}
        System.out.println();
	}

	protected void eventHandling() {
//		for (int i = 0; i < spriteGroups.length; i++) {
//			SpriteGroup sg = spriteGroups[i];
//			System.out.println("SpriteGroup: " + i + ", id: " + sg.id + ", spriteName: " + (sg.sprites.get(0) != null ? sg.sprites.get(0).name : "null") + ", amount: "+ sg.sprites.size() +  ", childIds: " + sg.childIds);
//		}
		
		
		for (Interaction inter : interactions) {
//			System.out.println("Interaction: " + inter.getClass().getSimpleName() + ", " + inter.id1 + " - " + inter.id2);
			
//			ArrayList<SpriteGroup> sg1s = new ArrayList<SpriteGroup>();
//			ArrayList<SpriteGroup> sg2s = new ArrayList<SpriteGroup>();
			
			ArrayList<Sprite> s1s = new ArrayList<Sprite>();
			ArrayList<Sprite> s2s = new ArrayList<Sprite>();
			
			//Sprite 1:
			if (spriteGroups[inter.id1].leafNode){
				for (Sprite sprite : spriteGroups[inter.id1].sprites.values()) {
					s1s.add(sprite);
				}
			}else{
				//Find child sprites:
				for (Integer childId : spriteGroups[inter.id1].childIds) {
					for (Sprite sprite : spriteGroups[childId].sprites.values()){
						s1s.add(sprite);
					}
				}
			}
			
			//Sprite 2:
			if (inter.id2 == -1){
				//EOS effect
//				for (SpriteGroup sg1 : sg1s) {
//					for (Integer sp1key : sg1.sprites.keySet()) {
//						Sprite sp1 = sg1.sprites.get(sp1key);
//						if (sp1.position.x < 0 || sp1.position.x >= levelSize.x || sp1.position.y < 0 || sp1.position.y >= levelSize.y){
//							System.out.println("Interaction execute!: " + inter.getClass().getSimpleName() + ", " + inter.id1 + " - " + inter.id2);
//							System.out.println(sp1.name + "("+sp1.id+")" + " colliding with EOS");
//							inter.execute(sp1, null, this);
//						}
//					}
//				}
			}else if (spriteGroups[inter.id2].leafNode){
				for (Sprite sprite : spriteGroups[inter.id2].sprites.values()) {
					s2s.add(sprite);
				}
			}else{
				//Find child sprites:
				for (Integer childId : spriteGroups[inter.id2].childIds) {
					for (Sprite sprite : spriteGroups[childId].sprites.values()){
						s2s.add(sprite);
					}
				}
			}
		
			for (Sprite sp1 : s1s) {
				if (killList.contains(sp1)) continue;
				for (Sprite sp2 : s2s) {
					if (killList.contains(sp2)) continue;
					if (sp1 != sp2 && sp1.position.x == sp2.position.x && sp1.position.y == sp2.position.y){
//						System.out.println("Interaction execute!: " + inter.getClass().getSimpleName() + ", " + inter.id1 + " - " + inter.id2);
//						System.out.println(sp1.name + "("+sp1.id+")" + " colliding with " + sp2.name + "("+sp2.id+")");
						inter.execute(sp1, sp2, this);
						
						if (killList.contains(sp1)) break;
					}
				}
			}
		}
		
	}
	


    void waitStep(int duration) {
    	try
         {
             Thread.sleep(duration);
         }
         catch(InterruptedException e)
         {
             e.printStackTrace();
         }
     }

	public Dimension getScreenSize() {
		blockSize = 500 / Math.max(levelSize.x, levelSize.y);
		
		Dimension screenSize = new Dimension(levelSize.x * blockSize, levelSize.y * blockSize);
		return screenSize;
	}

	public void setLevelSize(int width, int height) {
		levelSize = new Vector2i(width, height);		
	}
	
	public int getBlockSize(){
		return blockSize;
	}

	public void killSprite(Sprite sprite) {
		killList.add(sprite);		
	}
	
	
	public Sprite addSprite(SpriteDefinition sd, int x, int y){
		
//		System.out.println("ADDING SPRITE: " + sd.spriteName + " AT " + x + ", " + y + " --- image: " + sd.parameters.get("img") + " id: " + sd.id);

		Sprite sp = null;
		try {
			Constructor spriteConstructor = sd.spriteClass.getConstructor();
			sp = (Sprite) spriteConstructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		sp.id = sd.id;
		sp.name = sd.spriteName;
		sp.position = new Vector2i(x, y);
		sp.lastPosition = sp.position.copy();

		spriteGroups[sd.id].addSprite(sp);
		spriteGroups[sd.id].leafNode = sd.leafNode;
		
		if (sd.isAvatar){
			avatarSprite = sp;
			sp.isAvatar = true;
			if (sp.img == null) sp.img = "avatar";
		}
		
		sp.parseParameters(sd.parameters);
		
		if (visuals) sp.loadImage(sd.parameters.get("img"));
		
		return sp;
	}


	public int getNumSprites(int spriteId) {
		if (spriteGroups[spriteId].leafNode){
			return spriteGroups[spriteId].sprites.size();
		}else{
			int count = 0;
			for (Integer childId : spriteGroups[spriteId].childIds) {
				count += spriteGroups[childId].sprites.size();
			}
			return count;
		}
	}


	public ArrayList<ACTIONS> getAvailableActions() {
		if (availableActions == null) {
			availableActions = new ArrayList<ACTIONS>();
			availableActions.add(ACTIONS.ACTION_UP);
			availableActions.add(ACTIONS.ACTION_RIGHT);
			availableActions.add(ACTIONS.ACTION_DOWN);
			availableActions.add(ACTIONS.ACTION_LEFT);
		}
		return availableActions;
	}
	
}
