package fastVGDL.core.game;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

import fastVGDL.core.player.AbstractPlayer;
import fastVGDL.ontology.Types.ACTIONS;
import fastVGDL.ontology.core.Sprite;
import fastVGDL.ontology.core.SpriteDefinition;
import fastVGDL.ontology.core.Termination;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.ontology.sprites.Resource;
import fastVGDL.parsing.core.SpriteGroup;
import fastVGDL.tools.JEasyFrame;
import fastVGDL.tools.KeyInput;
import fastVGDL.tools.Vector2i;

public class Game {
	public String gamePath = "";
	
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
	
	
	protected int[] resources_limits;
		
	final int STANDARD_DELAY = 50;
	final int MAX_GAME_TICKS = 5000;
	
	public boolean won = false;
	
	public boolean visuals = false;
	
	ArrayList<ACTIONS> availableActions = null;
	
	public int numSprites = 0;
	public int numActions = 0;
	public int numInteractions = 0;
	public int numSpritesHasInteracted = 0;
	public int numSpritesKilled = 0;
	public int numSpritesCreated = 0;

	public int numberSpritesMoved;

	Random r;
	
	public Game(){}
	
	public Game(int derp){
		
	}
	
	
	public void initialize(ArrayList<SpriteDefinition> spritesDefinitions, ArrayList<Interaction> interactions, 
			ArrayList<Termination> terms, int amountOfSpriteIDs) {
		this.spritesDefinitions = spritesDefinitions;
		this.interactions = interactions;
		this.amountOfSpriteIDs = amountOfSpriteIDs;
		this.terms = terms;
		
		spriteGroups = new SpriteGroup[spritesDefinitions.size()];
		resources_limits = new int[spritesDefinitions.size()];
		
		ArrayList<Resource> resources = new ArrayList<Resource>();
        for(int i = 0; i < spritesDefinitions.size(); ++i){
        	SpriteDefinition sd = spritesDefinitions.get(i);
        	spriteGroups[sd.id] = new SpriteGroup(sd.id, sd.childIds);
        	spriteGroups[sd.id].leafNode = sd.leafNode;
        	spriteGroups[sd.id].isAvatar = sd.isAvatar;
        	
        	if (sd.defaultSprite != null && sd.defaultSprite.getClass().getSimpleName().equals("Resource")){
        		Resource res = (Resource) sd.defaultSprite;
        		res.id = sd.id;
        		resources.add(res);
        	}
        }
        
        for (Resource res : resources) {
			resources_limits[res.id] = res.limit;
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
//				System.out.println(sp.img);
				
				if (sp.img != null){
					sp.loadImage();
				}
//				else if (sp.color != null){
//					sp.loadColor();
//				}
//				
				
			}
		}
		
	}


	public void playGame(AbstractPlayer player, boolean visuals){
            if (visuals){
                playGameWithGraphics(player);
            }else{
                playGame(player);
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
				for (Sprite sp1 : s1s) {
					if (sp1.position.x < 0 || sp1.position.x >= levelSize.x || sp1.position.y < 0 || sp1.position.y >= levelSize.y){
//							System.out.println("Interaction execute!: " + inter.getClass().getSimpleName() + ", " + inter.id1 + " - " + inter.id2);
//							System.out.println(sp1.name + "("+sp1.id+")" + " colliding with EOS");
						if (!sp1.hasInteracted) numSpritesHasInteracted++;
						numInteractions++;
						inter.execute(sp1, null, this);
						
					}
				}
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
						numInteractions++;
						if (!sp1.hasInteracted) numSpritesHasInteracted++;
						if (!sp2.hasInteracted) numSpritesHasInteracted++;
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
		numSpritesKilled++;
		killList.add(sprite);		
	}
	
	
	public Sprite addSprite(SpriteDefinition sd, int x, int y){
		
//		System.out.println("ADDING SPRITE: " + sd.spriteName + " AT " + x + ", " + y + " --- image: " + sd.parameters.get("img") + " id: " + sd.id);

//		System.out.println("Num sprites: " + numSprites);
//                System.out.println("adding sprite -- sd.defaultSprite " + sd.defaultSprite);
            
//		System.out.println("sd.defaultSprite: " + sd.defaultSprite);
		
		Sprite sp = sd.defaultSprite.copy();
		
//		System.out.println("tha sp: " + sp);
//		try {
//			Constructor spriteConstructor = sd.spriteClass.getConstructor();
//			sp = (Sprite) spriteConstructor.newInstance();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
		sp.id = sd.id;
		sp.name = sd.spriteName;
		sp.position = new Vector2i(x, y);
		sp.lastPosition = sp.position.copy();
		if (visuals) sp.img = sd.parameters.get("img");
		
		spriteGroups[sd.id].addSprite(sp);
		spriteGroups[sd.id].leafNode = sd.leafNode;
		
		if (sd.isAvatar){
			avatarSprite = sp;
			sp.isAvatar = true;
			if (sp.img == null) sp.img = "avatar";
		}
		
//                System.out.println("sp " + sp + ", visuals: " + visuals + ", sd.parameters.get(\"img\"): " + sd.parameters.get("img"));
                
//		sp.parseParameters(sd.parameters);
		
		if (visuals){
			sp.loadImage(sp.img);
		}
		
		numSprites++;
		if (gametick > 0) numSpritesCreated++;
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

    public void disqualify() {

    }

    void reset() {

//	public Sprite avatarSprite;
//	
//	int amountOfSpriteIDs = 0;
//	public boolean isEnded = false;
//	
//	public int gametick = 0;
//	
//	public AbstractPlayer player = null;
//	
//	public static KeyInput ki;
//	
//	int blockSize = -1;
//	public Vector2i levelSize = null;
//	
//	
//	ArrayList<Sprite> killList = new ArrayList<Sprite>();
//	
//	
//	int numSprites = 0;
//		
//	final int STANDARD_DELAY = 50;
//	final int MAX_GAME_TICKS = 2000000;
//	
//	public boolean won = false;
//	
//	public boolean visuals = false;
//	
//	ArrayList<ACTIONS> availableActions = null;
//	
//	
//	public int numActions = 0;
//	public int numInteractions = 0;
    
    
    }

	public int getResourceLimit(int resourceId) {
		return resources_limits[resourceId];
	}

	public Random getRandomGenerator() {
		if (r == null) r = new Random();
		return r;
	}
	
}
