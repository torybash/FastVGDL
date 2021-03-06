package fastVGDL.ontology.avatar;

import java.awt.Dimension;

import fastVGDL.tools.Vector2i;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 22/10/13
 * Time: 18:08
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class FlakAvatar extends HorizontalAvatar
{
    public String stype;

    public int itype;

    //This is the resource I need, to be able to shoot.
    public String ammo; //If ammo is null, no resource needed to shoot.
    public int ammoId;
    public int minAmmo; //-1 if not used. minimum amount of ammo needed for shooting.
    public int ammoCost; //1 if not used. amount of ammo to subtract after shooting once.


    public FlakAvatar(){}

    public FlakAvatar(Vector2i position, Dimension size)
    {
//        //Init the sprite
//        this.init(position, size);
//
//        //Specific class default parameter values.
//        loadDefaults();
//
//        //Parse the arguments.
//        this.parseParameters(cnt);
    }

//    protected void loadDefaults()
//    {
//        super.loadDefaults();
//        ammo = null;
//        ammoId = -1;
//        minAmmo = -1;
//        ammoCost = 1;
//        color = Types.GREEN;
//    }
//
//
//    public void postProcess()
//    {
//        //Define actions here first.
//        if(actions.size()==0)
//        {
//            actions.add(Types.ACTIONS.ACTION_USE);
//            actions.add(Types.ACTIONS.ACTION_LEFT);
//            actions.add(Types.ACTIONS.ACTION_RIGHT);
//        }
//
//        super.postProcess();
//
//        itype =  VGDLRegistry.GetInstance().getRegisteredSpriteValue(stype);
//        if(ammo != null)
//            ammoId = VGDLRegistry.GetInstance().getRegisteredSpriteValue(ammo);
//    }
//
//    public void update(Game game)
//    {
//        super.update(game);
//
//        if(!hasMoved)
//            updateUse(game);
//    }
//
//    public void updateUse(Game game)
//    {
//        if(Utils.processUseKey(game.ki.getMask()) && hasAmmo())
//        {
//            VGDLSprite added = game.addSprite(itype, new Vector2d(this.rect.x, this.rect.y));
//            if(added != null){ //singleton sprites could not add anything here.
//                reduceAmmo();
//                added.setFromAvatar(true);
//            }
//        }
//    }
//
//    private boolean hasAmmo()
//    {
//        if(ammo == null)
//            return true; //no ammo defined, I can shoot.
//
//        //If I have ammo, I must have enough resource of ammo type to be able to shoot.
//        if(resources.containsKey(ammoId))
//            if(minAmmo > -1)
//                return resources.get(ammoId) > minAmmo;
//            else
//                return resources.get(ammoId) > 0;
//
//        return false;
//    }
//
//    private void reduceAmmo()
//    {
//        if(ammo != null && resources.containsKey(ammoId))
//        {
//            resources.put(ammoId, resources.get(ammoId) - ammoCost);
//        }
//    }
//    public VGDLSprite copy()
//    {
//        FlakAvatar newSprite = new FlakAvatar();
//        this.copyTo(newSprite);
//        return newSprite;
//    }
//
//    public void copyTo(VGDLSprite target)
//    {
//        FlakAvatar targetSprite = (FlakAvatar) target;
//        targetSprite.stype = this.stype;
//        targetSprite.itype= this.itype;
//        targetSprite.ammo = this.ammo;
//        targetSprite.ammoId= this.ammoId;
//        targetSprite.ammoCost = this.ammoCost;
//        targetSprite.minAmmo= this.minAmmo;
//        super.copyTo(targetSprite);
//    }

}
