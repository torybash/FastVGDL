package fastVGDL.ontology.sprites.producer;

import fastVGDL.ontology.core.Sprite;


/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 21/10/13
 * Time: 18:23
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Portal extends SpriteProducer
{
    public String stype;
    public int itype;

    
    
    public Portal(){}

//    {
//        //Init the sprite
//        this.init(position, size);
//
//        //Specific class default parameter values.
//        loadDefaults();
//
//        //Parse the arguments.
//        this.parseParameters(cnt);
//    }

//    protected void loadDefaults()
//    {
//        super.loadDefaults();
//        is_static = true;
//        portal = true;
//        color = Types.BLUE;
//    }
//
//    public void postProcess()
//    {
//        super.postProcess();
//        itype = VGDLRegistry.GetInstance().getRegisteredSpriteValue(stype);
//    }
//
//    public VGDLSprite copy()
//    {
//        Portal newSprite = new Portal();
//        this.copyTo(newSprite);
//        return newSprite;
//    }
//
//    public void copyTo(VGDLSprite target)
//    {
//        Portal targetSprite = (Portal) target;
//        targetSprite.stype = this.stype;
//        targetSprite.itype = this.itype;
//        super.copyTo(targetSprite);
//    }


	public Sprite copy()
	{
	    Portal newSprite = new Portal();
	    this.copyTo(newSprite);
	    return newSprite;
	}
	
	public void copyTo(Sprite target)
	{
		Portal targetSprite = (Portal) target;
		targetSprite.stype = stype;
		targetSprite.itype = itype;
		
	    super.copyTo(targetSprite);
	}

}
