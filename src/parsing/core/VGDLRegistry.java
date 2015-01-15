package parsing.core;

import java.util.HashMap;

import ontology.core.SpriteDefinition;

public class VGDLRegistry {

	
	static VGDLRegistry registry = null;
	
	HashMap<String, SpriteDefinition> spriteNameToDefinition = new HashMap<String, SpriteDefinition>();
	HashMap<String, Integer> spriteNameToId = new HashMap<String, Integer>();
	
	public static VGDLRegistry GetInstance(){
		if (registry == null) registry = new VGDLRegistry();
		return registry;
	}

	public SpriteDefinition getRegisteredSpriteDefinition(String stype) {
		return spriteNameToDefinition.get(stype);
	}
	
	public int getRegisteredSpriteId(String stype) {
		return spriteNameToId.get(stype);
	}
	
	public void registerSprite(SpriteDefinition sd){
		spriteNameToDefinition.put(sd.spriteName, sd);
		spriteNameToId.put(sd.spriteName, sd.id);
		System.out.println("Registering.. " + sd);
	}
	

	
}
