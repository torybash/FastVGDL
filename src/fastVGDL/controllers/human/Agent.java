package fastVGDL.controllers.human;

import java.util.Random;

import fastVGDL.ontology.Types;
import fastVGDL.ontology.Types.ACTIONS;
import fastVGDL.tools.ElapsedCpuTimer;
import fastVGDL.tools.Utils;
import fastVGDL.core.game.ForwardModel;
import fastVGDL.core.game.Game;
import fastVGDL.core.player.AbstractPlayer;

public class Agent extends AbstractPlayer {

    public Agent() {}

    Random r = new Random();
    
    public Types.ACTIONS act(Game game, ElapsedCpuTimer elapsedTimer) {
    	
    	
//    	ForwardModel fm = new ForwardModel(game);
//    	fm.advance(ACTIONS.ACTION_UP);
//    	System.out.println("Result of going up: " + fm.isEnded);
//    	
//    	ForwardModel fm2 = new ForwardModel(fm);
//    	fm2.advance(ACTIONS.ACTION_UP);
//    	System.out.println("result of going up twice: " + fm2.isEnded);
//    	
//    	ForwardModel fm3 = new ForwardModel(fm);
//    	fm3.advance(ACTIONS.ACTION_RIGHT);
//    	System.out.println("result of going up, then right: " + fm3.isEnded);
    	
//    	fm.advance(ACTIONS.ACTION_RIGHT);
//    	fm.advance(ACTIONS.ACTION_DOWN);
//    	fm.advance(ACTIONS.ACTION_RIGHT);
    	
//    	System.out.println(fm.isEnded);
    	
    	Types.ACTIONS action = Utils.processKeyMask(Game.ki.getMask());
        boolean useOn = Utils.processUseKey(Game.ki.getMask());


        if(action == Types.ACTIONS.ACTION_NIL && useOn)
            action = Types.ACTIONS.ACTION_USE;

        return action;
    
    }
}