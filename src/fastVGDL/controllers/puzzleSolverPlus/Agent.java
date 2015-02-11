package fastVGDL.controllers.puzzleSolverPlus;

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
import fastVGDL.core.game.results.GameResults;
import fastVGDL.core.player.AbstractPlayer;

public class Agent extends AbstractPlayer{
	
    ACTIONS[] actions;
    Random r;
    int blockSize = -1;
    int heightOfLevel = -1;
    int widthOfLevel = -1;
    
    boolean foundSolution = false;
    ArrayList<Integer> solution = new ArrayList<Integer>();
    int solutionIndex = 0;
    
    ArrayDeque<Node> q = new ArrayDeque<Node>();
    HashSet<Node> visitedNodes = new HashSet<Node>();
    
    final int MAX_VISITED_NODES = 5000000;
    final int MAX_QUEUE_SIZE = 2000000;
	
    final boolean VERBOSE = false;
    final boolean LOOP_VERBOSE = false;
    
    final boolean depthFirst = false;
    final boolean lowMemoryApproach = true;
    final boolean findAllSolutions = false;
    
    boolean[] wallPositions;
    
    boolean initialized = false;
    ForwardModel fm = null;
    
    //TEST
    int[] solutionTest = null;
    
    ArrayList<Node> solutions;
    
    private void initialize(Game game){
    	ArrayList<ACTIONS> act = game.getAvailableActions();
    	actions = new ACTIONS[act.size()];
    	for(int i = 0; i < actions.length; ++i) actions[i] = act.get(i);
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

        if (findAllSolutions) solutions = new ArrayList<Node>();
        
        initialized = true;
    }
        
