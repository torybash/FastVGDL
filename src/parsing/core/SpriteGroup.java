package parsing.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import ontology.core.Sprite;

/**
 * Created by Diego on 18/03/14.
 * This class encapsulates a SpriteGroup: a collection of Sprite objects
 * identified with an unique id. All sprites in the SpriteGroup are of the same
 * type.
 */
public class SpriteGroup
{
    /**
     * Type of sprite this class holds a collection of.
     */
	 public int id;
	 
	public ArrayList<Integer> childIds;

	public int idxIncrementer = -1;
    /**
     * Collection of sprites. They are maintained in a TreeMap, where the key is the
     * unique identifier for the given sprite (in the whole game).
     */
    public HashMap<Integer, Sprite> sprites;

	public boolean leafNode;
	
	public boolean isAvatar;

    /**
     * Creates a new SpriteGroup, specifying the type of sprites this will hold.
     * @param itype type of sprite for the SpriteGroup.
     */
    public SpriteGroup(int itype)
    {
        this.id = itype;
        sprites = new HashMap<Integer, Sprite>();
    }

    public SpriteGroup(int id, ArrayList<Integer> childIds) {
		this.id = id;
		this.childIds = childIds;
		sprites = new HashMap<Integer, Sprite>();
	}

	/**
     * Adds an sprite to the collection.
     * @param sprite Sprite to add.
     */
    public void addSprite(Sprite sprite)
    {
    	idxIncrementer++;
    	sprite.groupId = idxIncrementer;
        sprites.put(idxIncrementer, sprite);
    }


    /**
     * Adds a collection of sprites to this collection.
     * @param spritesToAdd Sprites to add.
     */
    public void addAllSprites(Collection<Sprite> spritesToAdd)
    {
        for(Sprite sp : spritesToAdd)
            sprites.put(sp.id, sp);
    }

     /**
     * Gets the collection of sprites, as a TreeMap [KEY => VALUE].
     * @return the TreeMap with the Sprites.
     */
    public HashMap<Integer, Sprite> getSprites()
    {
        return sprites;
    }

    /**
     * Gets the set of KEYs in an array.  It will return null if
     * the collection of sprites is empty.
     * @return the list of the sprite keys in this collection in an array.
     */
    public Integer[] getKeys()
    {
        int nSprites = sprites.size();
        if(nSprites == 0)
            return null;
        Integer[] keys = new Integer[nSprites];
        return sprites.keySet().toArray(keys);
    }

    /**
     * Gets an ordered iterator through all sprites. It will return null if
     * the collection of sprites is empty.
     * @return the list of the sprites in this collection in an iterator.
     */
    public Iterator<Sprite> getSpriteIterator()
    {
        if(numSprites() == 0)
            return null;
        return sprites.values().iterator();
    }

    /**
     * Removes an sprite indicated with its ID.
     * @param spriteId the id of the sprite to remove.
     */
    public void removeSprite(int spriteId)
    {
        sprites.remove(spriteId);
    }

    /**
     * Gets the type of this SpriteGroup.
     * @return the type of this sprite group
     */
    public int getItype()
    {
        return id;
    }

    /**
     * Retrieves a sprite given its unique ID. It'll return null if the
     * sprite is not in the collection.
     * @param spriteId ID of the sprite to retrieve.
     * @return the desired sprite.
     */
    public Sprite getSprite(int spriteId)
    {
        return sprites.get(spriteId);
    }

    /**
     * Clears the collection of sprites.
     */
    public void clear()
    {
        sprites.clear();
    }

    /**
     * Gets the number of sprites in the collection.
     * @return number of sprites in this collection.
     */
    public int numSprites()
    {
        return sprites.size();
    }

	public SpriteGroup copy() {
		SpriteGroup result = new SpriteGroup(id);
		result.childIds = childIds;
		result.leafNode = leafNode;
		result.isAvatar = isAvatar;
		
		result.sprites = new HashMap<Integer, Sprite>();
		for (Integer groupId : sprites.keySet()) {
			Sprite sp = sprites.get(groupId).copy();
		    result.sprites.put(groupId, sp);
		}
		result.idxIncrementer = idxIncrementer;

		return result;
	}
}
