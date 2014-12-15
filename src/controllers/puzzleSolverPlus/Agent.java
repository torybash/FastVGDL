package controllers.puzzleSolverPlus;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import ontology.Types.ACTIONS;
import ontology.core.Sprite;
import parsing.core.SpriteGroup;
import tools.ElapsedCpuTimer;
import tools.ElapsedCpuTimer.TimerType;
import tools.Vector2i;
import core.game.ForwardModel;
import core.game.Game;
import core.player.AbstractPlayer;

public class Agent extends AbstractPlayer{

	
	ACTIONS[] actions;
	Random r;
    int blockSize = -1;
    int heightOfLevel = -1;
    int widthOfLevel = -1;
    
    boolean foundSolution = false;
    LinkedList<Integer> solution = new LinkedList<Integer>();
//    int[] solution;
    int solutionIndex = 0;
    
    ArrayDeque<Node> q = new ArrayDeque<Node>();
    HashSet<Node> visitedNodes = new HashSet<Node>();
    
    
    final int MAX_VISITED_NODES = 5000000;
    
    final int MAX_QUEUE_SIZE = 1000000;
	
    final boolean VERBOSE = true;
    final boolean LOOP_VERBOSE = false;
    
    final boolean depthFirst = false;
    
    boolean[] wallPositions;
    
    
    int[] solutionTest = null;
//    int ACTION_UP = 0; int ACTION_RIGHT = 1; int ACTION_DOWN = 2; int ACTION_LEFT = 3;
//    int[] solutionTest = new int[]{ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_DOWN, ACTION_DOWN, ACTION_DOWN, ACTION_DOWN, ACTION_DOWN, ACTION_LEFT, ACTION_DOWN, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_DOWN, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_UP, ACTION_UP, ACTION_LEFT, ACTION_LEFT, ACTION_DOWN, ACTION_UP, ACTION_RIGHT, ACTION_RIGHT, ACTION_DOWN, ACTION_DOWN, ACTION_LEFT, ACTION_LEFT, ACTION_UP, ACTION_LEFT, ACTION_LEFT, ACTION_DOWN, ACTION_RIGHT, ACTION_UP, ACTION_RIGHT, ACTION_UP, ACTION_RIGHT, ACTION_RIGHT, ACTION_DOWN, ACTION_DOWN, ACTION_LEFT, ACTION_LEFT, ACTION_LEFT, ACTION_UP, ACTION_LEFT, ACTION_LEFT, ACTION_LEFT, ACTION_LEFT, ACTION_LEFT, ACTION_DOWN, ACTION_DOWN, ACTION_RIGHT, ACTION_UP, ACTION_LEFT, ACTION_UP, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_DOWN, ACTION_RIGHT, ACTION_RIGHT, ACTION_RIGHT, ACTION_UP, ACTION_UP, ACTION_LEFT, ACTION_LEFT, ACTION_DOWN, ACTION_UP, ACTION_RIGHT, ACTION_RIGHT, ACTION_DOWN, ACTION_DOWN, ACTION_LEFT, ACTION_LEFT};

    
//    public Agent(Game game, ElapsedCpuTimer elapsedTimer)
//    {
//        ArrayList<Types.ACTIONS> act = so.getAvailableActions();
//        actions = new Types.ACTIONS[act.size()];
//        for(int i = 0; i < actions.length; ++i)
//        {
//            actions[i] = act.get(i);
//        }
//    	r = new Random();
//    	
//    	blockSize = so.getBlockSize();
//    	heightOfLevel = (int) (so.getWorldDimension().height / so.getBlockSize());
//    	widthOfLevel = (int) (so.getWorldDimension().width / so.getBlockSize());
//    	
//    	wallPositions = new boolean[heightOfLevel * widthOfLevel + 1];
//    	
//		if (so.getImmovablePositions() != null){
//			for (ArrayList<Observation> arrayList : so.getImmovablePositions()) {
//				for (Observation observation : arrayList) {
//					if (observation.itype != 0) continue; //<- wall
//					wallPositions[getPositionKey(observation.position)] = true;
//				}
//			}
//		}    	
//    }
    boolean initialized = false;
	ForwardModel fm = null;
    
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
		
