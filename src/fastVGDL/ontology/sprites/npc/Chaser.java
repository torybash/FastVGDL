package fastVGDL.ontology.sprites.npc;

import java.awt.Dimension;
import java.util.ArrayList;

import fastVGDL.tools.Vector2i;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 21/10/13
 * Time: 18:14
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Chaser extends RandomNPC
{
    public boolean fleeing;
    public String stype;
    public int itype;

//    ArrayList<VGDLSprite> targets;
    ArrayList<Vector2i> actions;

    public Chaser(){}

    public Chaser(Vector2i position, Dimension size)
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
//        fleeing = false;
//        targets = new ArrayList<VGDLSprite>();
//        actions = new ArrayList<Vector2d>();
//    }
//
//    public void postProcess()
//    {
//        super.postProcess();
//        //Define actions here.
//        itype =  VGDLRegistry.GetInstance().getRegisteredSpriteValue(stype);
//    }
//
//    public void update(Game game)
//    {
//        actions.clear();
//
//        //passive moment.
//        super.updatePassive();
//
//        //Get the closest targets
//        closestTargets(game);
//        for(VGDLSprite target : targets)
//        {
//            //Update the list of actions that moves me towards each target
//            movesToward(target);
//        }
//
//        //Choose randomly an action among the ones that allows me to chase.
//        Vector2d act;
//        if(actions.size() == 0)
//        {
//            //unless, no actions really take me closer to anybody!
//            act = (Vector2d) Utils.choice(Types.BASEDIRS,game.getRandomGenerator());
//        }else{
//            act = Utils.choice(actions,game.getRandomGenerator());
//        }
//
//        //Apply the action to move.
//        this.physics.activeMovement(this, act, this.speed);
//    }
//
//    protected void movesToward(VGDLSprite target)
//    {
//        double distance = this.physics.distance(rect, target.rect);
//        for(Vector2d act : Types.BASEDIRS)
//        {
//            //Calculate the distance if I'd apply this move.
//            Rectangle r = new Rectangle(this.rect);
//            r.translate((int)act.x, (int)act.y);
//            double newDist = this.physics.distance(r, target.rect);
//
//            //depending on getting me closer/farther, if I'm fleeing/chasing, add move:
//            if(fleeing && distance<newDist)
//                actions.add(act);
//            if(!fleeing && distance>newDist)
//                actions.add(act);
//        }
//    }
//
//    /**
//     * Sets a list with the closest targets (sprites with the type 'stype'), by distance
//     * @param game game to access all sprites
//     */
//    protected void closestTargets(Game game)
//    {
//        targets.clear();
//        double bestDist = Double.MAX_VALUE;
//
//        Iterator<VGDLSprite> spriteIt = game.getSpriteGroup(itype);
//        if(spriteIt != null) while(spriteIt.hasNext())
//        {
//            VGDLSprite s = spriteIt.next();
//            double distance = this.physics.distance(rect, s.rect);
//            if(distance < bestDist)
//            {
//                bestDist = distance;
//                targets.clear();
//                targets.add(s);
//            }else if(distance == bestDist){
//                targets.add(s);
//            }
//        }
//    }
//
//
//    public VGDLSprite copy()
//    {
//        Chaser newSprite = new Chaser();
//        this.copyTo(newSprite);
//        return newSprite;
//    }
//
//    public void copyTo(VGDLSprite target)
//    {
//        Chaser targetSprite = (Chaser) target;
//        targetSprite.fleeing = this.fleeing;
//        targetSprite.stype = this.stype;
//        targetSprite.itype = this.itype;
//        targetSprite.targets = new ArrayList<VGDLSprite>();
//        targetSprite.actions = new ArrayList<Vector2d>();
//        super.copyTo(targetSprite);
//    }

}
