package controllers.random;

import java.util.Random;

import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.Game;
import core.player.AbstractPlayer;

public class Agent extends AbstractPlayer {

    public Agent() {}

    Random r = new Random();
    
    public Types.ACTIONS act(Game game, ElapsedCpuTimer elapsedTimer) {
//        int index = r.nextInt(stateObs.getAvailableActions().size());
//        return stateObs.getAvailableActions().get(index);
    	
    	
    	int index = r.nextInt(game.getAvailableActions().size());
    	return game.getAvailableActions().get(index);
    }
}