    public ACTIONS act(Game game, ElapsedCpuTimer ect) {
        if (VERBOSE) System.out.println("Puzzlesolver plus acting----");
        
        if (!initialized)  initialize(game);

        if (foundSolution) return actions[solution.get(++solutionIndex)];

        if (q.isEmpty()){
            Node currentNode = new Node(new ArrayList<Integer>(), new HashSet<Moveable>(), -1);
            q.add(currentNode);
        }
		
        double avgTimeTaken = 0, acumTimeTaken = 0;
        long remaining = ect.remainingTimeMillis();
        int numIters = 0, remainingLimit = 5, lastDepth = -1;
        ElapsedCpuTimer elapsedTimerIteration = new ElapsedCpuTimer(TimerType.CPU_TIME);
        ForwardModel currentState = null;
        
        while(remaining > 2*avgTimeTaken && remaining > remainingLimit)
//        while(true)
        {
            if (LOOP_VERBOSE) System.out.println("START LOOP--" + elapsedTimerIteration.elapsedMillis() + " --> " + acumTimeTaken + " (" + remaining + "),  avgTimeTaken: " + avgTimeTaken + " - iters: " +numIters);
            if (LOOP_VERBOSE) System.out.println("visited node size: " + visitedNodes.size());
            
            boolean nodeAlreadyExists = false;
            boolean moveablesHaveChanged = false;
        	
            if (q.isEmpty()){
                if (VERBOSE) System.out.println("QUEUE EMPTY!!!");
                
                if (findAllSolutions) printSolutions(game);
                game.isEnded = true;
                return ACTIONS.ACTION_NIL; 
            }

            Node n = null;
            n = q.pollFirst();


            if (n.lastAction >= 0){
                n.list = (ArrayList<Integer>) n.list.clone();
                n.addAction(n.lastAction);
            }

            int d = n.list.size();

            HashSet<Moveable> lastMoveables = (HashSet<Moveable>) n.moveables.clone();
            
            if (d > 0){            
                if (LOOP_VERBOSE){
                    System.out.println("Advancing with node: {action list: " + getActionList(n.list));
                }
            	currentState = n.getFwdModel();
                if (currentState == null) currentState = playbackActions(fm, n.list);
                else currentState.advance(actions[n.lastAction]);
                
                
                //if current state contains flickers, advance with nil action
                while (stateContainsFlickers(currentState)){
//                    System.out.println("STATE CONTAINS FLICKERS!!!!!!!!!!!");
//                    System.out.println("moveables: " + getMoveables(currentState));

                    currentState.advance(ACTIONS.ACTION_NIL);
                }
            }else{
            	currentState = fm;
            }

            n.moveables = getMoveables(currentState);
            n.avatarPos = currentState.avatarSprite.position.copy();     
            
            //****Node has now finished initializing****
        	
        	
            if (!lastMoveables.equals(n.moveables)){
                    moveablesHaveChanged = true;
            }


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
                boolean gameOver = false;
                if (currentState.isEnded){
                    if (currentState.won){
                        if (VERBOSE){ 
                            System.out.println("FOUND SOLUTION!");
                            System.out.println("Avatar pos: " + currentState.avatarSprite.position);
                            System.out.println("Solution: " + getActionList(solution));
                            System.out.println("Solution length: " + n.list.size());
                            System.out.println("Visited nodes: " + visitedNodes.size());
                            System.out.println("Queue size: " + q.size());
                            System.out.println("Moveables: " + n.moveables);
                        }
                        if (findAllSolutions){
                        	solutions.add(n);
                        	gameOver = true;
                        }else{
                            solution = n.list;
                            foundSolution = true;
                        	return actions[solution.get(solutionIndex)];
                        }
                    }else{
                        gameOver = true;
                    }
                }

                //Expansion
                if (!gameOver){
                    if (solutionTest != null){
                        Node n_new = new Node(n.list, n.moveables, solutionTest[solutionIndex]);
                        q.add(n_new);
                        solutionIndex++;
                    }else{
                        for (int i = 0; i < actions.length; i++) {
                            //Dont expand into walls
                            Vector2i expectedNewPos = changePosByAction(n.avatarPos, i);
                            if (wallPositions[getPositionKey(expectedNewPos)]){
                                continue;
                            }

                            //Dont expand in opposite direction, if moveables haven't changed
                            if (!moveablesHaveChanged){
                                if (n.lastAction >= 0 && actions[i] == oppositeDirectionAction(n.lastAction)){
                                    continue;
                                }
                            }

                            Node n_new = null;
                            if (lowMemoryApproach){
                                n_new = new Node(n.list, n.moveables, i);
                            }else{
                                n_new = new Node(currentState.copy(), n.list, n.moveables, i);
                            }
                            
                            if (depthFirst){
//                          if (depthFirst || q.size() > MAX_QUEUE_SIZE){
//                          if (depthFirst || moveablesHaveChanged){
                                q.addFirst(n_new);
                            }else{
                                q.add(n_new);
                            }
                        }

                    }
                }

                visitedNodes.add(n);
            }
        	        	
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



    private void printSolutions(Game game) {
		for (Node n : solutions) {
			ForwardModel stateCopy = playbackActions(new ForwardModel(game), n.list);
	
			 GameResults results = new GameResults(stateCopy);

			 System.out.println("Printing results: " +results);
			 System.out.println("----------------");
		}
		
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
        return result;
    }
	
	
    ArrayList<ACTIONS> getActionList(ArrayList<Integer> list){
    	ArrayList<ACTIONS> result = new ArrayList<ACTIONS>();
    	
    	for (Integer integer : list) result.add(actions[integer]);
    	return result;
    }

    
    private int getPositionKey(Vector2i vec){
    	if (vec == null) return 0;
    	if (vec.x < 0 || vec.y < 0 || vec.x >= widthOfLevel || vec.y >= heightOfLevel) return widthOfLevel * heightOfLevel;
            return (vec.x + vec.y * widthOfLevel);
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

    private boolean stateContainsFlickers(ForwardModel currentState) {
        
        if (currentState.isEnded) return false;
        for (SpriteGroup sg : currentState.spriteGroups) {
            if (sg.sprites.size() > 0){
                Sprite sp =  sg.getFirstSprite();
                if (sp.getClass().getSimpleName().equals("Flicker") ||sp.getClass().getSimpleName().equals("OrientedFlicker")){
                    return true;
                }
            }  
        }
        
        return false;
    }
}
