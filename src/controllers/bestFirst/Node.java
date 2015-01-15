package controllers.bestFirst;

import controllers.puzzleSolverPlus.*;
import java.util.HashSet;

import tools.Vector2i;
import core.game.ForwardModel;
import java.util.ArrayList;
import java.util.Objects;
import ontology.core.Sprite;
import parsing.core.SpriteGroup;

public class Node implements Comparable {
    public ForwardModel fwdModel = null;
    public float value;
    public ArrayList<Integer> list = new ArrayList<Integer>();

    public HashSet<Moveable> moveables = new HashSet<Moveable>();


    public Node(ForwardModel fwdModel, float value, ArrayList<Integer> list, HashSet<Moveable> moveables){
            this.fwdModel = fwdModel;
            this.value = value;
            this.list = list;
            this.moveables = moveables;
    }

    public void addAction(int act){
            list.add(act);
    }
    

	

 
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.moveables);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (!Objects.equals(this.moveables, other.moveables)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    @Override
    public int compareTo(Object o) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
    
        Node otherNode = (Node) o;
        
        if (value > otherNode.value) return BEFORE;
        else if (value < otherNode.value) return AFTER;
        else return EQUAL;
    }

    @Override
    public String toString() {
        String result = "[Node: ";
        result += "tick="+fwdModel.gametick;
        result += ", avatar pos="+fwdModel.avatarSprite.position;
        result += ", action count: " + list.size();
        result += ", last action="+list.get(list.size()-1);
        result += ", value="+value;
        result += "]";
        return result; //To change body of generated methods, choose Tools | Templates.
    }

	
    
}
