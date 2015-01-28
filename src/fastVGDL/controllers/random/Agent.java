package fastVGDL.controllers.random;

import java.util.Random;

import fastVGDL.ontology.Types;
import fastVGDL.ontology.Types.ACTIONS;
import fastVGDL.tools.ElapsedCpuTimer;
import fastVGDL.core.game.Game;
import fastVGDL.core.player.AbstractPlayer;

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