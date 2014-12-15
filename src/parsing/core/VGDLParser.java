package parsing.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.management.RuntimeErrorException;

import ontology.Types;
import ontology.avatar.FlakAvatar;
import ontology.avatar.HorizontalAvatar;
import ontology.avatar.MovingAvatar;
import ontology.avatar.NoisyRotatingFlippingAvatar;
import ontology.avatar.RotatingAvatar;
import ontology.avatar.RotatingFlippingAvatar;
import ontology.avatar.VerticalAvatar;
import ontology.avatar.oriented.AimedAvatar;
import ontology.avatar.oriented.AimedFlakAvatar;
import ontology.avatar.oriented.InertialAvatar;
import ontology.avatar.oriented.MarioAvatar;
import ontology.avatar.oriented.MissileAvatar;
import ontology.avatar.oriented.OrientedAvatar;
import ontology.avatar.oriented.ShootAvatar;
import ontology.core.SpriteDefinition;
import ontology.core.Termination;
import ontology.effects.Interaction;
import ontology.sprites.Conveyor;
import ontology.sprites.Door;
import ontology.sprites.Flicker;
import ontology.sprites.Immovable;
import ontology.sprites.OrientedFlicker;
import ontology.sprites.Passive;
import ontology.sprites.Resource;
import ontology.sprites.ResourcePack;
import ontology.sprites.Spreader;
import ontology.sprites.missile.ErraticMissile;
import ontology.sprites.missile.Missile;
import ontology.sprites.missile.RandomMissile;
import ontology.sprites.missile.Walker;
import ontology.sprites.missile.WalkerJumper;
import ontology.sprites.npc.AlternateChaser;
import ontology.sprites.npc.Chaser;
import ontology.sprites.npc.Fleeing;
import ontology.sprites.npc.RandomAltChaser;
import ontology.sprites.npc.RandomInertial;
import ontology.sprites.npc.RandomNPC;
import ontology.sprites.producer.Bomber;
import ontology.sprites.producer.Portal;
import ontology.sprites.producer.SpawnPoint;
import ontology.sprites.producer.SpriteProducer;
import tools.IO;
import tools.Utils;
import core.game.Game;

public class VGDLParser {

	public int currentSet;
	
	public int amountOfSpriteIDs = -1;
	ArrayList<SpriteDefinition> spritesDefinitions = new ArrayList<SpriteDefinition>();
	SpriteDefinition wallDefintion = null;
	SpriteDefinition avatarDefinition = null;

	ArrayList<Interaction> interacts = new ArrayList<Interaction>();
	ArrayList<Termination> terms = new ArrayList<Termination>();
	
	ArrayList<Interaction> interactions = null;
	
	HashMap<Character, ArrayList<SpriteDefinition>> mappings = new HashMap<Character, ArrayList<SpriteDefinition>>();
	
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
    	
        String[] desc_lines = new IO().readFile(gamedesc_file);
        
        ArrayList[] elements = new ArrayList[4];
        
        if(desc_lines != null)
        {
            Node rootNode = indentTreeParser(desc_lines);

            
            //Parse here game and arguments of the first line
//            game = VGDLFactory.GetInstance().createGame((GameContent) rootNode.content);

            //Parse here blocks of VGDL.
            for(Node n : rootNode.children)
            {
                if(n.identifier.equals("SpriteSet"))
                {
                	parseSpriteSet(n.children);
                	
                }else if(n.identifier.equals("InteractionSet"))
                {
            
                    parseInteractionSet(n.children);
                }else if(n.identifier.equals("LevelMapping"))
                {
                    parseLevelMapping(n.children);
                }else if(n.identifier.equals("TerminationSet"))
                {
                    parseTerminationSet(n.children);
                }
            }
        }

        
        System.out.println("DONE PARSING");
        System.out.println("amountOfSpriteIDs: " + amountOfSpriteIDs);
        
        game.initialize(spritesDefinitions, interactions, terms, amountOfSpriteIDs);
        