		initialized = true;
    }
        
	public ACTIONS act(Game game, ElapsedCpuTimer ect) {

		if (!initialized){
			initialize(game);
		}
		
		
//		if (foundSolution && solutionIndex< solution.size()-1){
		if (foundSolution){
			solutionIndex++;
			return actions[solution.get(solutionIndex)];
//			return actions[solution[solutionIndex]];
			
		}
				
		if (q.isEmpty()){
			Node currentNode = new Node(fm, new LinkedList<Integer>(), new HashSet<Moveable>(), -1);
//			Node currentNode = new Node(so, new int[MAX_ACTIONS], 0, -1);
	        q.add(currentNode);
		}
		
        double avgTimeTaken = 0, acumTimeTaken = 0;
        long remaining = ect.remainingTimeMillis();
        int numIters = 0, remainingLimit = 5, lastDepth = -1;
        ElapsedCpuTimer elapsedTimerIteration = new ElapsedCpuTimer(TimerType.CPU_TIME);
        boolean queueEmpty = false;
        while(remaining > 2*avgTimeTaken && remaining > remainingLimit)
        {
            if (LOOP_VERBOSE) System.out.println("START LOOP--" + elapsedTimerIteration.elapsedMillis() + " --> " + acumTimeTaken + " (" + remaining + "),  avgTimeTaken: " + avgTimeTaken + " - iters: " +numIters);
            if (LOOP_VERBOSE) System.out.println("visited node size: " + visitedNodes.size());
            
        	boolean nodeAlreadyExists = false;
            boolean moveablesHaveChanged = false;
            boolean nodeInteresting = true;
        	
        	if (q.isEmpty()){
        		if (VERBOSE) System.out.println("QUEUE EMPTY!!!");
        		return ACTIONS.ACTION_NIL; 
        	}
        	
        	Node n = null;
    		n = q.pollFirst();
        	
        	
        	if (n.lastAction >= 0){
        		n.list = (LinkedList<Integer>) n.list.clone();
//        		n.list = n.list.clone();
	        	n.addAction(n.lastAction);
	        	n.fwdModel = n.fwdModel.copy();
        	}
        	
        	int d = n.list.size();
                        
        	HashSet<Moveable> lastMoveables = (HashSet<Moveable>) n.moveables.clone();

        	if (d > 0){
            	n.fwdModel.advance(actions[n.lastAction]);
            }

            n.moveables = getMoveables(n.fwdModel);
            n.avatarPos = n.fwdModel.avatarSprite.position.copy();
        	//Node has now finished initializing
        	
        	
        	if (!lastMoveables.equals(n.moveables)){
        		moveablesHaveChanged = true;
        		nodeInteresting = true;
        	}

//        	System.out.println("5 moveable");
//        	for (Moveable moveable : n.moveables) {
//				if (moveable.type == 5){
//					System.out.println(moveable);
//				}
//			}
        	
        	if (LOOP_VERBOSE){
        		System.out.println("---New node initialized!--- (iteration: " + numIters + " - q length: + " + q.size() +")");
        		System.out.println("Node action list: " + getActionList(n.list));
	            System.out.println("Avatar pos: " + n.avatarPos);
	            System.out.println("Moveables: " + n.moveables);
	            System.out.println((moveablesHaveChanged ? "Moveable HAVE CHANGED" : "Moveables did not change"));
        	}
        	
        	if (visitedNodes.contains(n)){
        		if (LOOP_VERBOSE){
	        		Node existingNode = null;
	        		for (Node node : visitedNodes) if (node.equals(n)) existingNode = node;
	        		System.out.println("VISITED ALREADY NODES CONTAIN NODE!! (visited node size: " + visitedNodes.size() + ")");
	        		System.out.println("orig actions: "+getActionList(existingNode.list));
	        		System.out.println("new actions: "+getActionList(n.list));
        		}
        		nodeAlreadyExists = true;
        	}


        	if (!nodeAlreadyExists){
	        	boolean gameLost = false;
	        	if (n.fwdModel.isEnded){
	        		if (n.fwdModel.won){
		        		solution = n.list;
		        		foundSolution = true;
		        		if (VERBOSE){ 
		        			System.out.println("FOUND SOLUTION!");
		        			System.out.println("Avatar pos: " + n.fwdModel.avatarSprite.position);
			        		System.out.println("Solution: " + getActionList(solution));
			        		System.out.println("Solution length: " + n.list.size());
//			        		System.out.println("Solution length: " + n.currIdx);
			        		System.out.println("Visited nodes: " + visitedNodes.size());
			        		System.out.println("Queue size: " + q.size());
			        		System.out.println("Moveables: " + n.moveables);
			        		System.out.println(n.fwdModel.spriteGroups[5].sprites);
		        		}
		        		return actions[solution.get(solutionIndex)];
	        		}else{
	        			gameLost = true;
	        		}
	        	}
	
	        	//Expansion
	        	if (!gameLost){
	        		
	    			if (solutionTest != null){
	    				Node n_new = new Node(n.fwdModel, n.list, n.moveables, solutionTest[solutionIndex]);
	    				q.add(n_new);
	    				solutionIndex++;
	    			}else{
	    			
		    		for (int i = 0; i < actions.length; i++) {
		    			//Dont expand into walls
		    			Vector2i expectedNewPos = changePosByAction(n.avatarPos, i);
		    			if (wallPositions[getPositionKey(expectedNewPos)]){
//		    				System.out.println();
		    				continue;
		    			}
		    			
		    			//Dont expand in opposite direction, if moveables haven't changed
		    			if (!moveablesHaveChanged){
		    				if (n.lastAction >= 0 && actions[i] == oppositeDirectionAction(n.lastAction)){
//		    					System.out.println("Skipping expansion in opposite direction");
		    					continue;
		    				}
		    			}

		    			
		    			Node n_new = new Node(n.fwdModel, n.list, n.moveables, i);
//			    			n_new.currIdx = n.currIdx;
//			        		if (nodeInteresting){
//			        			q.addFirst(n_new);
//			        		}else{
//			        			q.add(n_new);
//			        		}
		    			if (depthFirst || q.size() > MAX_QUEUE_SIZE){
		    				q.addFirst(n_new);
		    			}else{
		    				q.add(n_new);
		    			}
		    		}
		    		
	    			}
	        	}
	    		
	    		visitedNodes.add(n);
        	}
        	
        	n.fwdModel = null;
        	
            if (!nodeAlreadyExists) numIters++;
            acumTimeTaken = (elapsedTimerIteration.elapsedMillis());
            avgTimeTaken = numIters == 0 ? 0 : acumTimeTaken/numIters;
            remaining = ect.remainingTimeMillis();
            lastDepth = d;
            if (LOOP_VERBOSE) System.out.println("END LOOP--" + elapsedTimerIteration.elapsedMillis() + " --> " + acumTimeTaken + " (" + remaining + "),  avgTimeTaken: " + avgTimeTaken + " - iters: " +numIters);
        }
        
//        if (VERBOSE) printVisitedNodes();
        
        if (VERBOSE) System.out.println("Haven't found solution yet - returning ACTION_NIL, gametick: "  +game.gametick);
        if (VERBOSE) System.out.println("Visited nodes: " + visitedNodes.size() + ", Queue size: " + q.size() + ", lastDepth: " + lastDepth);
        return ACTIONS.ACTION_NIL;	//haven't found solution yet - return nil
  	}



	private void wasteTime(float factor) {
		System.out.println("COULDN'T FIND SOLUTION FOR GAME - WASTING TIME");
		int c = 0;
		for (int i = 0; i < 10000000 * factor; i++) {
			c = (int) Math.pow(i, 2);
		}
		
	}

	HashSet<Moveable> getMoveables(ForwardModel fwdModel){
		
		HashSet<Moveable> result = new HashSet<Moveable>();
		
		for (int i = 0; i < fwdModel.spriteGroups.length; i++) {
			SpriteGroup sg = fwdModel.spriteGroups[i];
			
			for (Integer groupId : sg.sprites.keySet()) {
				Sprite sp = sg.sprites.get(groupId);
								
				if (!sp.isAvatar && !sp.name.equals("wall") && !sp.name.equals("ground")){
					result.add(new Moveable(sp.position, sp.id));
				}
			}
		}
		
//		
//		if (so.getMovablePositions() != null){
//			for (ArrayList<Observation> arrayList : so.getMovablePositions()) {
//				for (Observation observation : arrayList) {
//					result.add(new Moveable(observation.position, observation.itype));
//				}
//			}
//		}
//			
//				
//		if (so.getImmovablePositions() != null){
//			for (ArrayList<Observation> arrayList : so.getImmovablePositions()) {
//				for (Observation observation : arrayList) {
//					if (observation.itype == 0) continue; //<- wall
//					result.add(new Moveable(observation.position, observation.itype));
//				}
//			}
//		}
//		
//		if (so.getResourcesPositions() != null){
//			for (ArrayList<Observation> arrayList : so.getResourcesPositions()) {
//				for (Observation observation : arrayList) {
//					result.add(new Moveable(observation.position, observation.itype));
//				}
//			}
//		}

		return result;
	}
	
	
    ArrayList<ACTIONS> getActionList(LinkedList<Integer> list){
    	ArrayList<ACTIONS> result = new ArrayList<ACTIONS>();
    	
    	for (Integer integer : list){
    		result.add(actions[integer]);
		}
    	return result;
    }
