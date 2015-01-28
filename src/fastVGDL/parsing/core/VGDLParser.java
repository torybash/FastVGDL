package fastVGDL.parsing.core;

import fastVGDL.core.game.Game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.management.RuntimeErrorException;

import fastVGDL.ontology.Types;
import fastVGDL.ontology.avatar.FlakAvatar;
import fastVGDL.ontology.avatar.HorizontalAvatar;
import fastVGDL.ontology.avatar.MovingAvatar;
import fastVGDL.ontology.avatar.NoisyRotatingFlippingAvatar;
import fastVGDL.ontology.avatar.RotatingAvatar;
import fastVGDL.ontology.avatar.RotatingFlippingAvatar;
import fastVGDL.ontology.avatar.VerticalAvatar;
import fastVGDL.ontology.avatar.oriented.AimedAvatar;
import fastVGDL.ontology.avatar.oriented.AimedFlakAvatar;
import fastVGDL.ontology.avatar.oriented.InertialAvatar;
import fastVGDL.ontology.avatar.oriented.MarioAvatar;
import fastVGDL.ontology.avatar.oriented.MissileAvatar;
import fastVGDL.ontology.avatar.oriented.OrientedAvatar;
import fastVGDL.ontology.avatar.oriented.ShootAvatar;
import fastVGDL.ontology.core.SpriteDefinition;
import fastVGDL.ontology.core.Termination;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.ontology.sprites.Conveyor;
import fastVGDL.ontology.sprites.Door;
import fastVGDL.ontology.sprites.Flicker;
import fastVGDL.ontology.sprites.Immovable;
import fastVGDL.ontology.sprites.OrientedFlicker;
import fastVGDL.ontology.sprites.Passive;
import fastVGDL.ontology.sprites.Resource;
import fastVGDL.ontology.sprites.ResourcePack;
import fastVGDL.ontology.sprites.Spreader;
import fastVGDL.ontology.sprites.missile.ErraticMissile;
import fastVGDL.ontology.sprites.missile.Missile;
import fastVGDL.ontology.sprites.missile.RandomMissile;
import fastVGDL.ontology.sprites.missile.Walker;
import fastVGDL.ontology.sprites.missile.WalkerJumper;
import fastVGDL.ontology.sprites.npc.AlternateChaser;
import fastVGDL.ontology.sprites.npc.Chaser;
import fastVGDL.ontology.sprites.npc.Fleeing;
import fastVGDL.ontology.sprites.npc.RandomAltChaser;
import fastVGDL.ontology.sprites.npc.RandomInertial;
import fastVGDL.ontology.sprites.npc.RandomNPC;
import fastVGDL.ontology.sprites.producer.Bomber;
import fastVGDL.ontology.sprites.producer.Portal;
import fastVGDL.ontology.sprites.producer.SpawnPoint;
import fastVGDL.ontology.sprites.producer.SpriteProducer;
import fastVGDL.tools.IO;
import fastVGDL.tools.Utils;
import fastVGDL.ontology.termination.MultiSpriteCounter;
import fastVGDL.ontology.termination.SpriteCounter;

public class VGDLParser {

	int currentSet;
	int currID = 0;
        
	public int amountOfSpriteIDs = -1;
	SpriteDefinition wallDefintion = null;
	SpriteDefinition avatarDefinition = null;
        

       
//        ArrayList<SpriteDefinition> spritesDefinitions = new ArrayList<SpriteDefinition>();
//	ArrayList<Termination> terms = new ArrayList<Termination>();
//	ArrayList<Interaction> interactions = null;
	
	HashMap<Character, ArrayList<SpriteDefinition>> currentGameMappings = new HashMap<Character, ArrayList<SpriteDefinition>>();
	
	static VGDLParser parser = null;
	
    private Class[] possibleSpriteClasses = new Class[]
            {Conveyor.class, Flicker.class, Immovable.class, OrientedFlicker.class, Passive.class, Resource.class, Spreader.class,
             ErraticMissile.class, Missile.class, RandomMissile.class, Walker.class, WalkerJumper.class,
             ResourcePack.class, Chaser.class, Fleeing.class, RandomInertial.class, RandomNPC.class, AlternateChaser.class, RandomAltChaser.class,
             Bomber.class, Portal.class, SpawnPoint.class, SpriteProducer.class, Door.class,
             FlakAvatar.class, HorizontalAvatar.class,MovingAvatar.class, VerticalAvatar.class,
             NoisyRotatingFlippingAvatar.class,RotatingAvatar.class,RotatingFlippingAvatar.class,
             AimedAvatar.class,AimedFlakAvatar.class,InertialAvatar.class,MarioAvatar.class,
             OrientedAvatar.class,ShootAvatar.class, MissileAvatar.class};
	
