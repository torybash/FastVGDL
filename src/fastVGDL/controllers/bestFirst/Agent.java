package fastVGDL.controllers.bestFirst;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import fastVGDL.ontology.Types.ACTIONS;
import fastVGDL.ontology.core.Sprite;
import fastVGDL.parsing.core.SpriteGroup;
import fastVGDL.tools.ElapsedCpuTimer;
import fastVGDL.tools.ElapsedCpuTimer.TimerType;
import fastVGDL.tools.Vector2i;
import fastVGDL.core.game.ForwardModel;
import fastVGDL.core.game.Game;
import fastVGDL.core.player.AbstractPlayer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections; 
import fastVGDL.parsing.core.VGDLParser;
import fastVGDL.tools.IO;

public class Agent extends AbstractPlayer{

	
	ACTIONS[] actions;
	Random r;
    int blockSize = -1;
    int heightOfLevel = -1;
    int widthOfLevel = -1;
    
    boolean foundSolution = false;
    ArrayList<Integer> solution = new ArrayList<Integer>();
//    int[] solution;
    int solutionIndex = 0;
    
    ArrayList<Node> q = new ArrayList<Node>();
    HashSet<Node> visitedNodes = new HashSet<Node>();
    
    
    final int MAX_VISITED_NODES = 5000000;
    
    final int MAX_QUEUE_SIZE = 2000000;
	
    final boolean VERBOSE = true;
    final boolean LOOP_VERBOSE = false;
    
    final boolean depthFirst = false;
    
    boolean[] wallPositions;
    
    
    boolean initialized = false;
    ForwardModel fm = null;
    
    
    //Goal interaction
    int[] goalInterSprites;
//    int goalInterSprite1 = -1;
//    int goalInterSprite2 = -1;
    
    private void initialize(Game game){
    	ArrayList<ACTIONS> act = game.getAvailableActions();
    	actions = new ACTIONS[act.size()];
    	for(int i = 0; i < actions.length; ++i)
    	{
    		actions[i] = act.get(i);
    	}
    	r = new Random();
    	
    	
    	fm = new ForwardModel(game);
    	
    	widthOfLevel = game.levelSize.x;
    	heightOfLevel = game.levelSize.y;
    	wallPositions = new boolean[widthOfLevel * heightOfLevel + 1];
        for (int i = 0; i < fm.spriteGroups.length; i++) {
            SpriteGroup sg = fm.spriteGroups[i];

            for (Integer groupId : sg.sprites.keySet()) {
                Sprite sp = sg.sprites.get(groupId);

                if (sp.name.equals("wall")){
                    wallPositions[getPositionKey(sp.position)] = true;
                }
            }
        }
        
        goalInterSprites = VGDLParser.GetInstance().getGoalInteraction(game.gamePath);

        initialized = true;
    }
        
    public ACTIONS act(Game game, ElapsedCpuTimer ect) {

        if (!initialized)initialize(game);

        if (foundSolution){
            solutionIndex++;
            return actions[solution.get(solutionIndex)];			
        }

        if (q.isEmpty()){
            HashSet<Moveable> moveables = getMoveables(fm);
            Node currentNode = new Node(fm, heuristic(moveables), new ArrayList<Integer>(), moveables);
            q.add(currentNode);
        }

        int numIters = 0;
        while(!q.isEmpty()){
            Node n = q.remove(q.size()-1);

            if (LOOP_VERBOSE){
                System.out.println("*********ITERATION " + numIters + " ********");
                if (n.list.size() > 0) System.out.println("best current node: " + n);
            }
           

            if (n.fwdModel.won){
                solution = n.list;
                foundSolution = true;
                if (VERBOSE){ 
                    System.out.println("FOUND SOLUTION!");
                }
                return actions[solution.get(solutionIndex)];
            }

            for (int i = 0; i < actions.length; i++) {
                ForwardModel nextState = n.fwdModel.copy();
                nextState.advance(actions[i]);

                HashSet<Moveable> nextMoveables = getMoveables(nextState);
                
                Node new_n = new Node(nextState, heuristic(nextMoveables), ((ArrayList)n.list.clone()), nextMoveables);
                new_n.addAction(i);
             


                if (LOOP_VERBOSE){
                    System.out.println("Checking new_n: " + new_n);
                    System.out.println("visitedNodes.contains(new_n): " + visitedNodes.contains(new_n));
                    System.out.println("q.contains(new_n): " + q.contains(new_n));
                }
                if (!visitedNodes.contains(new_n) && !q.contains(new_n)){
                    visitedNodes.add(new_n);

                    q.add(new_n);
                    Collections.sort(q);


                    if (LOOP_VERBOSE) System.out.println("new_n action list: " + new_n.list);
                }
                if (LOOP_VERBOSE) System.out.println("------Q: " +q);
            }
            
            numIters++;
            
            if (VERBOSE && numIters%100 == 0){
                System.out.println("Visited nodes: " + visitedNodes.size() + ", Queue size: " + q.size());
            }
        }
        
        if (VERBOSE) System.out.println("Haven't found solution yet - returning ACTION_NIL, gametick: "  +game.gametick);
        if (VERBOSE) System.out.println("Visited nodes: " + visitedNodes.size() + ", Queue size: " + q.size());
        return ACTIONS.ACTION_NIL;	//haven't found solution yet - return nil
    }



