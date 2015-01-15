package controllers.bestFirst;

import controllers.puzzleSolverPlus.*;
import java.util.HashSet;

import tools.Vector2i;
import core.game.ForwardModel;
import java.util.ArrayList;

public class Node {
	public ForwardModel fwdModel = null;
        public float value;
	public ArrayList<Integer> list = new ArrayList<Integer>();
	
        
        public Node(ForwardModel fwdModel, float value, ArrayList<Integer> list){
		this.fwdModel = fwdModel;
                this.value = value;
		this.list = list;
	}
	
	public void addAction(int act){
		list.add(act);
	}

	
}