    public static VGDLParser GetInstance(){
        if(parser == null)
        	parser = new VGDLParser();
        return parser;
    }
	

    
    
    public Game parseGame(String gamedesc_file){
				
    	Game game = new Game();

        game.gamePath = gamedesc_file;
                
        String[] desc_lines = new IO().readFile(gamedesc_file);
        
        currentSet = 0;
	currID = 0;
        
	amountOfSpriteIDs = -1;
	wallDefintion = null;
	avatarDefinition = null;
        
        ArrayList<SpriteDefinition> spriteDefinitions = null;
        ArrayList<Interaction> interactions = null;
        ArrayList<Termination> terminations = null;
        
        if(desc_lines != null)
        {
            Node rootNode = indentTreeParser(desc_lines);

            //Parse blocks of VGDL.
            for(Node n : rootNode.children)
            {
                if(n.identifier.equals("SpriteSet"))
                {
                    spriteDefinitions = parseSpriteSet(n.children, true);
                	
                }else if(n.identifier.equals("InteractionSet"))
                {
                    interactions = parseInteractionSet(n.children, spriteDefinitions);
                }else if(n.identifier.equals("LevelMapping"))
                {
                    parseLevelMapping(n.children, spriteDefinitions);
                }else if(n.identifier.equals("TerminationSet"))
                {
                    terminations = parseTerminationSet(n.children);
                }
            }
        }

        
//        System.out.println("DONE PARSING");
//        System.out.println("amountOfSpriteIDs: " + amountOfSpriteIDs);
        
        game.initialize(spriteDefinitions, interactions, terminations, amountOfSpriteIDs);
        
        return game;
    }
    
    




	public Node indentTreeParser(String[] lines)
    {
        //By default, let's make tab as four spaces
        String tabTemplate = "    ";
        Node last = null;

        for(String line : lines)
        {
        	// replace tabs with spaces
        	line = line.replaceAll("\t",tabTemplate);

        	// remove comments starting with "#"
            if(line.contains("#"))  line = line.split("#")[0];
               
            // handle whitespace and indentation
            String contentLine = line.trim();
            
            // make content prettier
            contentLine = Utils.formatString(line);

            if(contentLine.length() > 0)
            {
                updateSet(contentLine); //Identify the set we are in.
                //figure out the indent of the line.
                int indent = line.indexOf(contentLine.charAt(0));
                last = new Node(contentLine, indent, last, currentSet);
            }
        }

        return last.getRoot();
    }

    
    private void updateSet(String line)
    {
        if(line.equalsIgnoreCase("SpriteSet"))
            currentSet = Types.VGDL_SPRITE_SET;
        if(line.equalsIgnoreCase("InteractionSet"))
            currentSet = Types.VGDL_INTERACTION_SET;
        if(line.equalsIgnoreCase("LevelMapping"))
            currentSet = Types.VGDL_LEVEL_MAPPING;
        if(line.equalsIgnoreCase("TerminationSet"))
            currentSet = Types.VGDL_TERMINATION_SET;
    }
    
    
    /**
     * Parses the sprite set, and then initializes the game structures for the sprites.
     * @param spriteNodes children of the root node of the game description sprite set.
     */
    public ArrayList<SpriteDefinition> parseSpriteSet(ArrayList<Node> spriteNodes, boolean registerSprites)
    {
        ArrayList<SpriteDefinition> spriteDefinitions = new ArrayList<SpriteDefinition>();
       
        _parseSprites(spriteNodes, null, spriteDefinitions);

    	//Check for avatar - make one if don't exist
        boolean hasAvatar = false;
    	for (SpriteDefinition sd : spriteDefinitions) {
    		if (!sd.leafNode) continue;
    		if (sd.spriteClass.getSimpleName().contains("vatar")){
    			hasAvatar = true;
    			avatarDefinition = sd;
    			break;
    		}
    	}
    	if (!hasAvatar){
    		avatarDefinition = new SpriteDefinition("avatar");
    		spriteDefinitions.add(avatarDefinition);
    	}
    	
    	
    	//Make wall
    	wallDefintion = new SpriteDefinition("wall");
    	spriteDefinitions.add(wallDefintion);
        
    	
    	//Set IDs
    	setSpriteIDs(spriteNodes, hasAvatar, spriteDefinitions);

        //Register all spriteDefinitions
        if (registerSprites){
            for (SpriteDefinition sd : spriteDefinitions) {
                VGDLRegistry.GetInstance().registerSprite(sd);
            }
        }

        return spriteDefinitions;
    }
    
    
    private void setSpriteIDs(ArrayList<Node> spriteNodes, boolean hasAvatar, ArrayList<SpriteDefinition> spriteDefinitions){
    	
        
    	_setSpriteIDs(spriteNodes, spriteDefinitions);
    	
    	SpriteDefinition wallSd = spriteDefinitions.get(spriteDefinitions.size()-1);
    	wallSd.id = 0;
    	
    	if (!hasAvatar){ //avatar was generated, so ID need to be generated as well
    		SpriteDefinition avatarSd = spriteDefinitions.get(spriteDefinitions.size()-2);
    		avatarSd.id = ++currID;
    	}
    	
    	amountOfSpriteIDs = ++currID;
    }
    