    private void wasteTime(float factor) {
        System.out.println("COULDN'T FIND SOLUTION FOR GAME - WASTING TIME");
        int c = 0;
        for (int i = 0; i < 10000000 * factor; i++) {
                c = (int) Math.pow(i, 2);
        }
    }


	
    ArrayList<ACTIONS> getActionList(ArrayList<Integer> list){
    	ArrayList<ACTIONS> result = new ArrayList<ACTIONS>();
    	
    	for (Integer integer : list){
    		result.add(actions[integer]);
		}
    	return result;
    }
    

      
    private int getPositionKey(Vector2i vec){
    	if (vec == null) return 0;
    	if (vec.x < 0 || vec.y < 0 || vec.x >= widthOfLevel || vec.y >= heightOfLevel) return widthOfLevel * heightOfLevel;
		return (vec.x + vec.y * widthOfLevel);
    }
    
    private Vector2i getPositionFromKey(int key){
    	Vector2i result = new Vector2i();
    	result.x = (key % widthOfLevel) * blockSize;
    	result.y = (key / widthOfLevel) * blockSize;
		return result;
    	
    }
    
    private Vector2i changePosByAction(Vector2i pos, int action){
    	Vector2i newPos = pos.copy();
    	
    	switch (actions[action]) {
		case ACTION_DOWN:
			newPos.y += 1;
			break;
		case ACTION_LEFT:
			newPos.x -= 1;
			break;
		case ACTION_RIGHT:
			newPos.x += 1;
			break;
		case ACTION_UP:
			newPos.y -= 1;
			break;
		case ACTION_USE:
			break;
		default:
			break;
		}
    	return newPos;
    }
    
    
    private ACTIONS oppositeDirectionAction(int action){
    	switch (actions[action]) {
		case ACTION_DOWN:
			return ACTIONS.ACTION_UP;
		case ACTION_LEFT:
			return ACTIONS.ACTION_RIGHT;
		case ACTION_RIGHT:
			return ACTIONS.ACTION_LEFT;
		case ACTION_UP:
			return ACTIONS.ACTION_DOWN;
		default:
			break;
    	}
    	return ACTIONS.ACTION_NIL;
    }
    
    
    private ForwardModel playbackActions(ForwardModel currentState,	ArrayList<Integer> list) {
            ForwardModel result = currentState.copy();

            for (Integer act : list) {
                result.advance(actions[act]);
            }
            return result;
    }

        
    private float heuristic(HashSet<Moveable> moveables){

        float result = 0;
                
        ArrayList<Moveable> sprite1s = new ArrayList<Moveable>();
        ArrayList<Moveable> sprite2s = new ArrayList<Moveable>();
        for (Moveable moveable : moveables) {
            if (moveable.type == goalInterSprites[0]) sprite1s.add(moveable);
            if (moveable.type == goalInterSprites[1]) sprite2s.add(moveable);
        }
        
        for (Moveable sprite1 : sprite1s) {
            double shortestDist = Double.POSITIVE_INFINITY;
//            Moveable closestSprite = null;
            for (Moveable sprite2 : sprite2s) {
                double dist = sprite1.pos.sqDist(sprite2.pos);
                if (dist < shortestDist){
                    shortestDist = dist;
//                    closestSprite = sprite2;
                }
            }
            
            result += shortestDist;
        }
        
        return result;
    }
        
            
    HashSet<Moveable> getMoveables(ForwardModel fwdModel){
        HashSet<Moveable> result = new HashSet<Moveable>();
        for (int i = 0; i < fwdModel.spriteGroups.length; i++) {
            SpriteGroup sg = fwdModel.spriteGroups[i];
            for (Integer groupId : sg.sprites.keySet()) {
                Sprite sp = sg.sprites.get(groupId);
                if (!sp.name.equals("wall") && !sp.name.equals("ground")){
                    result.add(new Moveable(sp.position, sp.id));
                }   
            }
        }
        return result;
    }
    
}