        return game;
    }
    
    




	private Node indentTreeParser(String[] lines)
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
    private void parseSpriteSet(ArrayList<Node> spriteNodes)
    {

       
        _parseSprites(spriteNodes, null);

    	//Check for avatar - make one if don't exist
        boolean hasAvatar = false;
    	for (SpriteDefinition sd : spritesDefinitions) {
    		if (!sd.leafNode) continue;
    		if (sd.spriteClass.getSimpleName().contains("vatar")){
    			hasAvatar = true;
    			avatarDefinition = sd;
    			break;
    		}
    	}
    	if (!hasAvatar){
    		avatarDefinition = new SpriteDefinition("avatar");
    		spritesDefinitions.add(avatarDefinition);
    	}
    	
    	
    	//Make wall
    	wallDefintion = new SpriteDefinition("wall");
    	spritesDefinitions.add(wallDefintion);
        
    	
    	//Set IDs
    	setSpriteIDs(spriteNodes, hasAvatar);

        //Register all spriteDefinitions
        for (SpriteDefinition sd : spritesDefinitions) {
        	VGDLRegistry.GetInstance().registerSprite(sd);
		}
        
    }
    
    
    private void setSpriteIDs(ArrayList<Node> spriteNodes, boolean hasAvatar){
    	
    	_setSpriteIDs(spriteNodes);
    	
    	SpriteDefinition wallSd = spritesDefinitions.get(spritesDefinitions.size()-1);
    	wallSd.id = 0;
    	
    	if (!hasAvatar){ //avatar was generated, so ID need to be generated as well
    		SpriteDefinition avatarSd = spritesDefinitions.get(spritesDefinitions.size()-2);
    		avatarSd.id = ++currID;
    	}
    	
    	amountOfSpriteIDs = ++currID;
    }
    
    int currID = 0;
    private void _setSpriteIDs(ArrayList<Node> spriteNodes){
    	for (Node n : spriteNodes) {	
    		n.id = ++currID;
    		
			if (n.children.size() == 0){ //leaf node
				String spriteName = n.identifier;

				for (SpriteDefinition sd : spritesDefinitions) {
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
				
				for (SpriteDefinition sd : spritesDefinitions) {
					if (spriteName.equals(sd.spriteName)){
						sd.id = n.id;
					}
				}
				_setSpriteIDs(n.children);
			}
		}
    }

    private void _parseSprites(ArrayList<Node> spriteNodes, SpriteDefinition parentDef){
    	for (Node n : spriteNodes) {	
			if (n.children.size() == 0){ //leaf node
				SpriteDefinition sd = new SpriteDefinition(n, true, parentDef);
				spritesDefinitions.add(sd);
			}else{
				SpriteDefinition sd = new SpriteDefinition(n, false, parentDef);
				spritesDefinitions.add(sd);
				_parseSprites(n.children, sd);
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
    private void parseInteractionSet(ArrayList<Node> elements){
    	interactions = new ArrayList<Interaction>();
    	
        for(Node n : elements){
            Interaction inter = VGDLFactory.GetInstance().makeInteraction(n);
            
            //Get sprites IDs
            String[] pieces = n.contentLine.split(" ");
        	String sprite1name = pieces[0];
        	String sprite2name = pieces[1];
        	int sprite1Id = -1;
        	int sprite2Id = -1;
        	for (SpriteDefinition sd : spritesDefinitions) {
				if (sd.spriteName.equals(sprite1name)) sprite1Id = sd.id;
				if (sd.spriteName.equals(sprite2name))  sprite2Id = sd.id;
			}
        	
        	inter.id1 = sprite1Id;
        	inter.id2 = sprite2Id;
//        	if (collisionEffects[sprite1Id][sprite2Id] == null) collisionEffects[sprite1Id][sprite2Id] = new ArrayList<Interaction>();
        	interactions.add(inter);
        	
//        	System.out.println(sprite1name + ": " + sprite1Id + ", " + sprite2name + ": " + sprite2Id);
//        	//Set colllision effect / EOS effect
//        	if (collisionEffects[sprite1Id * spritesDefinitions.size() + sprite2Id] == null)
//        		collisionEffects[sprite1Id * spritesDefinitions.size() + sprite2Id] = new ArrayList<Interaction>();
//			collisionEffects[sprite1Id * spritesDefinitions.size() + sprite2Id].add(inter);
        }
    }
//
//    /**
//     * Parses the level mapping.
//     * @param elements all mapping units.
//     */
    private void parseLevelMapping(ArrayList<Node> elements)
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
				for (SpriteDefinition sd : spritesDefinitions) {
					if (sd.spriteName.equals(spriteId)){
						//Set mapping
						sds.add(sd);
						break;
					}
				}
			}
			mappings.put(chKey, sds);
        }

        //Add wall mapping
        char wallChar = "w".charAt(0);
        ArrayList<SpriteDefinition> wallSds = new ArrayList<SpriteDefinition>();
        wallSds.add(wallDefintion);
        mappings.put(wallChar, wallSds);
        
        //Add avatar mapping (if don't exist)
        if (!hasMappingForAvatar){
            char avatarChar = "A".charAt(0);
            ArrayList<SpriteDefinition> avatarSds = new ArrayList<SpriteDefinition>();
            avatarSds.add(avatarDefinition);
            mappings.put(avatarChar, avatarSds);
        }
        
    }

    /**
     * Parses the termination set.
     * @param elements all terminations defined for the game.
     */
    private void parseTerminationSet(ArrayList<Node> elements)
    {
        for(Node n : elements)
        {
        	Termination t = VGDLFactory.GetInstance().makeTermination(n);
        	terms.add(t);
        }

    }




	public void parseLevel(Game game, String level_desc) {
        String[] lines = new IO().readFile(level_desc);
        
        if(lines == null) throw new RuntimeException();
            
        game.setLevelSize(lines[0].length(), lines.length);
       
        
        for (int i = 0; i < lines.length; i++) {
        	String line = lines[i];
        	
        	for (int j = 0; j < line.length(); j++) {
				char ch = line.charAt(j);
				if (mappings.get(ch) == null) continue;
				for (SpriteDefinition sd : mappings.get(ch)){
					game.addSprite(sd, j, i);
				}
			}
        	
		}        
	}
}