    private void _setSpriteIDs(ArrayList<Node> spriteNodes, ArrayList<SpriteDefinition> spriteDefinitions){
    	for (Node n : spriteNodes) {	
    		n.id = ++currID;
    		
			if (n.children.size() == 0){ //leaf node
				String spriteName = n.identifier;

				for (SpriteDefinition sd : spriteDefinitions) {
					if (spriteName.equals(sd.spriteName)){
						sd.id = n.id;
						if (sd.depth > 0){
//							sd.ids = new int[sd.depth];
//							Node parentNode = n;
//							for (int i = 0; i < sd.depth; i++) {
//								parentNode = parentNode.parent;
//								sd.ids[i] = parentNode.id;
//							}
							
							SpriteDefinition parentDef = sd;
							for (int i = 0; i < sd.depth; i++) {
								parentDef = parentDef.parentDef;
								parentDef.childIds.add(sd.id);;
							}
							
						}
					}
				}
				
			}else{
				String spriteName = n.identifier;
				
				for (SpriteDefinition sd : spriteDefinitions) {
					if (spriteName.equals(sd.spriteName)){
						sd.id = n.id;
					}
				}
				_setSpriteIDs(n.children, spriteDefinitions);
			}
		}
    }

    private void _parseSprites(ArrayList<Node> spriteNodes, SpriteDefinition parentDef, ArrayList<SpriteDefinition> spriteDefinitions){
    	for (Node n : spriteNodes) {	
			if (n.children.size() == 0){ //leaf node
				SpriteDefinition sd = new SpriteDefinition(n, true, parentDef);
				spriteDefinitions.add(sd);
			}else{
				SpriteDefinition sd = new SpriteDefinition(n, false, parentDef);
				spriteDefinitions.add(sd);
				_parseSprites(n.children, sd, spriteDefinitions);
			}
		}
    }

    
    public Class getSpriteClass(String refClass){
    	for (int i = 0; i < possibleSpriteClasses.length; i++) {
    		if (refClass.equals(possibleSpriteClasses[i].getSimpleName())){
    			return possibleSpriteClasses[i];
    		}
		}
    	throw new RuntimeErrorException(null, "Couldn't find sprite class!");
    }
    

    /**
     * Parses the interaction set.
     * @param elements all interactions defined for the game.
     */
    public ArrayList<Interaction> parseInteractionSet(ArrayList<Node> elements, ArrayList<SpriteDefinition> spriteDefinitions){
    	
        
        ArrayList<Interaction> interactions = new ArrayList<Interaction>();
    	
        for(Node n : elements){
            Interaction inter = VGDLFactory.GetInstance().makeInteraction(n);
            
            //Get sprites IDs
            String[] pieces = n.contentLine.split(" ");
        	String sprite1name = pieces[0];
        	String sprite2name = pieces[1];
        	int sprite1Id = -1;
        	int sprite2Id = -1;
        	for (SpriteDefinition sd : spriteDefinitions) {
                    if (sd.spriteName.equals(sprite1name)) sprite1Id = sd.id;
                    if (sd.spriteName.equals(sprite2name))  sprite2Id = sd.id;
                }
        	
        	inter.id1 = sprite1Id;
        	inter.id2 = sprite2Id;
        	interactions.add(inter);
        	
        }
        
        return interactions;
    }
//
//    /**
//     * Parses the level mapping.
//     * @param elements all mapping units.
//     */
    public void parseLevelMapping(ArrayList<Node> elements, ArrayList<SpriteDefinition> spriteDefinitions)
    {
    	boolean hasMappingForAvatar = false;
        for(Node n : elements)
        {	        
        	String[] pieces = n.contentLine.split(" ");
        	
        	char chKey = pieces[0].charAt(0);
        	if (chKey == "A".charAt(0)) hasMappingForAvatar = true;
        	
        	ArrayList<String> spriteIds = new ArrayList<String>();
        	
        	for (int i = 1; i < pieces.length; i++) {
        		spriteIds.add(pieces[i]);
			}
        	
			ArrayList<SpriteDefinition> sds = new ArrayList<SpriteDefinition>();
        	
        	for (String spriteId : spriteIds) {
                    for (SpriteDefinition sd : spriteDefinitions) {
                            if (sd.spriteName.equals(spriteId)){
                                    //Set mapping
                                    sds.add(sd);
                                    break;
                            }
                    }
                }
                currentGameMappings.put(chKey, sds);
        }

        //Add wall mapping
        char wallChar = "w".charAt(0);
        ArrayList<SpriteDefinition> wallSds = new ArrayList<SpriteDefinition>();
        wallSds.add(wallDefintion);
        currentGameMappings.put(wallChar, wallSds);
        
        //Add avatar mapping (if don't exist)
        if (!hasMappingForAvatar){
            char avatarChar = "A".charAt(0);
            ArrayList<SpriteDefinition> avatarSds = new ArrayList<SpriteDefinition>();
            avatarSds.add(avatarDefinition);
            currentGameMappings.put(avatarChar, avatarSds);
        }
        
    }

