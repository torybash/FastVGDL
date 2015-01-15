package ontology.core;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import ontology.Types;
import parsing.core.Node;
import parsing.core.VGDLParser;
import tools.Vector2i;

public class SpriteDefinition {
	
	public int id = -1;
	public ArrayList<Integer> childIds = new ArrayList<Integer>();
	public String spriteName = "";
	public Class spriteClass = null;
	public HashMap<String, String> parameters = new HashMap<String, String>();
	public int depth = 0;
	public boolean leafNode = false;
	public SpriteDefinition parentDef = null;
	public boolean isAvatar = false;
        
        public Sprite defaultSprite = null;
        
        
	
	public SpriteDefinition(Node n, boolean leafNode, SpriteDefinition parentDef){
		
		String pieces[] = n.contentLine.split(" ");
		spriteName = pieces[0].trim();

		this.leafNode = leafNode;
		this.parentDef = parentDef;
		
		if (!leafNode) return;
		
		//Take data (class/parameters) from parent(s)	--2 layers must be enough!
		if (n.parent != null && n.parent.set == Types.VGDL_SPRITE_SET && n.parent.isDefinition){
			depth = 1;
			if (n.parent.parent != null && n.parent.parent.set == Types.VGDL_SPRITE_SET && n.parent.parent.isDefinition){
				depth = 2;
				spriteClass = getSpriteClass(n.parent.parent);
				addParameters(n.parent.parent);
			}
			Class sc = getSpriteClass(n.parent);
			if (sc != null) spriteClass = sc;
			addParameters(n.parent);
		}
		
		Class sc = getSpriteClass(n);
		if (sc != null) spriteClass = sc;
		addParameters(n);
		
		if (spriteClass != null && spriteClass.getSimpleName().contains("vatar")) isAvatar = true;
                
                
                
                makeDefaultSprite();
                

	}
	
	
	
   
        
	public SpriteDefinition(String name) {
		spriteName = name;
		if (name == "avatar"){
			spriteClass = VGDLParser.GetInstance().getSpriteClass("MovingAvatar");
			parameters.put("img", "avatar");
			isAvatar = true;
		}else if (name == "wall"){
			spriteClass = VGDLParser.GetInstance().getSpriteClass("Immovable");
			parameters.put("img", "wall");
		}
		leafNode = true;
                makeDefaultSprite();
	}


        
        private void makeDefaultSprite(){
            		try {
			Constructor spriteConstructor = spriteClass.getConstructor();
			defaultSprite = (Sprite) spriteConstructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		defaultSprite.id = id;
		defaultSprite.name = spriteName;
		defaultSprite.position = new Vector2i();
                defaultSprite.lastPosition = new Vector2i();
                        
		if (isAvatar){
			defaultSprite.isAvatar = true;
			if (defaultSprite.img == null) defaultSprite.img = "avatar";
		}
		
		defaultSprite.parseParameters(parameters);
        }

	private void addParameters(Node n) {
		String pieces[] = n.contentLine.split(" ");
		
		
        for(int i = 1; i < pieces.length; ++i){
            String piece = pieces[i].trim();
            if(piece.contains("=")){
            	String[] keyVal = piece.split("=");
         
            	parameters.put(keyVal[0], keyVal[1]);
            }
        }
		
	}



	Class getSpriteClass(Node n){
		String[] pieces =  n.contentLine.split(" ");
		
		if (pieces.length < 3) return null;
		String spriteClassName = pieces[2];
		if (!spriteClassName.contains("=")){
			return VGDLParser.GetInstance().getSpriteClass(spriteClassName);
		}
		return null;
	}
	
	
	public String toString() {
		return "SpriteDefinition: " + spriteName + " > " + spriteClass + " id: " + id + " childIds: " + childIds + ", isAvatar: " + isAvatar;
	}

	
}
