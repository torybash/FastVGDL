import fastVGDL.parsing.core.VGDLFactory;
import fastVGDL.parsing.core.VGDLParser;
import fastVGDL.tools.IO;
import fastVGDL.core.game.Game;
import fastVGDL.core.game.GamePlayer;
import fastVGDL.core.game.results.GameResults;
import fastVGDL.core.player.AbstractPlayer;



public class Test {
	
	  private static final long MEGABYTE = 1024L * 1024L;

	  public static long bytesToMegabytes(long bytes) {
	    return bytes / MEGABYTE;
	  }
	
    public static void main(String[] args) {

    	String dataFolder = "designed_results/";
    	
        String game_title = "portals";
        int level = 9;
        
        String gvgaiGames = "../gvgai/examples/gridphysics/";

        String game_desc = gvgaiGames + game_title+".txt";
        String level_desc = gvgaiGames + game_title+"_lvl"+level+".txt";
        
//        game_desc = "../GameChanger/rnd_gen_puzzle_games/"+game_title+".txt";
//        level_desc = "../GameChanger/rnd_gen_puzzle_games/"+game_title+"_lvl"+level+".txt";
//        game_desc = "../GameChanger/evolPuzzleGames/21;02,04;25/"+game_title+".txt";
//        level_desc = "../GameChanger/evolPuzzleGames/21;02,04;25/"+game_title+"_lvl"+level+".txt";
        
        
//        String level_desc = "../gvgai/examples/puzzlegames/realsokoban_orig/"+game_title+"_lvl"+level+".txt";
        
        String puzzleController = "fastVGDL.controllers.puzzleSolverPlus.Agent";
        String puzzleLowMemController = "fastVGDL.controllers.puzzleSolverPlusLowMem.Agent";
        String humanController = "fastVGDL.controllers.human.Agent";
        String bestFirstController = "fastVGDL.controllers.bestFirst.Agent";
        
        
        
        //1. Play a game and get results
//        long tim = System.currentTimeMillis();
        GameResults results = GamePlayer.playGame(game_desc, level_desc, puzzleController, true);
        System.out.println(results);
        
        
//        System.out.println("time: " + (System.currentTimeMillis() - tim));
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + memory);
        System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory));
        
        
        String[] games = new String[]{"bait", "brainman",
                "modality", "realsokoban", "thecitadel", "zenpuzzle"};
       
      //2- PLay a series of games and store results
//        int L = 5;
//        for (int g = 0; g < games.length; g++) {
//            game_desc = gvgaiGames + games[g]+".txt";
//            for (int l = 0; l < L; l++) {
//            	 level_desc = gvgaiGames + games[g]+"_lvl"+l+".txt";
//            	 GameResults results = GamePlayer.playGame(game_desc, level_desc, puzzleController, false);
//                 IO.storeString(results.toString(), dataFolder, games[g] + "_lvl"+ l + "_results");
//			}
//		}
        
        
    }
	
	
}