    /**
     * Parses the termination set.
     * @param elements all terminations defined for the game.
     */
    public ArrayList<Termination> parseTerminationSet(ArrayList<Node> elements)
    {
        ArrayList<Termination> terminations = new ArrayList<Termination>();
        for(Node n : elements)
        {
            Termination t = VGDLFactory.GetInstance().makeTermination(n);
            terminations.add(t);
        }
        return terminations;
    }




    public void parseLevel(Game game, String level_desc) {
        String[] lines = new IO().readFile(level_desc);
        
        if(lines == null) throw new RuntimeException();
            
        game.setLevelSize(lines[0].length(), lines.length);
       
        
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            for (int j = 0; j < line.length(); j++) {
                char ch = line.charAt(j);
                if (currentGameMappings.get(ch) == null) continue;
                for (SpriteDefinition sd : currentGameMappings.get(ch)){
                    game.addSprite(sd, j, i);
                }
            }
        }        
    }
        
        
        
        
     public int[] getGoalInteraction(String gamePath) {
        currID = 0;
        amountOfSpriteIDs = -1;
         
        
         int[] result = new int[2];
         
         
        String[] desc_lines = new IO().readFile(gamePath);
        
        ArrayList<SpriteDefinition> spriteDefinitions = null;
        ArrayList<Interaction> interactions = null;
        ArrayList<Termination> terminations = null;
        
        if(desc_lines != null)
        {
            Node rootNode = indentTreeParser(desc_lines);

            //Parse blocks of VGDL.
            for(Node n : rootNode.children)
            {
                if(n.identifier.equals("SpriteSet"))
                {
                    spriteDefinitions = parseSpriteSet(n.children, false);
                	
                }else if(n.identifier.equals("InteractionSet"))
                {
                    interactions = parseInteractionSet(n.children, spriteDefinitions);
                }else if(n.identifier.equals("LevelMapping"))
                {
//                    parseLevelMapping(n.children, spriteDefinitions);
                }else if(n.identifier.equals("TerminationSet"))
                {
                    terminations = parseTerminationSet(n.children);
                }
            }
        }
        
        //Find sprite that can cause win
        int winningSpriteId = -1;
        boolean goalIsCreatingSprite = false;
        for (Termination termination : terminations) {
             if (termination.win){
                 if (termination.getClass().getSimpleName().equals("SpriteCounter")){
                     SpriteCounter sc = (SpriteCounter) termination;
                    
//                     System.out.println(" sc.itype: " +  sc.itype + " sc.stype: " + sc.stype);
                     winningSpriteId = sc.itype;
                     if (termination.limit > 0) goalIsCreatingSprite = true;
                 }else if (termination.getClass().getSimpleName().equals("MultiSpriteCounter")){
                    MultiSpriteCounter msc = (MultiSpriteCounter) termination;
                    
//                     System.out.println(" sc.itype: " +  sc.itype + " sc.stype: " + sc.stype);
                    winningSpriteId = msc.itype1;
                    if (termination.limit > 0) goalIsCreatingSprite = true;
                 }
             }
         }
         
         //Check for interaction that can remove/create sprite
        for (Interaction inter : interactions) {
            if (inter.id1 != winningSpriteId) continue;
            
            String interClassName = inter.getClass().getSimpleName();
            if (goalIsCreatingSprite){
                if (interClassName.equals("TransformTo") || interClassName.equals("SpawnIfHasMore")){
                    result[0] = inter.id1;
                    result[1] = inter.id2;
                }
            }else{
                if (interClassName.equals("KillIfOtherHasMore") || interClassName.equals("KillIfFromAbove") || interClassName.equals("KillIfHasLess")
                         || interClassName.equals("KillIfHasMore")  || interClassName.equals("KillSprite")  || interClassName.equals("TransformTo")){
                    result[0] = inter.id1;
                    result[1] = inter.id2;
                }
            }
            
            
//            if (inter.id1 == winningSpriteId || inter.id2 == winningSpriteId){
//                
//            }
        }
         
         System.out.println("result: " + Arrays.toString(result));
         return result;
     }
}
