package controllers.puzzleSolverPlus;

import java.util.HashSet;

import tools.Vector2i;
import core.game.ForwardModel;
import java.util.ArrayList;

public class Node {
	ForwardModel fwdModel = null;
	public Vector2i avatarPos;
	public ArrayList<Integer> list = new ArrayList<Integer>();
//	public LinkedList<Integer> list = new LinkedList<Integer>();
	public HashSet<Moveable> moveables = new HashSet<Moveable>();
	
	public int lastAction = -1;
	
	public Node(ArrayList<Integer> list, HashSet<Moveable> moveables, int lastAction){
//	public Node(ForwardModel fwdModel, ArrayList<Integer> list, HashSet<Moveable> moveables, int lastAction){
//	public Node(ForwardModel fwdModel, LinkedList<Integer> list, HashSet<Moveable> moveables, int lastAction){
//	public Node(StateObservation state, LinkedList<Integer> list, int moveablesHashcode, int lastAction){
//		this.fwdModel = fwdModel;
		this.list = list;
		this.moveables = moveables;
//		this.moveablesHashcode = moveablesHashcode;
		this.lastAction = lastAction;
	}
	
        
        public Node(ForwardModel fwdModel, ArrayList<Integer> list, HashSet<Moveable> moveables, int lastAction){
		this.fwdModel = fwdModel;
		this.list = list;
		this.moveables = moveables;
//		this.moveablesHashcode = moveablesHashcode;
		this.lastAction = lastAction;
	}
	
	public void addAction(int act){
		list.add(act);
	}


	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((avatarPos == null) ? 0 : avatarPos.hashCode());
		result = prime * result
				+ ((moveables == null) ? 0 : moveables.hashCode());
//				+ moveablesHashcode;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (avatarPos == null) {
			if (other.avatarPos != null)
				return false;
		} else if (!avatarPos.equals(other.avatarPos))
			return false;
		if (moveables == null) {
			if (other.moveables != null)
				return false;
		} else if (!moveables.containsAll(other.moveables))
//		if (moveables.hashCode() != other.moveables.hashCode())
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String result = "Node: avatar pos: ";
		result += avatarPos.toString();
		result += ", moveables: ";
//		int moveablenr = 0;
//		for (Moveable m: moveables) {
//			result += "" + moveablenr + ": ";
//			result += m.toString();
//			moveablenr++;
//		}
		
		return result;
	}

        
        public ForwardModel getFwdModel(){
            
            
            return fwdModel;
        }
	
}
