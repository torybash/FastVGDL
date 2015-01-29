import fastVGDL.parsing.core.VGDLFactory;
import fastVGDL.parsing.core.VGDLParser;
import fastVGDL.core.game.Game;
import fastVGDL.core.game.GamePlayer;
import fastVGDL.core.game.results.GameResults;
import fastVGDL.core.player.AbstractPlayer;



public class Test {
    public static void main(String[] args) {

        String game_title = "boloadventures";
        int level = 0;

        String game_desc = "../gvgai/examples/gridphysics/"+game_title+".txt";
        String level_desc = "../gvgai/examples/gridphysics/"+game_title+"_lvl"+level+".txt";
        
        String puzzleController = "fastVGDL.controllers.puzzleSolverPlus.Agent";
        String humanController = "fastVGDL.controllers.human.Agent";
        String bestFirstController = "fastVGDL.controllers.bestFirst.Agent";
        
        GameResults results = GamePlayer.playGame(game_desc, level_desc, humanController, true);
        
        System.out.println(results);
//        String game_desc = "../gvgai/examples/gridphysics/"+game_title+".txt";
//        String level_desc = "../gvgai/examples/gridphysics/"+game_title+"_lvl"+level+".txt";
//
//        Game game = VGDLParser.GetInstance().parseGame(game_desc);
//
//        game.visuals = true;
//        VGDLParser.GetInstance().parseLevel(game, level_desc);
//
//        AbstractPlayer humanPlayer = VGDLFactory.GetInstance().createController("controllers.human.Agent");
//        AbstractPlayer puzzlePlayer = VGDLFactory.GetInstance().createController("controllers.puzzleSolverPlus.Agent");
//        AbstractPlayer bestFirstPlayer = VGDLFactory.GetInstance().createController("controllers.bestFirst.Agent");
//
//        game.playGameWithGraphics(puzzlePlayer);
//                game.playGameWithGraphics(bestFirstPlayer);
//		game.playGameWithGraphics(humanPlayer);
//		game.playGame(puzzlePlayer);

    }
	
	
}