//    
//    ArrayList<ACTIONS> getActionList(int[] list){
//    	ArrayList<ACTIONS> result = new ArrayList<ACTIONS>();
//    	
//		for (int i = 0; i < list.length; i++) {
//			ACTIONS act = actions[list[i]];
//			result.add(act);
//		}
//    	return result;
//    }
//    
//    private double value(StateObservation a_gameState) {
//
//        boolean gameOver = a_gameState.isGameOver();
//        Types.WINNER win = a_gameState.getGameWinner();
//        double rawScore = a_gameState.getGameScore();
//
//        if(gameOver && win == Types.WINNER.PLAYER_LOSES)
//            return -100000;
//
//        if(gameOver && win == Types.WINNER.PLAYER_WINS){
//            return 1000 + rawScore; //rawScore + a_gameState.getGameTick() > 1000 ? 100000 : 0;  //WINNING IS ONLY GOOD LATE IN GAME
//        }
//            
//        return rawScore;
//    }
//    
//    private void printVisitedNodes(){
//
//    	System.out.println("------------------------");
//    	System.out.println("--PRINTING VISITED NODES");
//    	System.out.println("------------------------");
//    	for (Node n : visitedNodes) {
//    		printNode(n);
//		}
//    }
//    
//    private void printNode(Node n){
//    	int[][] map = new int[widthOfLevel][];
//    	for (int i = 0; i < widthOfLevel; i++) {
//    		map[i] = new int[heightOfLevel];
//		}
//    	
//       	int avatar_x = (int) (n.avatarPos.x / blockSize);
//    	int avatar_y = (int) (n.avatarPos.y / blockSize);
//    	
//    	map[avatar_x][avatar_y] = "A".charAt(0) - 36;
//    	
//    	System.out.println("Avatar pos: " + n.avatarPos + " -> int pos: " + avatar_x + ", " + avatar_y);
////    	for (Moveable m : n.moveables) {
////    		
////    		int x = (int) (m.pos.x / blockSize);
////    		int y = (int) (m.pos.y / blockSize);
////    		System.out.println(m + " -> int pos: " + x + ", " + y);
////    		map[x][y] = (char)m.type;
////		}
//    	
//    	String mapString = "";
//    	for (int j = 0; j < heightOfLevel; j++) {
//    		for (int i = 0; i < widthOfLevel; i++) {
//				if (map[i][j] > 0){
//					mapString += (char)(map[i][j] + 36);
//				}else{
//					mapString += ".".charAt(0);
//				}
//			}
//			mapString += "\n";
//		}
//    	
//    	
////    	System.out.println(getActionList(n.list));
//    	System.out.println(mapString);
//    	System.out.println();
//    }
//    
//    
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
}
